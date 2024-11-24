import javax.swing.*;

public class guiDriver {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            loginGui loginPage = new loginGui();
            loginPage.buildGuiPanel();
        });
    }
}
