import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class schedulerGui {
    private JPanel guiPanel; // Main panel
    private JButton previousWeekButton; // Navigate to previous week
    private JButton goToRemindersButton; // Navigate to reminders GUI
    private JButton nextWeekButton; // Navigate to next week
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
    private final String DB_URL = "jdbc:mysql://localhost:3306/scheduler_db"; // Update with your DB URL
    private final String DB_USER = "root"; // Update with your DB username
    private final String DB_PASSWORD = "ilovelife2093003!"; // Update with your DB password

    public schedulerGui(int userId) {
        this.userId = userId; // Pass the logged-in user's ID
    }

    public void buildGuiPanel() {
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setSize(1200, 400);
        guiFrame.setContentPane(guiPanel);
        guiFrame.setVisible(true);

        updateDayLabels(); // Initialize day labels with current dates
        loadDailyData(); // Load any existing data for the current week

        // Action listener for navigating to the Reminders GUI
        goToRemindersButton.addActionListener(e -> {
            saveDailyData(); // Save data before switching screens
            guiFrame.dispose(); // Close the scheduler window
            remindersGui remindersPage = new remindersGui(userId);
            remindersPage.buildGuiPanel(); // Open the Reminders GUI
        });

        // Action listeners for navigating weeks
        previousWeekButton.addActionListener(e -> {
            saveDailyData(); // Save current week's data
            startDate = startDate.minusWeeks(1); // Move to the previous week
            updateDayLabels(); // Update the displayed dates
            loadDailyData(); // Load data for the new week
        });

        nextWeekButton.addActionListener(e -> {
            saveDailyData(); // Save current week's data
            startDate = startDate.plusWeeks(1); // Move to the next week
            updateDayLabels(); // Update the displayed dates
            loadDailyData(); // Load data for the new week
        });
    }

    private void updateDayLabels() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");
        for (int i = 0; i < 7; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            JTextArea textArea = getTextAreaForDay(i);
            textArea.setBorder(BorderFactory.createTitledBorder(currentDate.format(formatter)));
        }
    }

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
