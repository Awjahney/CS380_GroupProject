import javax.swing.*;

public class GUI_2 {
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

    public static void main(String[] args) {
        GUI_2 gui = new GUI_2();
        gui.guiPanel2.setVisible(true);
        gui.guiFrame2.setSize(600, 300);
        gui.guiFrame2.setContentPane(gui.guiPanel2);

        gui.guiFrame2.setVisible(true);
    }
}
