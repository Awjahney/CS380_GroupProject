import javax.swing.*;
import java.sql.*;

public class loginGui {
    private JPanel guiPanel;
    private JTextField usernameField;
    private JButton loginButton;
    private JButton createAccountButton;
    private JButton forgotUsernamePasswordButton;
    private JPasswordField passwordField; // Correctly use JPasswordField
    private JFrame guiFrame3 = new JFrame("Weekly Scheduler");

    private final String DB_URL = "jdbc:mysql://localhost:3306/scheduler_db"; // Update with your database URL
    private final String DB_USER = "root"; // Update with your DB username
    private final String DB_PASSWORD = "FlameBoy500!"; // Update with your DB password

    public void buildGuiPanel() {
        guiFrame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame3.setSize(600, 300);
        guiFrame3.setContentPane(guiPanel);
        guiFrame3.setVisible(true);

        // Add action listeners for buttons
        loginButton.addActionListener(e -> login());
        createAccountButton.addActionListener(e -> navigateToCreateAccount());
        forgotUsernamePasswordButton.addActionListener(e -> navigateToForgotInfo());
    }

    private void navigateToCreateAccount() {
        guiFrame3.dispose(); // Close login GUI
        createAccGui accPage = new createAccGui();
        accPage.buildGuiPanel(); // Open the Create Account GUI
    }

    private void navigateToForgotInfo() {
        guiFrame3.dispose(); // Close login GUI
        forgotInfoGui forgotPage = new forgotInfoGui();
        forgotPage.buildGuiPanel(); // Open the Forgot Info GUI
    }

    private void login() {
        String username = usernameField.getText();
        // Get the password from JPasswordField
        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars); // Convert char[] to String

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(guiFrame3, "Username and password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT user_id FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int userId = rs.getInt("user_id"); // Get the user ID
                        JOptionPane.showMessageDialog(guiFrame3, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        guiFrame3.dispose();  // Close the login window

                        // Pass the userId to the scheduler GUI
                        schedulerGui schedulerPage = new schedulerGui(userId);
                        schedulerPage.buildGuiPanel(); // Transition to scheduler page
                    } else {
                        JOptionPane.showMessageDialog(guiFrame3, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(guiFrame3, "Error logging in: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
