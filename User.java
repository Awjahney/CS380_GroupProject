import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;


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
     * Fetches all tasks for the user from the database.
     *
     * @param connection the database connection
     * @return a list of tasks for the user
     * @throws SQLException if a database error occurs
     */
    public List<Task> fetchTasks(Connection connection) throws SQLException {
        List<Task> tasks = new ArrayList<>();
        String query = "SELECT * FROM tasks WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, this.userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int taskId = rs.getInt("task_id");
                    String title = rs.getString("title");
                    Task.Priority priority = Task.Priority.valueOf(rs.getString("priority"));
                    LocalDate date = rs.getDate("date").toLocalDate();
                    LocalTime reminderTime = rs.getTime("reminder_time").toLocalTime();
                    String notes = rs.getString("notes");

                    tasks.add(new Task(taskId, title, priority, date, reminderTime, notes));
                }
            }
        }
        return tasks;
    }

    /**
     * Saves a task to the database for the user.
     *
     * @param connection the database connection
     * @param task the task to save
     * @throws SQLException if a database error occurs
     */
    public void saveTask(Connection connection, Task task) throws SQLException {
        String query = "INSERT INTO tasks (user_id, title, priority, date, reminder_time, notes) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, this.userId);
            stmt.setString(2, task.getTitle());
            stmt.setString(3, task.getPriority().name());
            stmt.setDate(4, Date.valueOf(task.getDate()));
            stmt.setTime(5, Time.valueOf(task.getReminderTime()));
            stmt.setString(6, task.getNotes());
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a task from the database.
     *
     * @param connection the database connection
     * @param taskId the ID of the task to delete
     * @throws SQLException if a database error occurs
     */
    public void deleteTask(Connection connection, int taskId) throws SQLException {
        String query = "DELETE FROM tasks WHERE task_id = ? AND user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, taskId);
            stmt.setInt(2, this.userId);
            stmt.executeUpdate();
        }
    }

    @Override
    public String toString() {
        return "User [ID=" + userId + ", Username=" + username + "]";
    }
}
