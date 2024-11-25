import javax.swing.*;

public class guiDriver {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Start the application with the login GUI
            loginGui loginPage = new loginGui();
            loginPage.buildGuiPanel();
        });
    }
}