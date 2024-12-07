import javax.swing.*;
import java.sql.*;

/**
 * This class represents a pop-up window that displays a reminder and provides options to confirm or delete it.
 * It connects to a MySQL database to delete the reminder when the delete button is clicked.
 *
 * @since 1.0
 */
public class reminderPopUp {

    private JPanel panel1;
    private JLabel reminderLabel;
    private JPanel TitleArea;
    private JTextArea reminderTextArea;
    private JButton confirmButton;
    private JButton deleteButton; // Button to delete the reminder
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JFrame guiFrame;

    private final String DB_URL = "jdbc:mysql://localhost:3306/scheduler_db"; // Update with your database URL
    private final String DB_USER = "root"; // Update with your DB username
    private final String DB_PASSWORD = "xynjeg-Hifmag-7quxty"; // Update with your DB password

    private int reminderId; // ID of the reminder to delete

    /**
     * Constructs a reminder pop-up window.
     *
     * @param reminderId The ID of the reminder to be deleted
     * @param reminderContent The content of the reminder to be displayed in the pop-up window
     * @since 1.0
     */
    public reminderPopUp(int reminderId, String reminderContent) {
        guiFrame = new JFrame("Reminder");
        guiFrame.setSize(300, 200);
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setContentPane(panel1);

        reminderTextArea.setText(reminderContent); // Set the reminder content in the text area

        // Add action listener to confirm button
        confirmButton.addActionListener(e -> guiFrame.dispose()); // Close the pop-up on button click

        // Add action listener to delete button
        deleteButton.addActionListener(e -> {
            deleteReminder(reminderId); // Delete the reminder
            guiFrame.dispose(); // Close the pop-up after deletion
        });

        guiFrame.setVisible(true);
    }

    /**
     * Deletes the reminder from the database based on the provided reminder ID.
     *
     * @param reminderId The ID of the reminder to be deleted
     * @throws SQLException If an SQL error occurs while deleting the reminder
     * @since 1.0
     */
    private void deleteReminder(int reminderId) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "DELETE FROM reminders WHERE reminder_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, reminderId);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(guiFrame, "Reminder deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(guiFrame, "Error deleting reminder: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
