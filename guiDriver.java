import javax.swing.*;

public class guiDriver {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //build login gui screen
            loginGui loginPage = new loginGui();
            loginPage.buildGuiPanel();
            remindersGui remindersPage = new remindersGui();
            remindersPage.buildGuiPanel();
            schedulerGui schedulerPage = new schedulerGui();
            schedulerPage.buildGuiPanel();
            createAccGui accPage = new createAccGui();
            accPage.buildGuiPanel();
            forgotInfoGui forgotInfoPage = new forgotInfoGui();
            forgotInfoPage.buildGuiPanel();
        });
    }
}
