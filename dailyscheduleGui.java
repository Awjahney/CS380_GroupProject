import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class dailyscheduleGui {
    private JPanel guiPanel2;
    private JScrollPane guiPane;
    private JButton addReminderButton;
    private JButton returnToWeeklyScheduleButton;
    private JButton viewRemindersButton;
    private JButton removeReminderButton;
    private JTextArea textArea6PM;
    private JTextArea textArea4PM;
    private JTextArea textArea5PM;
    private JTextArea textArea3PM;
    private JTextArea textArea7PM;
    private JTextArea textArea8PM;
    private JTextArea textArea9AM;
    private JTextArea textArea8AM;
    private JTextArea textArea7AM;
    private JTextArea textArea10AM;
    private JTextArea textArea2PM;
    private JTextArea textArea6AM;
    private JLabel Date; // Label to show the current day
    private JTextPane viewRemindersPane;
    private JTextArea textArea1PM;
    private JTextArea textArea12PM;
    private JTextArea textArea11AM;
    private JFrame guiFrame2 = new JFrame("Daily Schedule");

    private final String DB_URL = "jdbc:mysql://localhost:3306/scheduler_db"; // Update with your database URL
    private final String DB_USER = "root"; // Update with your DB username
    private final String DB_PASSWORD = "ilovelife2093003!"; // Update with your DB password

    private int userId; // Logged-in user's ID

    public dailyscheduleGui(int userId) {
        this.userId = userId;
    }

    public void buildGuiPanel() {
        guiFrame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame2.setSize(600, 900);
        guiFrame2.setContentPane(guiPanel2);

        // Set the current day at the top
        updateDate();

        guiFrame2.setVisible(true);

        // Action listener to return to the weekly schedule
        returnToWeeklyScheduleButton.addActionListener(e -> {
            guiFrame2.dispose(); // Close the Daily Schedule GUI
            schedulerGui schedulerPage = new schedulerGui(userId); // Pass userId to schedulerGui
            schedulerPage.buildGuiPanel(); // Open the Scheduler GUI
        });

        // Action listener to navigate to Edit Reminder GUI
        addReminderButton.addActionListener(e -> {
            guiFrame2.dispose(); // Close the Daily Schedule GUI
            addReminderGui editReminderPage = new addReminderGui(userId);
            editReminderPage.buildGuiPanel(); // Open Edit Reminder GUI
        });

        // Action listener to display reminders
        viewRemindersButton.addActionListener(e -> displayReminders());
    }

    private void updateDate() {
        LocalDate today = LocalDate.now(); // Get today's date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"); // Format the date
        Date.setText("Today: " + today.format(formatter)); // Set the formatted date text
    }

    private void displayReminders() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT task_name, reminder_date, reminder_time, priority, content " +
                    "FROM reminders WHERE user_id = ? ORDER BY reminder_date, reminder_time";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);

                try (ResultSet rs = stmt.executeQuery()) {
                    StringBuilder reminders = new StringBuilder();
                    while (rs.next()) {
                        reminders.append("Task: ").append(rs.getString("task_name")).append("\n")
                                .append("Date: ").append(rs.getString("reminder_date")).append("\n")
                                .append("Time: ").append(rs.getString("reminder_time")).append("\n")
                                .append("Priority: ").append(rs.getString("priority")).append("\n")
                                .append("Notes: ").append(rs.getString("content")).append("\n\n");
                    }
                    viewRemindersPane.setText(reminders.toString());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(guiFrame2, "Error loading reminders: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
