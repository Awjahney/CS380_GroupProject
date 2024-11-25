import java.sql.*;

public class ScheduleDriver {
    public static void main(String[] args) {
        // Database connection details
        String url = "jdbc:mysql://localhost:3306/scheduler_db";
        String user = "root";
        String password = "ilovelife2093003!";

        // Connect to the database
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to the database!");

            // Insert a new user and retrieve the user_id
            String insertUserQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
            int userId = -1;

            // Start a transaction
            connection.setAutoCommit(false);  // Disable auto-commit to start transaction

            try (PreparedStatement userStmt = connection.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS)) {
                userStmt.setString(1, "test_user");
                userStmt.setString(2, "password123");
                userStmt.executeUpdate();

                // Get the generated user_id
                try (ResultSet generatedKeys = userStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        userId = generatedKeys.getInt(1);  // Retrieve the generated user_id
                        System.out.println("New user ID: " + userId);
                    }
                }

                // Insert the reminder for the newly created user
                if (userId != -1) {
                    String insertReminderQuery = "INSERT INTO reminders (user_id, task_name, reminder_date, reminder_time, priority, content, is_active) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

                    try (PreparedStatement reminderStmt = connection.prepareStatement(insertReminderQuery)) {
                        reminderStmt.setInt(1, userId);  // Use the user_id for the reminder
                        reminderStmt.setString(2, "Finish Report");
                        reminderStmt.setDate(3, Date.valueOf("2024-11-25"));
                        reminderStmt.setTime(4, Time.valueOf("14:00:00"));
                        reminderStmt.setString(5, "HIGH");
                        reminderStmt.setString(6, "Complete the quarterly report.");
                        reminderStmt.setBoolean(7, true);

                        reminderStmt.executeUpdate();
                        System.out.println("Reminder saved successfully!");
                    }
                }

                // Commit the transaction
                connection.commit();
            } catch (SQLException e) {
                // Rollback if any error occurs
                connection.rollback();
                System.err.println("Transaction failed, rolling back changes.");
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.err.println("Database error occurred!");
            e.printStackTrace();
        }
    }
}
