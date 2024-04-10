

// import com.DataBaseConnection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;


public class HealthMonitoringApp {

    private static UserDaoExample userDao = new UserDaoExample();
    private static DoctorPortalDao doctorPortalDao = new DoctorPortalDao();
    private static HealthDataDao healthDataDao = new HealthDataDao();
    private static RecommendationSystem recommendationSystem = new RecommendationSystem();
    private static MedicineReminder reminder = new MedicineReminder(0, 5, "Ibuprofen", "1 tablet", "Daily", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
    private static MedicineReminderManager medicineReminderManager = new MedicineReminderManager();
    
    /**
     * Test the following functionalities within the Main Application
     *  1. Register a new user
     *  2. Log in the user
     *  3. Add health data
     *  4. Generate recommendations
     *  5. Add a medicine reminder
     *  6. Get reminders for a specific user
     *  7. Get due reminders for a specific user
     *  8. test doctor portal
     */
    public static void main(String[] args) {
       UserDaoExample userDao = new UserDaoExample();
        // test register a new user
        // User user3 = new User (0, "Andrew", "Morgan", "morgan@gmail.com", "password", false);
        // userDao.createUser(user3);
        // // test Login user (call testLoginUser() here)
        // UserDao.verifyPassword("morgan@gmail.com", "password");
        // testLoginUser("morgan@gmail.com", "password");
        // // Add users health data
        HealthData healthData = new HealthData(0, 5, 70.0, 170.0, 5000, 80, new Date(System.currentTimeMillis()));
        // healthDataDao.createHealthData(healthData);
        // Generate recommendations
        List<String> recomm = recommendationSystem.generateRecommendations(healthData);    
        System.out.println("Printing Recommendations:");
        // for (String recommendation : recomm) {    
        //     System.out.println(recommendation);
        //     System.out.println(" ");
        // }
        // convert the list to a string
        String recommendations = RecommendationSystem.getRecomString(recomm);
        System.out.println("Recommendations: " + recommendations);
        String recommendation = String.join(", ", recomm); 
        System.out.println("Recommendation: " + recommendation);
        

        // add recommendation to database
        Recommendation recommendationss = new Recommendation(0, 5, recommendations, new Date(System.currentTimeMillis()));
        RecommendationSystem.createRecommendation(recommendationss);
        // Add a medicine reminder
        MedicineReminder reminder = new MedicineReminder(0, 5, "Ibuprofen", "1 tablet", "Daily", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        medicineReminderManager.addReminder(reminder);
        medicineReminderManager.createReminder(reminder);// add to database       
        // Get reminders for a specific user
        List<MedicineReminder> medReminder =  medicineReminderManager.getRemindersForUser(5);
        if(medReminder.isEmpty()) {
            System.out.println("No Medicine Reminders for User 5");
        }
        if(medReminder.size() > 0) {
            System.out.println("Printing Medicine Reminders for User 5:");
            for (MedicineReminder medicineReminder : medReminder) {           
                System.out.println(medicineReminder);
                System.out.println(" ");
            }
        }

        // Get due reminders for a specific user
        List<MedicineReminder> dueReminders = medicineReminderManager.getDueReminders(5);
        if(dueReminders.isEmpty()) {
            System.out.println("No Due Reminders for User 5");
        }
        if(dueReminders.size() > 0) {
            System.out.println("Printing Due Medicine Reminders for User 5:");
            for (MedicineReminder medicineReminder : dueReminders) {
                System.out.println(medicineReminder);
                System.out.println(" ");
            }
        }
        //test doctor portal (call testDoctorPortal() here)
        testDoctorPortal();

        // List<User> userList = new ArrayList<>();
        // User user1 = new User(5,"Ainee", "Malik","qmalik@gmail.com", "guggu", false);
        // userList.add(user1);
        // for (User users : userList) {
        //     userDao.createUser(users);
        // }
    }


    public static boolean loginUser(String email, String password) {
        //implement method to login user.
        User user = userDao.getUserByEmail(email);
        if (user != null) {
            // Compare the stored hashed password with the given password and return result
            return BCrypt.checkpw(password, user.getPassword());
        }
        return false;
    }

      // Test the Doctor Portal
    public static void testDoctorPortal() {
        // Replace the doctorId with a valid ID from your database
        int doctorId = 3;
        int patientId = 5;
        // Add code to Fetch the doctor by ID
        Doctor doctor = doctorPortalDao.getDoctorById(doctorId);
        System.out.println("Printing Doctor 3:");
        System.out.println(doctor);
        // Add code to Fetch patients associated with the doctor
        List<User> patients = doctorPortalDao.getPatientsByDoctorId(doctorId);
        System.out.println("Printing Patients associated with Doctor 3:");
        for (User patient : patients) {         
            System.out.println(patient);
            System.out.println(" ");
        }
        // Add code to Fetch health data for the patient       
        List<HealthData> healthDataList = doctorPortalDao.getHealthDataByPatientId(patientId);
        System.out.println("Printing Health Data for Patient 5:");
        for (HealthData healthData : healthDataList) {
            System.out.println(healthData);
            System.out.println(" ");
        }

    }

    // Test the loginUser method by providing email and password
    public static void testLoginUser(String email, String password) {
        boolean loginSuccess = loginUser(email, password);
        if (loginSuccess) {
            // Print to console, "Login Successful"
            System.out.println("Login Successful");
        } else {
            // Print to console, "Incorrect email or password. Please try again.");
            System.out.println("Incorrect email or password. Please try again.");
            // Show an error message and prompt the user to re-enter their credentials
                       
        }
    }

}
