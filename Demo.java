public class Demo {
    public static void main(String[] args) {
        System.out.println("Hello, World!");

        User user = new User(0, "John", "Doe", "vkampah@gmail.com", "password", false);
        User user2 = new User(0, "Jane", "Don", "vkampah28@gmail.com", "password", true);

        User user3 = new User(0, "Alice", "Smith", "alice@gmail.com", "password", false);
        User user4 = new User(0, "Bob", "Johnson", "bob@gmail.com", "password", true);
        User user5 = new User(0, "Emily", "Davis", "emily@gmail.com", "password", false);
        User user6 = new User(0, "Michael", "Wilson", "michael@gmail.com", "password", true);
        User user7 = new User(0, "Sophia", "Brown", "sophia@gmail.com", "password", false);

        UserDao userDao = new UserDao();
        userDao.createUser(user);
        userDao.createUser(user2);
        userDao.createUser(user3);
        userDao.createUser(user4);
        userDao.createUser(user5);
        userDao.createUser(user6);
        userDao.createUser(user7);

        User vala = userDao.getUserById(1);
        System.out.println("-----------------");
        System.out.println(vala.toString());
        System.out.println("-----------------");
        User jane = userDao.getUserByEmail("vkampah28@gmail.com");
        System.out.println(jane.toString());
        System.out.println("-----------------");
        User alice = userDao.getUserByEmail("alice@gmail.com");
        System.out.println(alice.toString());
        System.out.println("-----------------");
        User bob = userDao.getUserByEmail("bob@gmail.com");
        System.out.println(bob.toString());
        System.out.println("-----------------");
        User emily = userDao.getUserByEmail("emily@gmail.com");
        System.out.println(emily.toString());
        System.out.println("-----------------");
        User michael = userDao.getUserById(6);
        System.out.println(michael.toString());
        System.out.println("-----------------");

        // update user by id       
        // userDao.updateUser(4, "Doe", false, "james2");
        // delete user by id
        userDao.deleteUser(7);
        // verify password
        System.out.println(userDao.verifyPassword("alice@gmail.com","james2"));

    }    
}
