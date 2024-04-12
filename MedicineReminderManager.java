import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class MedicineReminderManager {
    private List<MedicineReminder> reminders;
    DatabaseConnection databaseConnection = new DatabaseConnection();
    Connection connection = DatabaseConnection.getCon();

    // Constructor
    public MedicineReminderManager() {
        this.reminders = new ArrayList<>();
    }

    // Add a reminder
    public void addReminder(MedicineReminder reminder) {
        reminders.add(reminder);
    }

    // create reminder for a specific user
    public boolean createReminder(MedicineReminder reminder) {
        String query = "INSERT INTO medicine_reminders (user_id, medicine_name, dosage, schedule, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, reminder.getUserId());
            preparedStatement.setString(2, reminder.getMedicineName());
            preparedStatement.setString(3, reminder.getDosage());
            preparedStatement.setString(4, reminder.getSchedule());
            // process the date
            java.sql.Date sqlStartDate = new java.sql.Date(reminder.getStartDate().getTime());
            java.sql.Date sqlEndDate = new java.sql.Date(reminder.getEndDate().getTime());
            preparedStatement.setDate(5, sqlStartDate);
            preparedStatement.setDate(6, sqlEndDate);
            int insertedReminder = preparedStatement.executeUpdate();
            if (insertedReminder != 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    // update reminder
    public boolean updateReminder(MedicineReminder reminder) {     
        String query = "UPDATE medicine_reminders SET medicine_name = ?, dosage = ?, schedule = ?, start_date = ?, end_date = ? WHERE id = ?";
        // Database logic to update reminder Using Prepared Statement
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, reminder.getMedicineName());
            preparedStatement.setString(2, reminder.getDosage());
            preparedStatement.setString(3, reminder.getSchedule());
            java.sql.Date startDate = new java.sql.Date(reminder.getStartDate().getTime());
            java.sql.Date endDate = new java.sql.Date(reminder.getEndDate().getTime());
            preparedStatement.setDate(4, startDate);
            preparedStatement.setDate(5, endDate);
            // java.sql.Date sqlStartDate = java.sql.Date.valueOf(reminder.getStartDate());
            // java.sql.Date sqlEndDate = java.sql.Date.valueOf(reminder.getEndDate());
            // preparedStatement.setDate(4, sqlStartDate);
            // preparedStatement.setDate(5, sqlEndDate);
            preparedStatement.setInt(6, reminder.getId());
            int updatedReminder = preparedStatement.executeUpdate();
            if (updatedReminder != 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean deleteReminder(int reminderId) {     
        String query = "DELETE FROM medicine_reminders WHERE id = ?";
        // Database logic to delete reminder Using Prepared Statement
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, reminderId);
            int deletedReminder = preparedStatement.executeUpdate();
            if (deletedReminder != 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public List<MedicineReminder> getRemindersForUser(int userId) {
        List<MedicineReminder> userReminders = new ArrayList<>();
        String query = "SELECT * FROM medicine_reminders WHERE user_id = ?";
        // Database logic to get reminders for a specific user
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                MedicineReminder reminder = new MedicineReminder(resultSet.getInt("id"), resultSet.getInt("user_id"), resultSet.getString("medicine_name"), resultSet.getString("dosage"), resultSet.getString("schedule"), resultSet.getDate("start_date"), resultSet.getDate("end_date"));
                userReminders.add(reminder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return userReminders;
    }

    public List<MedicineReminder> getDueReminders(int userId) {
        List<MedicineReminder> dueReminders = new ArrayList<>();
        // define current date and time
        LocalDateTime now = LocalDateTime.now();
        // get users reminders
        List<MedicineReminder> userReminders = getRemindersForUser(userId);
        // check if the reminders are due
        for (MedicineReminder reminder : userReminders) {
            LocalDateTime startDate = reminder.getStartDate().toLocalDate().atStartOfDay();
            LocalDateTime endDate = reminder.getEndDate().toLocalDate().atStartOfDay();            
            // check if the current date is between the start and end date (inclusive endpoints)
            if (!now.isBefore(startDate) && !now.isAfter(endDate)) {
                dueReminders.add(reminder);
            }
        }

        // if(dueReminders.size() == 0) {
        //     System.out.println("No reminders due");            
        // }
        return dueReminders;
    }
}
