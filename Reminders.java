import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a reminder for a specific task within the Weekly Scheduler application.
 * Allows users to set reminders for important tasks to ensure timely completion.
 */
public class Reminders {

    private static int idCounter = 1;  // Static counter for generating unique IDs
    private int reminderId;           // Unique identifier for the reminder
    private String taskName;          // Name of the task associated with the reminder
    private String reminderDate;      // Date for the reminder in YYYY-MM-DD format
    private String reminderTime;      // Time for the reminder in HH:MM format
    private boolean isActive;         // Status to check if the reminder is active

    /**
     * Constructs a new Reminders instance with the specified parameters.
     *
     * @param taskName Name of the task for which the reminder is set.
     * @param reminderDate Date of the reminder in YYYY-MM-DD format.
     * @param reminderTime Time of the reminder in HH:MM format.
     * @param isActive Indicates whether the reminder is active.
     */
    public Reminders(String taskName, String reminderDate, String reminderTime, boolean isActive) {
        this.reminderId = generateReminderId();
        setTaskName(taskName);
        setReminderDate(reminderDate);
        setReminderTime(reminderTime);
        setActive(isActive);
    }

    /**
     * @return the reminder's unique identifier.
     */
    public int getReminderId() {
        return reminderId;
    }

    /**
     * @return the name of the associated task.
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * Sets the name of the associated task.
     *
     * @param taskName the name of the task.
     */
    public void setTaskName(String taskName) {
        if (taskName == null || taskName.trim().isEmpty()) {
            throw new IllegalArgumentException("Task name cannot be null or empty.");
        }
        this.taskName = taskName;
    }

    /**
     * @return the date of the reminder.
     */
    public String getReminderDate() {
        return reminderDate;
    }

    /**
     * Sets the reminder date. Validates the format.
     *
     * @param reminderDate the date of the reminder in YYYY-MM-DD format.
     */
    public void setReminderDate(String reminderDate) {
        if (reminderDate == null || !reminderDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Invalid date format. Expected YYYY-MM-DD.");
        }
        this.reminderDate = reminderDate;
    }

    /**
     * @return the time of the reminder.
     */
    public String getReminderTime() {
        return reminderTime;
    }

    /**
     * Sets the reminder time. Validates the format.
     *
     * @param reminderTime the time of the reminder in HH:MM format.
     */
    public void setReminderTime(String reminderTime) {
        if (reminderTime == null || !reminderTime.matches("\\d{2}:\\d{2}")) {
            throw new IllegalArgumentException("Invalid time format. Expected HH:MM.");
        }
        this.reminderTime = reminderTime;
    }

    /**
     * @return whether the reminder is active.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets the active status of the reminder.
     *
     * @param isActive true if the reminder should be active, false otherwise.
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Checks if the reminder should trigger based on the current date and time.
     *
     * @param currentDate the current date.
     * @param currentTime the current time.
     * @return true if the reminder should trigger, false otherwise.
     */
    public boolean shouldTriggerReminder(LocalDate currentDate, LocalTime currentTime) {
        LocalDate reminderLocalDate = LocalDate.parse(reminderDate);
        LocalTime reminderLocalTime = LocalTime.parse(reminderTime);
        return isActive && currentDate.equals(reminderLocalDate) && !currentTime.isBefore(reminderLocalTime);
    }

    /**
     * Saves the reminder to the database.
     *
     * @param conn the database connection.
     * @throws SQLException if a database error occurs.
     */
    public void saveToDatabase(Connection conn) throws SQLException {
        String sql = "INSERT INTO reminders (reminder_id, task_name, reminder_date, reminder_time, is_active) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reminderId);
            stmt.setString(2, taskName);
            stmt.setString(3, reminderDate);
            stmt.setString(4, reminderTime);
            stmt.setBoolean(5, isActive);
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves all reminders from the database.
     *
     * @param conn the database connection.
     * @return a list of reminders.
     * @throws SQLException if a database error occurs.
     */
    public static List<Reminders> getAllRemindersFromDatabase(Connection conn) throws SQLException {
        List<Reminders> reminders = new ArrayList<>();
        String sql = "SELECT * FROM reminders";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                reminders.add(new Reminders(
                        rs.getString("task_name"),
                        rs.getString("reminder_date"),
                        rs.getString("reminder_time"),
                        rs.getBoolean("is_active")
                ));
            }
        }
        return reminders;
    }

    /**
     * Generates a unique reminder ID.
     *
     * @return a unique identifier.
     */
    private static int generateReminderId() {
        return idCounter++;
    }

    /**
     * @return a string representation of the reminder's details.
     */
    @Override
    public String toString() {
        return "Reminder ID: " + reminderId + ", Task: " + taskName + ", Date: " + reminderDate + 
               ", Time: " + reminderTime + ", Active: " + isActive;
    }
}
