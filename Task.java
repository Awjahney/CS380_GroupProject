import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a task in the Weekly Scheduler application with database integration support.
 */
public class Task {
    private int taskId;
    private String title;
    private Priority priority;
    private LocalDate date;
    private LocalTime reminderTime;
    private String notes;

    public Task(int taskId, String title, Priority priority, LocalDate date, LocalTime reminderTime, String notes) {
        this.taskId = taskId;
        this.title = title;
        this.priority = priority;
        this.date = date;
        this.reminderTime = reminderTime;
        this.notes = notes;
    }

    // Getters and Setters
    public int getTaskId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public Priority getPriority() {
        return priority;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getReminderTime() {
        return reminderTime;
    }

    public String getNotes() {
        return notes;
    }

    // Enum for priority levels
    public enum Priority {
        HIGH, MEDIUM, LOW
    }

    @Override
    public String toString() {
        return "Task [ID=" + taskId + ", Title=" + title + ", Priority=" + priority + ", Date=" + date +
                ", Reminder=" + reminderTime + ", Notes=" + notes + "]";
    }
}
