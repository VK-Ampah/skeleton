import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Demo {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        // User user = new User(0, "John", "Doe", "vkampah@gmail.com", "password", false);
        // User user2 = new User(0, "Jane", "Don", "vkampah28@gmail.com", "password", true);

        // User user3 = new User(0, "Alice", "Smith", "alice@gmail.com", "password", false);
        // User user4 = new User(0, "Bob", "Johnson", "bob@gmail.com", "password", true);
        // User user5 = new User(0, "Emily", "Davis", "emily@gmail.com", "password", false);
        // User user6 = new User(0, "Michael", "Wilson", "michael@gmail.com", "password", true);
        // User user7 = new User(0, "Sophia", "Brown", "sophia@gmail.com", "password", false);

        // UserDao userDao = new UserDao();
        // userDao.createUser(user);
        // userDao.createUser(user2);
        // userDao.createUser(user3);
        // userDao.createUser(user4);
        // userDao.createUser(user5);
        // userDao.createUser(user6);
        // userDao.createUser(user7);

        // User vala = userDao.getUserById(1);
        // System.out.println("-----------------");
        // System.out.println(vala.toString());
        // System.out.println("-----------------");
        // User jane = userDao.getUserByEmail("vkampah28@gmail.com");
        // System.out.println(jane.toString());
        // System.out.println("-----------------");
        // User alice = userDao.getUserByEmail("alice@gmail.com");
        // System.out.println(alice.toString());
        // System.out.println("-----------------");
        // User bob = userDao.getUserByEmail("bob@gmail.com");
        // System.out.println(bob.toString());
        // System.out.println("-----------------");
        // User emily = userDao.getUserByEmail("emily@gmail.com");
        // System.out.println(emily.toString());
        // System.out.println("-----------------");
        // User michael = userDao.getUserById(6);
        // System.out.println(michael.toString());
        // System.out.println("-----------------");

        // // update user by id       
        // // userDao.updateUser(4, "Doe", false, "james2");
        // // delete user by id
        // userDao.deleteUser(7);
        // // verify password
        // System.out.println(userDao.verifyPassword("alice@gmail.com","james2"));

        // testing healthdata
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date date1 = null;
        Date date2 = null;
        Date date3 = null;

        try {
            date1 = formatter.parse("2021-09-01");
            date2 = formatter.parse("2021-09-02");
            date3 = formatter.parse("2021-09-03");
        } catch (ParseException e) {
            e.printStackTrace();
        }
      
        HealthData healthData = new HealthData(0, 1, 70.0, 5.5, 1000, 70, date1);
        HealthData healthData2 = new HealthData(0, 4, 80.0, 6.0, 2000, 80, date2);
        HealthData healthData3 = new HealthData(0, 3, 90.0, 6.5, 3000, 90, date3);
        // insert
        HealthDataDao healthDataDao = new HealthDataDao();
        // healthDataDao.createHealthData(healthData);
        // healthDataDao.createHealthData(healthData2);
        // healthDataDao.createHealthData(healthData3);
        // update
        // healthDataDao.updateHealthData(1, 80.0, 10.0, 7000, 100, date2);
        // delete
        healthDataDao.deleteHealthData(3);

    }    
}
