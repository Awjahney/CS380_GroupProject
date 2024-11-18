import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a note attached to a specific day in the Weekly Scheduler application.
 * Allows users to add and manage additional information related to scheduled tasks.
 */
public class Notes {

    private int noteId;       // Unique identifier for the note
    private String dayOfWeek; // Day of the week the note is associated with
    private String content;   // Content of the note
    private String createdAt; // Creation date of the note in YYYY-MM-DD format

    /**
     * Constructs a new Notes instance with the specified parameters.
     *
     * @param noteId Unique identifier for the note.
     * @param dayOfWeek Day of the week the note is associated with.
     * @param content Content of the note.
     * @param createdAt Creation date of the note in YYYY-MM-DD format.
     */
    public Notes(int noteId, String dayOfWeek, String content, String createdAt) {
        setNoteId(noteId);
        setDayOfWeek(dayOfWeek);
        setContent(content);
        setCreatedAt(createdAt);
    }

    /**
     * @return the note's unique identifier.
     */
    public int getNoteId() {
        return noteId;
    }

    /**
     * Sets the note's unique identifier.
     *
     * @param noteId the new unique identifier for the note.
     */
    public void setNoteId(int noteId) {
        if (noteId <= 0) {
            throw new IllegalArgumentException("Note ID must be a positive integer.");
        }
        this.noteId = noteId;
    }

    /**
     * @return the day of the week associated with this note.
     */
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Sets the day of the week for the note. Validates the input.
     *
     * @param dayOfWeek the new day of the week.
     */
    public void setDayOfWeek(String dayOfWeek) {
        if (dayOfWeek == null || !dayOfWeek.matches("(?i)monday|tuesday|wednesday|thursday|friday|saturday|sunday")) {
            throw new IllegalArgumentException("Invalid day of the week.");
        }
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * @return the content of the note.
     */
    public String getContent() {
        return content;
    }

    /**
     * Updates the content of the note.
     *
     * @param content the new content for the note.
     */
    public void setContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Content cannot be null or empty.");
        }
        this.content = content;
    }

    /**
     * @return the creation date of the note in YYYY-MM-DD format.
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * Sets the creation date of the note. Validates the date format.
     *
     * @param createdAt the new creation date in YYYY-MM-DD format.
     */
    public void setCreatedAt(String createdAt) {
        if (createdAt == null || !createdAt.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("Invalid date format. Expected YYYY-MM-DD.");
        }
        this.createdAt = createdAt;
    }

    /**
     * Saves the note to the database.
     *
     * @param conn the database connection.
     * @throws SQLException if a database error occurs.
     */
    public void saveToDatabase(Connection conn) throws SQLException {
        String sql = "INSERT INTO notes (note_id, day_of_week, content, created_at) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, noteId);
            stmt.setString(2, dayOfWeek);
            stmt.setString(3, content);
            stmt.setString(4, createdAt);
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves all notes from the database.
     *
     * @param conn the database connection.
     * @return a list of notes.
     * @throws SQLException if a database error occurs.
     */
    public static List<Notes> getAllNotesFromDatabase(Connection conn) throws SQLException {
        List<Notes> notes = new ArrayList<>();
        String sql = "SELECT * FROM notes";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                notes.add(new Notes(
                    rs.getInt("note_id"),
                    rs.getString("day_of_week"),
                    rs.getString("content"),
                    rs.getString("created_at")
                ));
            }
        }
        return notes;
    }

    /**
     * Filters a list of notes by the specified day of the week.
     *
     * @param notes the list of notes to filter.
     * @param day the day of the week to filter by.
     * @return a list of notes that match the specified day.
     */
    public static List<Notes> filterNotesByDay(List<Notes> notes, String day) {
        return notes.stream()
                .filter(note -> note.getDayOfWeek().equalsIgnoreCase(day))
                .collect(Collectors.toList());
    }

    /**
     * @return a string representation of the note's details.
     */
    @Override
    public String toString() {
        return "Note ID: " + noteId + ", Day: " + dayOfWeek + ", Created At: " + createdAt + "\nContent: " + content;
    }
}
