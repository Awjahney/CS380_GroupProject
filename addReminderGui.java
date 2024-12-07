import javax.swing.*;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * GUI for adding a new reminder to the user's schedule.
 * This class allows the user to input a task name, date, time, priority, and repeat options.
 */
public class addReminderGui {
    private JPanel guiPanel;
    private JButton backButton;
    private JButton addReminderButton;
    private JTextField reminderNameField;
    private JCheckBox lowCheckBox;
    private JCheckBox midCheckBox;
    private JCheckBox highCheckBox;
    private JTextField reminderDateField;
    private JTextField reminderTimeField;
    private JCheckBox repeatDailyCheckBox;
    private JFrame guiFrame = new JFrame("Add Reminder");

    private final String DB_URL = "jdbc:mysql://localhost:3306/scheduler_db";
    private final String DB_USER = "root";
    private final String DB_PASSWORD = "xynjeg-Hifmag-7quxty";
    private int userId;

    /**
     * Constructs an addReminderGui instance with the logged-in user's ID.
     *
     * @param userId The logged-in user's ID
     */
    public addReminderGui(int userId) {
        this.userId = userId;
    }

    /**
     * Sets up the GUI components and adds action listeners for buttons.
     */
    public void buildGuiPanel() {
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setSize(400, 300);
        guiFrame.setContentPane(guiPanel);
        guiFrame.setVisible(true);

        // Back to reminders page
        backButton.addActionListener(e -> {
            guiFrame.dispose();
            dailyscheduleGui remindersPage = new dailyscheduleGui(userId);
            remindersPage.buildGuiPanel();
        });

        // Add reminder
        addReminderButton.addActionListener(e -> addReminder());
    }

    /**
     * Adds a new reminder to the database with the provided information.
     * Validates the input fields and formats the date and time before saving to the database.
     */
    private void addReminder() {
        String taskName = reminderNameField.getText().trim();
        String dateInput = reminderDateField.getText().trim();
        String timeInput = reminderTimeField.getText().trim();
        String priority = getSelectedPriority();
        boolean repeatDaily = repeatDailyCheckBox.isSelected();

        // Validate required fields
        if (taskName.isEmpty() || dateInput.isEmpty() || timeInput.isEmpty()) {
            JOptionPane.showMessageDialog(guiFrame, "Task Name, Date, and Time are required.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convert the date and time formats
        String formattedDate = formatDate(dateInput);
        String formattedTime = formatTime(timeInput);

        if (formattedDate == null || formattedTime == null) {
            return; // If date or time formatting fails, stop the process
        }

        // Insert reminder into the database
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO reminders (user_id, task_name, reminder_date, reminder_time, priority, repeat_daily) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                stmt.setString(2, taskName);
                stmt.setString(3, formattedDate);
                stmt.setString(4, formattedTime);
                stmt.setString(5, priority);
                stmt.setBoolean(6, repeatDaily);

                stmt.executeUpdate();
                JOptionPane.showMessageDialog(guiFrame, "Reminder added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Reset fields after saving
                resetFields();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(guiFrame, "Error adding reminder: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Converts the input date string into the correct format for the database.
     *
     * @param dateInput The input date string (MM/DD/YYYY)
     * @return The formatted date string (YYYY-MM-DD), or null if invalid format
     */
    private String formatDate(String dateInput) {
        try {
            SimpleDateFormat inputDateFormat = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat dbDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = inputDateFormat.parse(dateInput);
            return dbDateFormat.format(date);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(guiFrame, "Invalid date format. Please use MM/DD/YYYY.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Converts the input time string into the correct format for the database.
     *
     * @param timeInput The input time string (hh:mm AM/PM)
     * @return The formatted time string (HH:mm:ss), or null if invalid format
     */
    private String formatTime(String timeInput) {
        try {
            SimpleDateFormat inputTimeFormat = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat dbTimeFormat = new SimpleDateFormat("HH:mm:ss");
            Date time = inputTimeFormat.parse(timeInput);
            return dbTimeFormat.format(time);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(guiFrame, "Invalid time format. Please use hh:mm AM/PM.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    /**
     * Retrieves the selected priority value from the checkboxes.
     *
     * @return The selected priority ("LOW", "MEDIUM", or "HIGH")
     */
    private String getSelectedPriority() {
        if (lowCheckBox.isSelected()) return "LOW";
        if (midCheckBox.isSelected()) return "MEDIUM";
        if (highCheckBox.isSelected()) return "HIGH";
        return null; // No priority selected
    }

    /**
     * Resets all input fields to their default values.
     */
    private void resetFields() {
        reminderNameField.setText("");
        reminderDateField.setText("");
        reminderTimeField.setText("");
        lowCheckBox.setSelected(false);
        midCheckBox.setSelected(false);
        highCheckBox.setSelected(false);
        repeatDailyCheckBox.setSelected(false);
    }
}
