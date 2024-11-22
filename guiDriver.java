public class guiDriver {
    public static void main(String[] args) {
        //build login gui screen
        loginGui loginPage = new loginGui();
        loginPage.buildGuiPanel();
        remindersGui remindersPage = new remindersGui();
        remindersPage.buildGuiPanel();
        schedulerGui schedulerPage = new schedulerGui();
        schedulerPage.buildGuiPanel();

        
    }
}
