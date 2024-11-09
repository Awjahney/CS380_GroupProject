import java.time.LocalDate;
import java.time.LocalTime;

/**
 * The Task class represents a user's task with details such as title, priority, date,
 * reminder time, and notes. It includes methods for updating task details.
 */
public class Task {

    private String title;
    private Priority priority;
    private LocalDate date;
    private LocalTime reminderTime;
    private String notes;

    /**
     * Constructs a Task with the specified title, priority, date, reminder time, and notes.
     *
     * @param title the title of the task
     * @param priority the priority level of the task
     * @param date the date assigned for the task
     * @param reminderTime the time to set a reminder for the task
     * @param notes additional notes about the task
     */
    public Task(String title, Priority priority, LocalDate date, LocalTime reminderTime, String notes) {
        this.title = title;
        this.priority = priority;
        this.date = date;
        this.reminderTime = reminderTime;
        this.notes = notes;
    }

    /**
     * Sets the reminder time for the task.
     *
     * @param reminderTime the new reminder time for the task
     */
    public void setReminder(LocalTime reminderTime) {
        this.reminderTime = reminderTime;
    }

    /**
     * Updates the priority level of the task.
     *
     * @param priority the new priority level for the task
     */
    public void updatePriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * Enum representing the priority levels of a task.
     */
    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    // Getters and setters can be added here as needed
}
