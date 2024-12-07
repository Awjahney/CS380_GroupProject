import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The schedulerGui class provides the user interface for the weekly scheduler application.
 * It allows the user to navigate through weeks, view and edit daily tasks, and interact
 * with a database to load and save schedule data.
 *
 * @since 1.0
 * @version 1.0
 */
public class schedulerGui {
    private JPanel guiPanel;
    private JButton previousWeekButton;
    private JButton goToDailyScheduleButton;
    private JButton nextWeekButton;
    private JTextArea SundayTextArea;
    private JTextArea MondayTextArea;
    private JTextArea TuesdayTextArea;
    private JTextArea WednesdayTextArea;
    private JTextArea ThursdayTextArea;
    private JTextArea FridayTextArea;
    private JTextArea SaturdayTextArea;
    private JFrame guiFrame = new JFrame("Weekly Scheduler");

    private LocalDate startDate = LocalDate.now().with(java.time.DayOfWeek.SUNDAY); // Start of the current week
    private int userId; // Logged-in user's ID
    private final String DB_URL = "jdbc:mysql://localhost:3306/scheduler_db"; // Database URL
    private final String DB_USER = "root"; // Database username
    private final String DB_PASSWORD = "xynjeg-Hifmag-7quxty"; // Database password

    /**
     * Constructs a schedulerGui instance for a specific user.
     *
     * @param userId The ID of the logged-in user.
     * @since 1.0
     */
    public schedulerGui(int userId) {
        this.userId = userId;
    }

    /**
     * Initializes the graphical user interface, including buttons, panels, and actions.
     * Sets up action listeners for navigating between weeks and going to the daily schedule view.
     *
     * @since 1.0
     */
    public void buildGuiPanel() {
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setSize(1200, 400);
        guiFrame.setContentPane(guiPanel);
        guiFrame.setVisible(true);

        updateDayLabels(); // Set labels with current week's dates
        loadDailyData(); // Load data for the current week

        // Action listener for navigating to the Reminders GUI
        goToDailyScheduleButton.addActionListener(e -> {
            saveDailyData(); // Save data before switching screens
            guiFrame.dispose(); // Close the Scheduler GUI
            dailyscheduleGui remindersPage = new dailyscheduleGui(userId);
            remindersPage.buildGuiPanel(); // Open the Reminders GUI
        });

        // Navigate to the previous week
        previousWeekButton.addActionListener(e -> {
            saveDailyData(); // Save the current week's data
            startDate = startDate.minusWeeks(1); // Move to the previous week
            updateDayLabels(); // Update the displayed dates
            clearTextFields(); // Clear the text fields
            loadDailyData(); // Load data for the new week
        });

        // Navigate to the next week
        nextWeekButton.addActionListener(e -> {
            saveDailyData(); // Save the current week's data
            startDate = startDate.plusWeeks(1); // Move to the next week
            updateDayLabels(); // Update the displayed dates
            clearTextFields(); // Clear the text fields
            loadDailyData(); // Load data for the new week
        });
    }

    /**
     * Updates the labels on the GUI to display the dates for the current week.
     *
     * @since 1.0
     */
    private void updateDayLabels() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
        for (int i = 0; i < 7; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            JTextArea textArea = getTextAreaForDay(i);
            textArea.setBorder(BorderFactory.createTitledBorder(currentDate.format(formatter)));
        }
    }

    /**
     * Saves the user's daily schedule data to the database for the current week.
     * This method saves data for each day of the week (Sunday to Saturday).
     *
     * @since 1.0
     */
    private void saveDailyData() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "REPLACE INTO scheduler_data (user_id, date, content) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                for (int i = 0; i < 7; i++) {
                    LocalDate currentDate = startDate.plusDays(i);
                    JTextArea textArea = getTextAreaForDay(i);
                    stmt.setInt(1, userId);
                    stmt.setDate(2, Date.valueOf(currentDate));
                    stmt.setString(3, textArea.getText());
                    stmt.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(guiFrame, "Error saving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Loads the user's schedule data from the database for the current week.
     * This method populates the text areas with the saved content for each day of the week.
     *
     * @since 1.0
     */
    private void loadDailyData() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT date, content FROM scheduler_data WHERE user_id = ? AND date BETWEEN ? AND ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                stmt.setDate(2, Date.valueOf(startDate));
                stmt.setDate(3, Date.valueOf(startDate.plusDays(6)));

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        LocalDate date = rs.getDate("date").toLocalDate();
                        String content = rs.getString("content");
                        int dayIndex = date.getDayOfWeek().getValue() % 7; // Convert to Sunday as 0
                        JTextArea textArea = getTextAreaForDay(dayIndex);
                        textArea.setText(content);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(guiFrame, "Error loading data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Clears all text areas for the current week, removing any user input.
     *
     * @since 1.0
     */
    private void clearTextFields() {
        SundayTextArea.setText("");
        MondayTextArea.setText("");
        TuesdayTextArea.setText("");
        WednesdayTextArea.setText("");
        ThursdayTextArea.setText("");
        FridayTextArea.setText("");
        SaturdayTextArea.setText("");
    }

    /**
     * Returns the JTextArea corresponding to a specific day of the week.
     *
     * @param dayIndex The day index (0 for Sunday, 1 for Monday, ..., 6 for Saturday).
     * @return The JTextArea corresponding to the specified day.
     * @throws IllegalArgumentException if the dayIndex is invalid (not between 0 and 6).
     * @since 1.0
     */
    private JTextArea getTextAreaForDay(int dayIndex) {
        switch (dayIndex) {
            case 0: return SundayTextArea;
            case 1: return MondayTextArea;
            case 2: return TuesdayTextArea;
            case 3: return WednesdayTextArea;
            case 4: return ThursdayTextArea;
            case 5: return FridayTextArea;
            case 6: return SaturdayTextArea;
            default: throw new IllegalArgumentException("Invalid day index: " + dayIndex);
        }
    }
}
