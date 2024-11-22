import javax.swing.*;

public class loginGui {
    private JPanel guiPanel;
    private JTextField textField1;
    private JButton loginButton;
    private JButton createAccountButton;
    private JButton forgotUsernamePasswordButton;
    private JFrame guiFrame3 = new JFrame("Weekly Scheduler");

    public void buildGuiPanel() {
        guiPanel.setVisible(true);
        guiFrame3.setSize(600, 300);
        guiFrame3.setContentPane(guiPanel);
        guiFrame3.setVisible(true);
    }
}
