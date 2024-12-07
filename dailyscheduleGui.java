import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The dailyscheduleGui class represents the graphical user interface for the daily schedule.
 * It allows users to view, add, and remove reminders for the day.
 *
 * @since 1.0
 */
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
    private JTextField reminderIdField;
    private JLabel reminderIDLabel;
    private JFrame guiFrame2 = new JFrame("Daily Schedule");

    private final String DB_URL = "jdbc:mysql://localhost:3306/scheduler_db"; // Update with your database URL
    private final String DB_USER = "root"; // Update with your DB username
    private final String DB_PASSWORD = "xynjeg-Hifmag-7quxty"; // Update with your DB password

    private int userId; // Logged-in user's ID

    /**
     * Constructor for dailyscheduleGui.
     * Initializes the user ID and starts the reminder checker.
     *
     * @param userId The ID of the logged-in user.
     * @since 1.0
     */
    public dailyscheduleGui(int userId) {
        this.userId = userId;
        startReminderChecker(); // Start checking for reminders
    }

    /**
     * Builds the graphical user interface for the daily schedule page.
     * Sets up the layout, buttons, and event listeners for the user interface.
     *
     * @since 1.0
     */
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

        // Action listener to navigate to Add Reminder GUI
        addReminderButton.addActionListener(e -> {
            guiFrame2.dispose(); // Close the Daily Schedule GUI
            addReminderGui addReminderPage = new addReminderGui(userId);
            addReminderPage.buildGuiPanel(); // Open Add Reminder GUI
        });

        // Action listener to display reminders
        viewRemindersButton.addActionListener(e -> displayReminders());

        // Action listener to remove reminders
        removeReminderButton.addActionListener(e -> removeReminder());
    }

    /**
     * Updates the date label with the current date in the format: "Today: Day, Month Date, Year".
     *
     * @since 1.0
     */
    private void updateDate() {
        LocalDate today = LocalDate.now(); // Get today's date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"); // Format the date
        Date.setText("Today: " + today.format(formatter)); // Set the formatted date text
    }

    /**
     * Displays the list of reminders for the user from the database.
     * Each reminder includes details such as task name, date, time, and priority.
     *
     * @since 1.0
     * @see #displayReminderContent(ResultSet)
     */
    private void displayReminders() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT reminder_id, task_name, reminder_date, reminder_time, priority, repeat_daily " +
                    "FROM reminders WHERE user_id = ? ORDER BY reminder_date, reminder_time";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);

                try (ResultSet rs = stmt.executeQuery()) {
                    StringBuilder reminders = new StringBuilder();
                    while (rs.next()) {
                        reminders.append("Reminder ID: ").append(rs.getInt("reminder_id")).append("\n")
                                .append("Task: ").append(rs.getString("task_name")).append("\n")
                                .append("Date: ").append(rs.getString("reminder_date")).append("\n")
                                .append("Time: ").append(rs.getString("reminder_time")).append("\n")
                                .append("Priority: ").append(rs.getString("priority")).append("\n")
                                .append("Repeats Daily: ").append(rs.getBoolean("repeat_daily") ? "Yes" : "No").append("\n\n");
                    }
                    viewRemindersPane.setText(reminders.toString());
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(guiFrame2, "Error loading reminders: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Removes a reminder based on the reminder ID input by the user.
     * The reminder is deleted from the database.
     *
     * @since 1.0
     * @throws NumberFormatException if the input reminder ID is not a valid number.
     */
    private void removeReminder() {
        String reminderIdInput = reminderIdField.getText().trim();
        if (reminderIdInput.isEmpty()) {
            JOptionPane.showMessageDialog(guiFrame2, "Please enter a Reminder ID to remove.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "DELETE FROM reminders WHERE reminder_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, Integer.parseInt(reminderIdInput));
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(guiFrame2, "Reminder removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(guiFrame2, "No reminder found with the given ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(guiFrame2, "Error removing reminder: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Starts a background thread that checks for reminders every minute.
     * The reminders are checked for the current time and shown to the user if any are due.
     *
     * @since 1.0
     * @see #checkForReminders()
     */
    private void startReminderChecker() {
        Timer timer = new Timer(true); // Run the timer as a daemon thread
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                checkForReminders();
            }
        }, 0, 60 * 1000); // Check every minute
    }

    /**
     * Checks for reminders that are due based on the current date and time.
     * If any reminders are due, they are displayed in a pop-up window.
     *
     * @since 1.0
     */
    private void checkForReminders() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        String currentDate = now.format(dateFormatter);
        String currentTime = now.format(timeFormatter);

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT reminder_id, task_name, reminder_date, reminder_time, priority, repeat_daily " +
                    "FROM reminders WHERE user_id = ? AND reminder_date = ? AND reminder_time = ? AND is_active = TRUE";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                stmt.setString(2, currentDate);
                stmt.setString(3, currentTime);

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        int reminderId = rs.getInt("reminder_id");
                        String taskName = rs.getString("task_name");
                        String priority = rs.getString("priority");
                        boolean repeatDaily = rs.getBoolean("repeat_daily");

                        // Format the reminder content
                        String reminderContent = "Task: " + taskName + "\nPriority: " + priority;

                        // Display the reminder pop-up
                        new reminderPopUp(reminderId, reminderContent); // Pass reminder ID and content

                        // Handle repeat logic
                        if (repeatDaily) {
                            scheduleNextDayReminder(conn, reminderId, rs);
                        } else {
                            markReminderAsInactive(conn, reminderId);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Schedules the reminder for the next day if it repeats daily.
     *
     * @param conn The database connection.
     * @param reminderId The ID of the reminder.
     * @param rs The result set containing reminder data.
     * @throws SQLException If an SQL exception occurs.
     * @since 1.0
     */
    private void scheduleNextDayReminder(Connection conn, int reminderId, ResultSet rs) throws SQLException {
        try {
            String reminderDate = rs.getString("reminder_date");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate nextDay = LocalDate.parse(reminderDate, formatter).plusDays(1);

            String sql = "UPDATE reminders SET reminder_date = ? WHERE reminder_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nextDay.toString());
                stmt.setInt(2, reminderId);
                stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Marks a reminder as inactive after it has been shown.
     *
     * @param conn The database connection.
     * @param reminderId The ID of the reminder.
     * @throws SQLException If an SQL exception occurs.
     * @since 1.0
     */
    private void markReminderAsInactive(Connection conn, int reminderId) throws SQLException {
        String sql = "UPDATE reminders SET is_active = FALSE WHERE reminder_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, reminderId);
            stmt.executeUpdate();
        }
    }

    /**
     * Shows a reminder pop-up with the reminder details.
     *
     * @param reminderId The ID of the reminder.
     * @param reminderContent The content of the reminder.
     * @since 1.0
     */
    private void reminderPopUp(int reminderId, String reminderContent) {
        JOptionPane.showMessageDialog(guiFrame2, reminderContent, "Reminder", JOptionPane.INFORMATION_MESSAGE);
    }
}
