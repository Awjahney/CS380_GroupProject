import javax.swing.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class dailyscheduleGui {
    private JPanel guiPanel2;
    private JScrollPane guiPane;
    private JButton addReminderButton;
    private JButton returnToWeeklyScheduleButton;
    private JButton viewReminders;
    private JButton removeReminderButton;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JTextArea textArea4;
    private JTextArea textArea5;
    private JTextArea textArea6;
    private JTextArea textArea7;
    private JTextArea textArea8;
    private JTextArea textArea9;
    private JTextArea textArea10;
    private JTextArea textArea11;
    private JTextArea textArea12;
    private JLabel Date; // Label to show the current day
    private JTextPane viewRemindersPane;
    private JTextArea textArea13;
    private JTextArea textArea14;
    private JFrame guiFrame2 = new JFrame("Reminders");

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
            guiFrame2.dispose(); // Close the Reminders window
            schedulerGui schedulerPage = new schedulerGui(userId); // Pass userId to schedulerGui
            schedulerPage.buildGuiPanel(); // Open the Scheduler GUI
        });

        // Action listener to navigate to Add Note GUI
        viewReminders.addActionListener(e -> {
            guiFrame2.dispose(); // Close the Reminders window
            addNoteGui addNotePage = new addNoteGui();
            addNotePage.buildGuiPanel(); // Open Add Note GUI
        });

        // Action listener to navigate to Edit Reminder GUI
        addReminderButton.addActionListener(e -> {
            guiFrame2.dispose(); // Close the Reminders window
            editReminderGui editReminderPage = new editReminderGui();
            editReminderPage.buildGuiPanel(); // Open Edit Reminder GUI
        });
    }

    private void updateDate() {
        LocalDate today = LocalDate.now(); // Get today's date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy"); // Format the date
        Date.setText("Today: " + today.format(formatter)); // Set the formatted date text
    }
}
