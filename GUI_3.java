import javax.swing.*;

public class GUI_3 {
    private JPanel guiPanel;
    private JTextField textField1;
    private JButton loginButton;
    private JButton createAccountButton;
    private JButton forgotUsernamePasswordButton;
    private JFrame guiFrame3 = new JFrame("Weekly Scheduler Login");

    public static void main(String[] args) {
        GUI_3 gui = new GUI_3();
        gui.guiPanel.setVisible(true);
        gui.guiFrame3.setSize(600, 300);
        gui.guiFrame3.setContentPane(gui.guiPanel);

        gui.guiFrame3.setVisible(true);
    }
}
