import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class WeeklySchedulerGUI {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public WeeklySchedulerGUI() {
        frame = new JFrame("Weekly Scheduler");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Add panels for each screen
        mainPanel.add(createMainScreen(), "Main");
        mainPanel.add(createReminderScreen(), "Reminders");
        mainPanel.add(createLoginScreen(), "Login");

        frame.setContentPane(mainPanel);
        frame.setSize(600, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private JPanel createMainScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton goToRemindersButton = new JButton("Go to Reminders");
        goToRemindersButton.addActionListener(e -> cardLayout.show(mainPanel, "Reminders"));

        panel.add(new JLabel("Weekly Scheduler"), BorderLayout.NORTH);
        panel.add(goToRemindersButton, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createReminderScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton returnToMainButton = new JButton("Return to Main");
        returnToMainButton.addActionListener(e -> cardLayout.show(mainPanel, "Main"));

        panel.add(new JLabel("Reminders Screen"), BorderLayout.NORTH);
        panel.add(returnToMainButton, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createLoginScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> cardLayout.show(mainPanel, "Main"));

        panel.add(new JLabel("Login Screen"), BorderLayout.NORTH);
        panel.add(loginButton, BorderLayout.SOUTH);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(WeeklySchedulerGUI::new);
    }
}
