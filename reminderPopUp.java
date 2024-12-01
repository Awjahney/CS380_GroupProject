import javax.swing.*;
import java.sql.*;

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

    private final String DB_URL = "jdbc:mysql://localhost:3306/scheduler_db"; // Database URL
    private final String DB_USER = "root"; // Database username
    private final String DB_PASSWORD = "ilovelife2093003!"; // Database password

    private int reminderId; // ID of the reminder to delete

    public reminderPopUp(int reminderId, String reminderContent) {
        guiFrame = new JFrame("Reminder");
        guiFrame.setSize(300, 200);
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setContentPane(panel1);

        reminderTextArea.setText(reminderContent); // Set the reminder content in the text area

        confirmButton.addActionListener(e -> guiFrame.dispose()); // Close the pop-up on button click

        deleteButton.addActionListener(e -> {
            deleteReminder(reminderId); // Delete the reminder
            guiFrame.dispose(); // Close the pop-up after deletion
        });

        guiFrame.setVisible(true);
    }

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
