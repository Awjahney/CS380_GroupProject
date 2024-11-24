import javax.swing.*;

public class remindersGui {
    private JPanel guiPanel2;
    private JScrollPane guiPane;
    private JButton addReminderButton;
    private JButton returnToWeeklyScheduleButton;
    private JButton addNoteButton;
    private JButton removeReminderButton;
    private JButton setPriorityLevelButton;
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
    private JFrame guiFrame2 = new JFrame("Weekly Scheduler");
    public void buildGuiPanel() {
        guiPanel2.setVisible(true);
        guiFrame2.setSize(600, 900);
        guiFrame2.setContentPane(guiPanel2);
        guiFrame2.setVisible(true);

        returnToWeeklyScheduleButton.addActionListener(e -> {
            guiFrame2.dispose();  // Close the Reminders window
            schedulerGui schedulerPage = new schedulerGui();
            schedulerPage.buildGuiPanel();  // Open the Scheduler GUI
        });
    }
}