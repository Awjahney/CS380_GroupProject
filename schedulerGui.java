import javax.swing.*;

public class schedulerGui {
    private JPanel guiPanel;
    private JButton previousWeekButton;
    private JButton goToRemindersButton;
    private JButton nextWeekButton;
    private JTextArea textArea1;
    private JTextArea textArea2;
    private JTextArea textArea3;
    private JTextArea textArea4;
    private JTextArea textArea5;
    private JTextArea textArea6;
    private JTextArea textArea7;
    private JFrame guiFrame = new JFrame("Weekly Scheduler");
    public void buildGuiPanel() {
        guiPanel.setVisible(true);
        guiFrame.setSize(1200, 300);
        guiFrame.setContentPane(guiPanel);
        guiFrame.setVisible(true);

        // Action listener to transition to the Reminders GUI
        goToRemindersButton.addActionListener(e -> {
            guiFrame.dispose();  // Close the scheduler window
            remindersGui remindersPage = new remindersGui();
            remindersPage.buildGuiPanel();  // Open the Reminders GUI
        });
    }


}