import javax.swing.*;

public class GUI_1 {
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

    public static void main(String[] args) {
        GUI_1 gui = new GUI_1();
        gui.guiPanel.setVisible(true);
        gui.guiFrame.setSize(600, 300);
        gui.guiFrame.setContentPane(gui.guiPanel);

        gui.guiFrame.setVisible(true);
    }
}
