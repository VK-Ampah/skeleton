import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// RecommendationSystem class to generate

public class RecommendationSystem {
    private static final int MIN_HEART_RATE = 60;
    private static final int MAX_HEART_RATE = 100;
    private static final int MIN_STEPS = 10000;
    private static final double BMI_UNDERWEIGHT = 18.5;
    private static final int BMI_OVERWEIGHT = 25;

    //generate recommendations based on the user's health data
    public List<String> generateRecommendations(HealthData healthData) {
        List<String> recommendations = new ArrayList<>();

//        // Analyze heart rate
        int heartRate = healthData.getHeartRate();
        if (heartRate < MIN_HEART_RATE) {
            recommendations.add("Your heart rate is lower than the recommended range. " +
                    "Consider increasing your physical activity to improve your cardiovascular health.");
        }
        if (heartRate > MAX_HEART_RATE) {
            recommendations.add("Your heart rate is higher than the recommended range. " +
                    "Consider consulting with a healthcare provider to evaluate your heart health.");
        }

       // Analyze steps
        int steps = healthData.getSteps();
        if (steps < MIN_STEPS) {
            recommendations.add("You're not reaching the recommended daily step count. " +
                    "Try to incorporate more walking or other physical activities into your daily routine.");
        }

        // Analyze BMI
        double weight = healthData.getWeight();
        double bmi = weight / (healthData.getHeight() * healthData.getHeight());
        if (bmi < BMI_UNDERWEIGHT) {
            recommendations.add("Your BMI is below the healthy range. " +
                    "Consider consulting with a healthcare provider to discuss your nutrition and weight management.");
        } else if (bmi > BMI_OVERWEIGHT) {
            recommendations.add("Your BMI is above the healthy range. " +
                    "Consider consulting with a healthcare provider to discuss your nutrition and weight management.");
        }

        int caloriesBurned = (int) (steps * 0.05);
        recommendations.add("You have burned approximately " + caloriesBurned + " calories today.");

        return recommendations;
    }

    // Create a new recommendation in the database
    public static boolean createRecommendation(Recommendation recommendation) {
        Connection connection = DatabaseConnection.getCon();
        String query = "INSERT INTO recommendations (user_id, recommendation_text, date) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, recommendation.getUserId());
            preparedStatement.setString(2, recommendation.getRecommendation());
            preparedStatement.setDate(3, new java.sql.Date(recommendation.getDate().getTime()));
            int insertedRecommendation = preparedStatement.executeUpdate();
            if (insertedRecommendation != 0) {
                return true;
            }
            System.out.println("Recommendation created successfully");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Recommendation not created");
            return false;
        }
        return true;
    }

    // Helper method to get the recommendations as a formatted string
    public static String getRecomString(List<String> recommendations) {
        StringBuilder sb = new StringBuilder();
        for (String recommendation : recommendations) {
            sb.append(recommendation).append("\n");
        }
        return sb.toString();
    }

    // get recommedation by user id
        public static List<Recommendation> getRecommendationsByUserId(int userId) {
                List<Recommendation> recommendations = new ArrayList<>();
                Connection connection = DatabaseConnection.getCon();
                String query = "SELECT * FROM recommendations WHERE user_id = ?";

                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, userId);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        // create a new recommendation object and set the values from the database
                        Recommendation recommendation = new Recommendation(resultSet.getInt("id"), resultSet.getInt("user_id"), resultSet.getString("recommendation_text"), resultSet.getDate("date"));
                        recommendations.add(recommendation);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    System.out.println("No recommendations found");
                }
                return recommendations;
        }

        // update recommendation text by user id
        public static boolean updateRecommendationText(int userId, String newRecommendationText) {
            Connection connection = DatabaseConnection.getCon();
            String query = "UPDATE recommendations SET recommendation_text = ? WHERE user_id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, newRecommendationText);
                preparedStatement.setInt(2, userId);
                int updatedRecommendation = preparedStatement.executeUpdate();
                if (updatedRecommendation != 0) {
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Recommendation text not updated");
                return false;
            }
            return false;
        }

}
