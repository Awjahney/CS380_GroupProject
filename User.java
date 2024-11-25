import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user in the Weekly Scheduler application with database integration support.
 */
public class User {
    private int userId;
    private String username;
    private String password;

    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Fetches all reminders for the user from the database.
     *
     * @param connection the database connection
     * @return a list of reminders for the user
     * @throws SQLException if a database error occurs
     */
    public List<Reminders> fetchReminders(Connection connection) throws SQLException {
        List<Reminders> reminders = new ArrayList<>();
        String query = "SELECT * FROM reminders WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, this.userId);  // Use current user's ID
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reminders.add(new Reminders(
                            rs.getString("task_name"),
                            rs.getString("reminder_date"),
                            rs.getString("reminder_time"),
                            rs.getBoolean("is_active"),
                            rs.getString("priority") != null ? Reminders.Priority.valueOf(rs.getString("priority")) : null,
                            rs.getString("content"),
                            rs.getInt("user_id")  // Get the user_id for reminder
                    ));
                }
            }
        }
        return reminders;
    }

    /**
     * Saves a reminder to the database for the user.
     *
     * @param connection the database connection
     * @param reminder   the reminder to save
     * @throws SQLException if a database error occurs
     */
    public void saveReminder(Connection connection, Reminders reminder) throws SQLException {
        // Check if the user exists
        String checkUserQuery = "SELECT COUNT(*) FROM users WHERE user_id = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkUserQuery)) {
            checkStmt.setInt(1, reminder.getUserId());
            try (ResultSet rs = checkStmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // User exists, now save the reminder
                    String query = "INSERT INTO reminders (user_id, task_name, reminder_date, reminder_time, priority, content, is_active) "
                            + "VALUES (?, ?, ?, ?, ?, ?, ?)";
                    try (PreparedStatement stmt = connection.prepareStatement(query)) {
                        stmt.setInt(1, reminder.getUserId());
                        stmt.setString(2, reminder.getTaskName());
                        stmt.setDate(3, Date.valueOf(reminder.getReminderDate()));
                        stmt.setTime(4, Time.valueOf(reminder.getReminderTime()));
                        stmt.setString(5, reminder.getPriority() != null ? reminder.getPriority().name() : null);
                        stmt.setString(6, reminder.getContent());
                        stmt.setBoolean(7, reminder.isActive());
                        stmt.executeUpdate();
                        System.out.println("Reminder saved successfully!");
                    }
                } else {
                    throw new SQLException("User with ID " + reminder.getUserId() + " does not exist.");
                }
            }
        }
    }

    @Override
    public String toString() {
        return "User [ID=" + userId + ", Username=" + username + "]";
    }
}
