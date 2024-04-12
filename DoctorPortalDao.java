import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorPortalDao {
    private UserDao userDao;
    private HealthDataDao healthDataDao;

    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = DatabaseConnection.getCon();

   // Implement the constructor 
    public DoctorPortalDao() {
        userDao = new UserDao();
        healthDataDao = new HealthDataDao(); 
    }

    // create doctor
    public Doctor getDoctorById(int doctorId) {
        Doctor doctor = null;
        String medicalLicenseNumber = null;
        String specialization = null;   
        User doc = userDao.getDoctorById(doctorId);
        if (doc != null) {
            doctor = new Doctor(doc.getId(), doc.getFirstName(), doc.getLastName(), doc.getEmail(), doc.getPassword(), doc.isDoctor(), medicalLicenseNumber, specialization);
        }
        return doctor;

    }

    // get patients by doctor id
    public List<User> getPatientsByDoctorId(int doctorId) {
        String query =  "Select * from users u\n" + //
                        "join doctor_patient dp \n" + 
                        "on u.id = dp.patient_id \n" + 
                        "where dp.doctor_id = ?";
        List<User> patients = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // for each row in the result set, create a new user object and add it to the list
                User user = new User(resultSet.getInt("id"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("password"), resultSet.getBoolean("is_doctor"));
                patients.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("No patients found");
        }
        return patients;
    }

    //  get health data by patient id
    public List<HealthData> getHealthDataByPatientId(int patientId) {
        List<HealthData> healthDataList = new ArrayList<>();
        healthDataList = healthDataDao.getHealthDataByUserId(patientId);
        return healthDataList;
        }

    // add doctor patient relationship  
    public boolean addDoctorPatient(int doctorId, int patientId) {
        // check if the relationship already exists
        String checkQuery = "SELECT * FROM doctor_patient WHERE doctor_id = ? AND patient_id = ?";
        String insertQuery = "INSERT INTO doctor_patient (doctor_id, patient_id) VALUES (?, ?)";
        try {
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, doctorId);
            checkStatement.setInt(2, patientId);
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                System.out.println("Doctor patient relationship already exists");
                return false;
            } else {
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setInt(1, doctorId);
                insertStatement.setInt(2, patientId);
                int result = insertStatement.executeUpdate();
                if (result != 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while adding doctor patient relationship");
            return false;
        }
        return false;
    }
}

