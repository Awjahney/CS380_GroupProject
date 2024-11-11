/**
 * Represents a note attached to a specific day in the Weekly Scheduler application.
 * Allows users to add and manage additional information related to scheduled tasks.
 */
public class Notes {

    private int noteId;       // Unique identifier for the note
    private String dayOfWeek; // Day of the week to which the note is associated
    private String content;   // Content of the note
    private String createdAt; // Creation date of the note in YYYY-MM-DD format

    /**
     * Constructs a new Notes instance with the specified parameters.
     *
     * @param noteId Unique identifier for the note
     * @param dayOfWeek Day of the week the note is associated with
     * @param content Content of the note
     * @param createdAt Creation date of the note in YYYY-MM-DD format
     */
    public Notes(int noteId, String dayOfWeek, String content, String createdAt) {
        this.noteId = noteId;
        this.dayOfWeek = dayOfWeek;
        this.content = content;
        this.createdAt = createdAt;
    }

    // Getters and Setters

    /** @return the note's unique identifier */
    public int getNoteId() {
        return noteId;
    }

    /** @return the day of the week associated with this note */
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    /** @return the content of the note */
    public String getContent() {
        return content;
    }

    /** @return the creation date of the note */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Updates the content of the note.
     *
     * @param content New content for the note
     */
    public void setContent(String content) {
        this.content = content;
    }

    // Additional methods, such as toString() and other custom functions, could be added as needed
}