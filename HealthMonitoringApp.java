

// import com.DataBaseConnection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
// import org.mindrot.jbcrypt.BCrypt;

public class HealthMonitoringApp {
    private static DoctorPortalDao doctorPortalDao = new DoctorPortalDao();
    private static HealthDataDao healthDataDao = new HealthDataDao();
    private static RecommendationSystem recommendationSystem = new RecommendationSystem();
    private static MedicineReminderManager medicineReminderManager = new MedicineReminderManager();
    public static void main(String[] args) {
        // test register a new user
        System.out.println("");
        System.out.println("Testing User Registration:");
        System.out.println("-----------------------");
        System.out.println("-----------------------");
        List<User> userList = new ArrayList<>();
        User user3 = new User(0, "Andrew", "Morgan", "morgan@gmail.com", "puyol", false);
        User user4 = new User(0, "shaun", "rewadisky", "shaun@gmail.com", "payard", true);
        User user5 = new User(0, "shigy", "laveron", "shigy@gmail.com", "lesly", false);
        User user6 = new User(0, "michael", "wilson", "mwilson@gmail.com", "leslina", true); 
        User user7 = new User(0, "sophia", "brown", "soph@gmail.com", "hedbg", false);
        User user8 = new User(0, "james", "smith", "james@gmail.com", "james", true);
        User user9 = new User(0, "jane", "doe", "jane@gmail.com", "jane", false);
        User user10 = new User(0, "alice", "smith", "alice@gmail.com", "alice", true);
    
        userList.add(user3);
        userList.add(user4);
        userList.add(user5);
        userList.add(user6);
        userList.add(user7);
        userList.add(user8);
        userList.add(user9);
        userList.add(user10);
        for (User user : userList) {
            UserDao.createUser(user);        
        }
        System.out.println("-----------------------");
        System.out.println("-----------------------");
        System.out.println(" ");
        System.out.println("Printing User details");
        System.out.println("-----------------------");
        System.out.println("-----------------------");
        for (User user : userList) {
            System.out.println(user);
            System.out.println(" ");
        }
        System.out.println("-----------------------");
        System.out.println("-----------------------");
        System.out.println(" ");
        // test Login user (call testLoginUser() here)
        System.out.println("Testing Login User:");
        System.out.println("-----------------------");
        UserDao.verifyPassword("morgan@gmail.com", "password");
        System.out.println("-----------------------");
        System.out.println("-----------------------");
        System.out.println(" ");
        // testLoginUser("morgan@gmail.com", "password");


        // Add users health data
        List<HealthData> healthDataList = new ArrayList<>();
        HealthData healthData = new HealthData(0, 5, 70.0, 170.0, 5000, 80, new Date(System.currentTimeMillis()));
        HealthData healthData2 = new HealthData(0, 8, 75.0, 170.0, 6000, 85, new Date(System.currentTimeMillis()));
        HealthData healthData3 = new HealthData(0, 6, 80.0, 170.0, 7000, 90, new Date(System.currentTimeMillis()));
        HealthData healthData4 = new HealthData(0, 5, 85.0, 170.0, 8000, 95, new Date(System.currentTimeMillis()));
        HealthData healthData5 = new HealthData(0, 8, 90.0, 170.0, 9000, 100, new Date(System.currentTimeMillis()));
        HealthData healthData6 = new HealthData(0, 6, 95.0, 170.0, 10000, 105, new Date(System.currentTimeMillis()));
        HealthData healthData7 = new HealthData(0, 5, 100.0, 170.0, 11000, 110, new Date(System.currentTimeMillis()));
        healthDataList.add(healthData);
        healthDataList.add(healthData2);
        healthDataList.add(healthData3);
        healthDataList.add(healthData4);
        healthDataList.add(healthData5);
        healthDataList.add(healthData6);
        healthDataList.add(healthData7);
        System.out.println("Adding Health Data:");
        System.out.println("------------------");
        System.out.println("------------------");
        for (HealthData healthData1 : healthDataList) {
            healthDataDao.createHealthData(healthData1);
        }
        System.out.println("------------------");
        System.out.println("------------------");
        System.out.println(" ");
        System.out.println("Printing Health Data:");
        System.out.println("---------------------");
        System.out.println("---------------------");
        for (HealthData healthData1 : healthDataList) {
            System.out.println(healthData1);
            System.out.println(" ");
        }
        System.out.println("---------------------");
        System.out.println("---------------------");
        System.out.println(" ");

        // Generate recommendations for all health data
        for (HealthData healthData1 : healthDataList) {
            List<String> recomm = recommendationSystem.generateRecommendations(healthData1);
            System.out.println("Printing Recommendations:");
            System.out.println("-------------------------");
            System.out.println("-------------------------");
            // convert the list to a string
            String recommendations = RecommendationSystem.getRecomString(recomm);
            System.out.println("Recommendations: " + recommendations);
            System.out.println("-------------------------");
            System.out.println("-------------------------");
            System.out.println(" ");
            // add recommendation to database
            Recommendation recommendation = new Recommendation(0, healthData1.getUserId(), recommendations, new Date(System.currentTimeMillis()));
            System.out.println("Adding Recommendations to db:");
            System.out.println("-----------------------------");
            System.out.println("-----------------------------");
            if(RecommendationSystem.createRecommendation(recommendation)) {
                System.out.println("Recommendation added to database successfully");
                System.out.println(" ");
            } else {
                System.out.println("Recommendation creation failed");
                System.out.println(" ");
            }
            System.out.println("-----------------------------");
            System.out.println("-----------------------------");
            System.out.println(" ");
        }

        // Add a medicine reminder
        MedicineReminder reminder = new MedicineReminder(0, 5, "Ibuprofen", "1 tablet", "Daily", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        MedicineReminder reminder2 = new MedicineReminder(0, 6, "Paracetamol", "1 tablet", "Daily", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        MedicineReminder reminder3 = new MedicineReminder(0, 8, "Aspirin", "1 tablet", "Daily", new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        System.out.println("Adding Medicine Reminder:");
        medicineReminderManager.addReminder(reminder);
        medicineReminderManager.addReminder(reminder2);
        medicineReminderManager.addReminder(reminder3);
        System.out.println("Medicine Reminder added to reminder list successfully");
        System.out.println("------------------------------------------------------");
        System.out.println("------------------------------------------------------");

        System.out.println(" ");
        System.out.println("Inserting Medicine Reminder to database:");
        medicineReminderManager.createReminder(reminder);  
        medicineReminderManager.createReminder(reminder2);
        medicineReminderManager.createReminder(reminder3);
        System.out.println("Medicine Reminder added to database successfully");
        System.out.println("------------------------------------------------");
        System.out.println("------------------------------------------------");
        System.out.println(" ");     


        // Get reminders for a specific user with ID 5
        List<MedicineReminder> medReminder =  medicineReminderManager.getRemindersForUser(5);
        if(medReminder.isEmpty()) {
            System.out.println("No Medicine Reminders for User 5");
        }
        if(medReminder.size() > 0) {
            System.out.println("Printing Medicine Reminders for User 5:");
            System.out.println("--------------------------------------");
            System.out.println("--------------------------------------");
            for (MedicineReminder medicineReminder : medReminder) {           
                System.out.println(medicineReminder);
                System.out.println(" ");
            }
            System.out.println("--------------------------------------");
            System.out.println("--------------------------------------");
            System.out.println(" ");
        }

        // Get due reminders for a specific user with ID 5
        System.out.println("Checking for Due Reminders for User 5:");
        List<MedicineReminder> dueReminders = medicineReminderManager.getDueReminders(5);
        if(dueReminders.isEmpty()) {
            System.out.println("No Due Reminders found for User 5");
            System.out.println("--------------------------------------");
            System.out.println("--------------------------------------");
            System.out.println(" ");
        }
        if(dueReminders.size() > 0) {
            System.out.println("Printing Due Medicine Reminders for User 5:");
            System.out.println("--------------------------------------");
            System.out.println("--------------------------------------");
            for (MedicineReminder medicineReminder : dueReminders) {
                System.out.println(medicineReminder);
                System.out.println(" ");
            }
            System.out.println("--------------------------------------");
            System.out.println("--------------------------------------");
            System.out.println(" ");
        }

        // test doctor portal 
        testDoctorPortal();      
    }

      // Test the Doctor Portal
    public static void testDoctorPortal() {
        // Replace the doctorId with a valid ID from your database
        int doctorId = 3;
        int patientId = 5;
        // Add code to Fetch the doctor by ID
        Doctor doctor = doctorPortalDao.getDoctorById(doctorId);
        doctor.setMedicalLicenseNumber("123456");
        doctor.setSpecialization("Cardiologist");
        System.out.println("Printing the details for Doctor with id " + doctorId + ":");
        System.out.println("------------------------------------------");
        System.out.println("------------------------------------------");
        System.out.println(doctor);
        System.out.println("------------------------------------------");
        System.out.println(" ");
        // Add code to Fetch patients associated with the doctor
        List<User> patients = doctorPortalDao.getPatientsByDoctorId(doctorId);
        System.out.println("Printing Patients associated with Doctor 3:");
        System.out.println("------------------------------------------");
        System.out.println("------------------------------------------");
        for (User patient : patients) {         
            System.out.println(patient);
            System.out.println(" ");
        }
        System.out.println("------------------------------------------");
        System.out.println("------------------------------------------");
        System.out.println(" ");
        // Add code to Fetch health data for the patient       
        List<HealthData> healthDataList = doctorPortalDao.getHealthDataByPatientId(patientId);
        System.out.println("Printing Health Data for Patient 5:");
        System.out.println("------------------------------------------");
        System.out.println("------------------------------------------");
        for (HealthData healthData : healthDataList) {
            System.out.println(healthData);
            System.out.println(" ");
        }
        System.out.println("------------------------------------------");
        System.out.println("------------------------------------------");

    }


}
