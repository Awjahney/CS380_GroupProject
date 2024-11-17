import javax.swing.*;

public class GUI_1 {
    private JPanel guiPanel;
    private JScrollPane guiPane;
    private JButton previousWeekButton;
    private JButton goToRemindersButton;
    private JButton nextWeekButton;
    private JFrame guiFrame = new JFrame("Weekly Scheduler");

    public static void main(String[] args) {
        GUI_1 gui = new GUI_1();
        gui.guiPanel.setVisible(true);
        gui.guiFrame.setSize(600, 300);
        gui.guiFrame.setContentPane(gui.guiPanel);

        gui.guiFrame.setVisible(true);
    }
}
