
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HealthDataDao {

   DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = DatabaseConnection.getCon();

   public boolean createHealthData(HealthData healthData) {

    // check if the health data already exists
    HealthData existingHealthData = getHealthDataById(healthData.getId());
    if (existingHealthData != null) {
      System.out.println("Health data already exists");
      return false;
    }

    boolean result = false;
    String query = "INSERT INTO health_data (user_id, weight, height, steps, heart_rate, date) VALUES (?, ?, ?, ?, ?, ?)";

    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, healthData.getUserId());
      preparedStatement.setDouble(2, healthData.getWeight());
      preparedStatement.setDouble(3, healthData.getHeight());
      preparedStatement.setInt(4, healthData.getSteps());
      preparedStatement.setInt(5, healthData.getHeartRate());
      // preparedStatement.set(6, healthData.getDate());

      // Convert java.util.Date to java.sql.Date
      java.sql.Date sqlDate = new java.sql.Date(healthData.getDate().getTime());
      preparedStatement.setDate(6, sqlDate);

      int insertedHealthData = preparedStatement.executeUpdate();
      if (insertedHealthData != 0) {
        result = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    System.out.println("Health data inserted successfully");
    return result;

  }

   public HealthData getHealthDataById(int id) { 

    HealthData healthData = null;
    String query = "SELECT * FROM health_data WHERE id = ?";

    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, id);
      ResultSet resultSet = preparedStatement.executeQuery();
      while (resultSet.next()) {
        // Convert java.sql.Date to java.util.Date
        java.util.Date utilDate = new java.util.Date(resultSet.getDate("date").getTime());
        healthData = new HealthData(resultSet.getInt("id"), resultSet.getInt("user_id"), resultSet.getDouble("weight"), resultSet.getDouble("height"), resultSet.getInt("steps"), resultSet.getInt("heart_rate"), utilDate);
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return healthData;

}

   public List<HealthData> getHealthDataByUserId(int userId) { 
      
      List<HealthData> healthDataList = new ArrayList<>();
      String query = "SELECT * FROM health_data WHERE user_id = ?";
  
      try {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
          // Convert java.sql.Date to java.util.Date
          java.util.Date utilDate = new java.util.Date(resultSet.getDate("date").getTime());
          HealthData healthData = new HealthData(resultSet.getInt("id"), resultSet.getInt("user_id"), resultSet.getDouble("weight"), resultSet.getDouble("height"), resultSet.getInt("steps"), resultSet.getInt("heart_rate"), utilDate);
          healthDataList.add(healthData);
        }
      } catch (SQLException e) {
        e.printStackTrace();
        return null;
      }
      return healthDataList;
 }

   public boolean updateHealthData(int id, double weight, double height, int steps, int heartRate, Date date) { 

    boolean result = false;

    // check if health data exists
    HealthData existingHealthData = getHealthDataById(id);
    if (existingHealthData == null) {
      return false;
    }

    String query = "UPDATE health_data SET weight = ?, height = ?, steps = ?, heart_rate = ?, date = ? WHERE id = ?";

    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setDouble(1, weight);
      preparedStatement.setDouble(2, height);
      preparedStatement.setInt(3, steps);
      preparedStatement.setInt(4, heartRate);
      // preparedStatement.set(5, date);

      // Convert java.util.Date to java.sql.Date
      java.sql.Date sqlDate = new java.sql.Date(date.getTime());
      preparedStatement.setDate(5, sqlDate);
      preparedStatement.setInt(6, id);
      int updatedHealthData = preparedStatement.executeUpdate();
      if (updatedHealthData != 0) {
        result = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    System.out.println("Health data updated successfully");
    return result;
  
  }

   public boolean deleteHealthData(int id) {
    // check if health data exists
    HealthData existingHealthData = getHealthDataById(id);
    if (existingHealthData == null) {
      return false;
    }

    boolean result = false;
    String query = "DELETE FROM health_data WHERE id = ?";
    try {
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      preparedStatement.setInt(1, id);
      int deletedHealthData = preparedStatement.executeUpdate();
      if (deletedHealthData != 0) {
        result = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    System.out.println("Health data deleted successfully");
    return result;
}
}
