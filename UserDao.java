import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class UserDao {
    DatabaseConnection databaseConnection = new DatabaseConnection();
    static Connection connection = DatabaseConnection.getCon();

    // create user
    public static boolean createUser(User user) {
        // check if the user already exists by unique email
        User user1 = getUserByEmail(user.getEmail());
        if (user1 != null) {
            System.out.println("user already exists");
            return false;
        }
        // create a boolean variable to check if the user is created
        boolean bool = false;
        // encrypting the password
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);// update the password with the hashed password
        // Prepare the SQL query
        String query = "INSERT INTO users (first_name, last_name, email, password, is_doctor ) VALUES (?, ?, ?, ?, ?)";
        // Database logic to insert user Using Prepared Statement
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            // get the user data and set the values to the query
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setBoolean(5, user.isDoctor());
            int insertedUser =  preparedStatement.executeUpdate();
            if (insertedUser != 0) {
                 bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        System.out.println("User created successfully");
        return bool;

    }

    // get user by id
    public User getUserById(int id) {  
        User user = null;
        String query = "SELECT * FROM users WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User( resultSet.getInt("id"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("password"), resultSet.getBoolean("is_doctor"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public User getDoctorById(int id) {  
        User user = null;
        String query = "SELECT * FROM users WHERE id = ? AND is_doctor = true";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User( resultSet.getInt("id"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("password"), resultSet.getBoolean("is_doctor"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    // get user by email
    public static User getUserByEmail(String email) {  
        User user = null;
        String query = "SELECT * FROM users WHERE email = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // create a new user object and set the values from the database
                user = new User( resultSet.getInt("id")   ,resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("email"), resultSet.getString("password"), resultSet.getBoolean("is_doctor"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;

    }

    // update user
    public boolean updateUser(int id, String lastName, boolean isDoctor, String password) {
        boolean bool = false;
        // harsh password
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        // Prepare the SQL query
        String query = "UPDATE users SET last_name = ?, is_doctor = ?, password = ? WHERE id = ?";    
        // Database logic to update Using Prepared Statement
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, lastName);
            preparedStatement.setBoolean(2, isDoctor);
            preparedStatement.setString(3, hashedPassword);
            preparedStatement.setInt(4, id);
            int updatedUser = preparedStatement.executeUpdate();
            if (updatedUser != 0) {
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User with id: " + id + " has been updated");
        return bool;
}
    // delete user
    public boolean deleteUser(int id) { // delete user from the database 
        // verify if user exists
        User user1 = getUserById(id);
        if (user1 == null) {
            System.out.println("user does not exists");
            return false;
        }
        // Prepare the SQL query
        String query = "DELETE FROM users WHERE id = ?";
        boolean bool = false;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            int deletedUser = preparedStatement.executeUpdate();
            if (deletedUser != 0) {
                bool = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
    }
        System.out.println("User with id: " + id + " has been deleted");
        return bool;
    }

    // verify password
    public static boolean verifyPassword (String email, String password)
    {
        // get the user by email
        User user = getUserByEmail(email);
        // check if the user is null
        if (user == null) {
            return false;
        }
        // check if the password is correct and print a custom text
        if(BCrypt.checkpw(password, user.getPassword())){
            System.out.println("Password is correct");
            System.out.println("Welcome " + user.getFirstName()+ ", You are logged in");
        } else {
            System.out.println("Password is incorrect, please try again");

        }
        return BCrypt.checkpw(password, user.getPassword());
    }
}
