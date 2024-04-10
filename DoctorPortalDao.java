

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.print.Doc;

public class DoctorPortalDao {
    private UserDao userDao;
    private HealthDataDao healthDataDao;

    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = DatabaseConnection.getCon();

   // Complete all these methods and add more as needed


   // Implement the constructor 
    public DoctorPortalDao() {
        // a doctor portal dao object should have access to the users data and their Health Data
        userDao = new UserDao();// create a new instance of the UserDao to access its methods
        healthDataDao = new HealthDataDao(); // create a new instance of the HealthDataDao to access its methods
    }


    public Doctor getDoctorById(int doctorId) {
        Doctor doctor = null;
        String medicalLicenseNumber = null;
        String specialization = null;   
        String query = "SELECT * FROM users WHERE id = ? AND is_doctor = true";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                 doctor = new Doctor(resultSet.getInt("id"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("password"), resultSet.getBoolean("is_doctor"), medicalLicenseNumber, specialization);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Doctor not found");
        }
        return doctor;

    }

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

    // Implement this method 
    public List<HealthData> getHealthDataByPatientId(int patientId) {

        // Implement this method
        List<HealthData> healthDataList = new ArrayList<>();

        // HealthData hd = (HealthData) healthDataDao.getHealthDataByUserId(patientId);
        // healthDataList.add(hd);


        String query = "select * from health_data hd \n" + 
                        "join doctor_patient dp \n" + 
                        "on hd.user_id = dp.patient_id \n" + 
                        "where patient_id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, patientId);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    // for each row in the result set, create a new health data object and add it to the list
                    HealthData healthData = new HealthData(resultSet.getInt("id"), resultSet.getInt("user_id"), resultSet.getDouble("weight"), resultSet.getDouble("height"), resultSet.getInt("steps"), resultSet.getInt("heart_rate"), resultSet.getDate("date"));
                    healthDataList.add(healthData);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("No health data found");
            }
            
            return healthDataList;
        }
    // Add more methods for other doctor-specific tasks   
    public boolean addDoctorPatient(int doctorId, int patientId) {
        // Implement this method
        String query = "INSERT INTO doctor_patient (doctor_id, patient_id) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorId);
            preparedStatement.setInt(2, patientId);
            int result = preparedStatement.executeUpdate();
            if (result != 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Doctor patient relationship not added");
            return false;
        }
        return false;
    }

}

