import javax.swing.*;

public class editReminderGui {
    private JPanel guiPanel;
    private JButton backToRemindersButton;
    private JButton editReminderButton;
    private JTextField reminderNameField; // Title field
    private JTextArea textArea1;  // Notes field
    private JCheckBox lowCheckBox;
    private JCheckBox midCheckBox;
    private JCheckBox highCheckBox;
    private JTextField reminderDateField;
    private JTextField reminderTimeField;
    private JFrame guiFrame = new JFrame("Edit Reminder");

    public void buildGuiPanel() {
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setSize(400, 300);
        guiFrame.setContentPane(guiPanel);
        guiFrame.setVisible(true);

        backToRemindersButton.addActionListener(e -> {
            guiFrame.dispose(); // Close Edit Reminder GUI
            dailyscheduleGui remindersPage = new dailyscheduleGui(1); // Replace 1 with the correct userId
            remindersPage.buildGuiPanel();
        });

        editReminderButton.addActionListener(e -> {
            String reminderTitle = reminderNameField.getText().trim();
            String reminderDetails = textArea1.getText().trim();

            if (reminderTitle.isEmpty()) {
                // Show error if Title field is empty
                JOptionPane.showMessageDialog(guiFrame, "Title field cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Process the reminder update
                JOptionPane.showMessageDialog(guiFrame, "Reminder updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                guiFrame.dispose(); // Close Edit Reminder GUI
                dailyscheduleGui remindersPage = new dailyscheduleGui(1); // Replace 1 with the correct userId
                remindersPage.buildGuiPanel();
            }
        });
    }
}
