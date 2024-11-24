import javax.swing.*;

public class forgotInfoGui {
    private JPanel guiPanel;
    private JButton findAccountButton;
    private JTextArea textArea1;
    private JFrame guiFrame = new JFrame("Weekly Scheduler");

    public void buildGuiPanel() {
        guiPanel.setVisible(true);
        guiFrame.setSize(600, 300);
        guiFrame.setContentPane(guiPanel);
        guiFrame.setVisible(true);
    }
}
