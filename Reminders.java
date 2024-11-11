/**
 * Represents a reminder for a specific task within the Weekly Scheduler application.
 * Allows users to set reminders for important tasks to ensure timely completion.
 */
public class Reminders {

    private int reminderId;  // Unique identifier for the reminder
    private String taskName; // Name of the task associated with the reminder
    private String reminderDate; // Date for the reminder in YYYY-MM-DD format
    private String reminderTime; // Time for the reminder in HH:MM format
    private boolean isActive; // Status to check if the reminder is active

    /**
     * Constructs a new Reminders instance with the specified parameters.
     *
     * @param reminderId Unique identifier for the reminder
     * @param taskName Name of the task for which the reminder is set
     * @param reminderDate Date of the reminder in YYYY-MM-DD format
     * @param reminderTime Time of the reminder in HH:MM format
     * @param isActive Indicates whether the reminder is active
     */
    public Reminders(int reminderId, String taskName, String reminderDate, String reminderTime, boolean isActive) {
        this.reminderId = reminderId;
        this.taskName = taskName;
        this.reminderDate = reminderDate;
        this.reminderTime = reminderTime;
        this.isActive = isActive;
    }

    // Getters and Setters

    /** @return the reminder's unique identifier */
    public int getReminderId() {
        return reminderId;
    }

    /** @return the name of the associated task */
    public String getTaskName() {
        return taskName;
    }

    /** @return the date of the reminder */
    public String getReminderDate() {
        return reminderDate;
    }

    /** @return the time of the reminder */
    public String getReminderTime() {
        return reminderTime;
    }

    /** @return the active status of the reminder */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets the active status of the reminder.
     *
     * @param isActive true if the reminder should be active, false otherwise
     */
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    // Additional methods, such as toString() and other custom functions, could be added as needed
}
