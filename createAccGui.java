import javax.swing.*;
import java.sql.*;

public class createAccGui {
    private JPanel guiPanel; // Main panel
    private JButton createAccountButton; // Button to create an account
    private JTextField usernameField; // Text field for username
    private JTextField passwordField; // Text field for password
    private JTextField accountIdField; // Text field for account ID
    private JButton backButton;
    private JFrame guiFrame = new JFrame("Create Account");

    // Database connection details
    private final String DB_URL = "jdbc:mysql://localhost:3306/scheduler_db"; // Update with your DB URL
    private final String DB_USER = "root"; // Update with your DB username
    private final String DB_PASSWORD = "ilovelife2093003!"; // Update with your DB password

    public void buildGuiPanel() {
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setSize(600, 300);
        guiFrame.setContentPane(guiPanel); // Use the main panel from the .form file
        guiFrame.setVisible(true);

        // Add action listener for the Create Account button
        createAccountButton.addActionListener(e -> createAccount());

        backButton.addActionListener(e -> {
            guiFrame.dispose(); // Close the create account GUI
            loginGui loginPage = new loginGui();
            loginPage.buildGuiPanel(); // Open the login GUI
        });
    }

    private void createAccount() {
        // Get input values
        String username = usernameField.getText();
        String password = passwordField.getText();
        String accountId = accountIdField.getText();

        // Validate inputs
        if (username.isEmpty() || password.isEmpty() || accountId.isEmpty()) {
            JOptionPane.showMessageDialog(guiFrame, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO users (account_id, username, password) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, accountId);
                stmt.setString(2, username);
                stmt.setString(3, password);
                stmt.executeUpdate();
                JOptionPane.showMessageDialog(guiFrame, "Account created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                // Return to Login GUI
                guiFrame.dispose();
                loginGui loginPage = new loginGui();
                loginPage.buildGuiPanel();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(guiFrame, "Error creating account: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
