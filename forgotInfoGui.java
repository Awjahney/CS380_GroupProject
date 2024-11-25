import javax.swing.*;
import java.sql.*;

public class forgotInfoGui {
    private JPanel guiPanel; // Main panel
    private JButton findAccountButton; // Button to find account
    private JTextArea textArea1; // Text area to display username/password
    private JTextField accountIdField; // Text field for Account ID input
    private JButton backToLoginButton; // Button to return to the login screen
    private JFrame guiFrame = new JFrame("Forgot Info");

    private final String DB_URL = "jdbc:mysql://localhost:3306/scheduler_db"; // Update with your DB URL
    private final String DB_USER = "root"; // Update with your DB username
    private final String DB_PASSWORD = "ilovelife2093003!"; // Update with your DB password

    public void buildGuiPanel() {
        // Initialize the GUI frame
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setSize(600, 300);
        guiFrame.setContentPane(guiPanel); // Use the main panel from the .form file
        guiFrame.setVisible(true);

        // Add action listener for the Find Account button
        findAccountButton.addActionListener(e -> findAccountInfo());

        // Add action listener for the Back to Login button
        backToLoginButton.addActionListener(e -> {
            guiFrame.dispose(); // Close Forgot Info GUI
            loginGui loginPage = new loginGui();
            loginPage.buildGuiPanel(); // Open Login GUI
        });
    }

    private void findAccountInfo() {
        String accountId = accountIdField.getText(); // Get Account ID from text field

        // Validate that the Account ID field is not empty
        if (accountId.isEmpty()) {
            JOptionPane.showMessageDialog(guiFrame, "Account ID cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Query the database to get the account info
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT username, password FROM users WHERE account_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, accountId);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        String username = rs.getString("username");
                        String password = rs.getString("password");

                        // Display the username and password in the text area
                        textArea1.setText("Username: " + username + "\nPassword: " + password);
                    } else {
                        JOptionPane.showMessageDialog(guiFrame, "No account found with the given Account ID.", "Error", JOptionPane.ERROR_MESSAGE);
                        textArea1.setText(""); // Clear the text area if no account is found
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(guiFrame, "Error retrieving account info: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
