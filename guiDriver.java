/**
 * Weekly Scheduler Application - GUI Driver
 *
 * This class provides the graphical user interface for the weekly scheduler application.
 * It allows the user to navigate through weeks, view and edit daily tasks, and interact
 * with a database to load and save schedule data.
 *
 * @title Weekly Scheduler Application - GUI Driver
 * @author Ben Howell-Burke, Fisher Bachman-Rhodes, Awjahney Markholt
 * @version 1.0
 */

import javax.swing.*;

public class guiDriver {
    /**
     * The main method that starts the application by launching the login GUI.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Start the application with the login GUI
            loginGui loginPage = new loginGui();
            loginPage.buildGuiPanel();
        });
    }
}
