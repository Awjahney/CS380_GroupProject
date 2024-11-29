import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class addReminderGui {
    private JPanel guiPanel;
    private JButton backButton;
    private JButton addReminderButton;
    private JTextField reminderNameField; // Title field
    private JTextArea textArea1;  // Notes field
    private JCheckBox lowCheckBox;
    private JCheckBox midCheckBox;
    private JCheckBox highCheckBox;
    private JTextField reminderDateField;
    private JTextField reminderTimeField;
    private JFrame guiFrame = new JFrame("Edit Reminder");

    private final String DB_URL = "jdbc:mysql://localhost:3306/scheduler_db"; // Database URL
    private final String DB_USER = "root"; // Database username
    private final String DB_PASSWORD = "ilovelife2093003!"; // Database password
    private int userId;

    public addReminderGui(int userId) {
        this.userId = userId; // Pass userId to identify the logged-in user
    }

    public void buildGuiPanel() {
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setSize(400, 300);
        guiFrame.setContentPane(guiPanel);
        guiFrame.setVisible(true);

        // Back to reminders page
        backButton.addActionListener(e -> {
            guiFrame.dispose(); // Close Edit Reminder GUI
            dailyscheduleGui remindersPage = new dailyscheduleGui(userId); // Pass the correct userId
            remindersPage.buildGuiPanel();
        });

        // Add or edit reminder
        addReminderButton.addActionListener(e -> addReminder());
    }

    private void addReminder() {
        String taskName = reminderNameField.getText().trim();
        String dateInput = reminderDateField.getText().trim();
        String timeInput = reminderTimeField.getText().trim();
        String priority = getSelectedPriority();
        String notes = textArea1.getText().trim();

        if (taskName.isEmpty() || dateInput.isEmpty() || timeInput.isEmpty()) {
            JOptionPane.showMessageDialog(guiFrame, "Task Name, Date, and Time are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convert the date to the correct format
        String formattedDate;
        try {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = inputDateFormat.parse(dateInput);
            formattedDate = dbDateFormat.format(date);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(guiFrame, "Invalid date format. Please use MM/DD/YYYY.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convert the time to the correct format
        String formattedTime;
        try {
            SimpleDateFormat inputTimeFormat = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat dbTimeFormat = new SimpleDateFormat("HH:mm:ss");
            Date time = inputTimeFormat.parse(timeInput);
            formattedTime = dbTimeFormat.format(time);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(guiFrame, "Invalid time format. Please use hh:mm AM/PM.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO reminders (user_id, task_name, reminder_date, reminder_time, priority, content) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                stmt.setString(2, taskName);
                stmt.setString(3, formattedDate);
                stmt.setString(4, formattedTime);
                stmt.setString(5, priority);
                stmt.setString(6, notes);

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(guiFrame, "Reminder added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(guiFrame, "Error adding reminder: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getSelectedPriority() {
        if (lowCheckBox.isSelected()) return "LOW";
        if (midCheckBox.isSelected()) return "MEDIUM";
        if (highCheckBox.isSelected()) return "HIGH";
        return null; // No priority selected
    }
}
