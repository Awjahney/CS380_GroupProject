import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a reminder for a specific task in the Weekly Scheduler application.
 */
public class Reminders {
    private String taskName;
    private String reminderDate; // YYYY-MM-DD format
    private String reminderTime; // HH:MM:SS format
    private Priority priority; // Optional
    private String content; // For note-like descriptions
    private boolean isActive;
    private int userId; // To store the user ID

    // Enum for priority levels
    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    /**
     * Constructs a new Reminders instance.
     *
     * @param taskName      the name of the task
     * @param reminderDate  the date of the reminder
     * @param reminderTime  the time of the reminder
     * @param isActive      whether the reminder is active
     * @param priority      the priority level (optional)
     * @param content       additional notes or content (optional)
     * @param userId        the user ID who owns the reminder
     */
    public Reminders(String taskName, String reminderDate, String reminderTime, boolean isActive, Priority priority, String content, int userId) {
        setTaskName(taskName);
        setReminderDate(reminderDate);
        setReminderTime(reminderTime);
        setActive(isActive);
        setPriority(priority);
        setContent(content);
        setUserId(userId);
    }

    // Getters and setters
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        if (taskName == null || taskName.trim().isEmpty()) {
            throw new IllegalArgumentException("Task name cannot be null or empty.");
        }
        this.taskName = taskName;
    }

    public String getReminderDate() {
        return reminderDate;
    }

    public void setReminderDate(String reminderDate) {
        if (reminderDate == null || !reminderDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Invalid date format. Expected YYYY-MM-DD.");
        }
        this.reminderDate = reminderDate;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        if (reminderTime == null || !reminderTime.matches("\\d{2}:\\d{2}:\\d{2}")) {
            throw new IllegalArgumentException("Invalid time format. Expected HH:MM:SS.");
        }
        this.reminderTime = reminderTime;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Reminder [Task=" + taskName + ", Date=" + reminderDate + ", Time=" + reminderTime +
                ", Priority=" + (priority != null ? priority.name() : "N/A") +
                ", Notes=" + content + ", Active=" + isActive + ", User ID=" + userId + "]";
    }
}
