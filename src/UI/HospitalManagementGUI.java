package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class HospitalManagementGUI extends JFrame {
    private JComboBox<String> userTypeComboBox;

    private JPanel patientLoginPanel;
    private JLabel patientNameLabel;
    private JLabel patientPasswordLabel;
    private JTextField patientNameField;
    private JPasswordField patientPasswordField;
    private JButton patientLoginButton;

    private JPanel workerLoginPanel;
    private JLabel workerIdLabel;
    private JLabel workerPasswordLabel;
    private JLabel workerRoleLabel;
    private JTextField workerIdField;
    private JPasswordField workerPasswordField;
    private JComboBox<String> workerRoleComboBox;
    private JButton workerLoginButton;

    private Map<String, String> patientCredentials;
    private Map<String, String> workerCredentials;

    public HospitalManagementGUI() {
        // Set up the JFrame
        setTitle("Hospital Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Set page background color
        getContentPane().setBackground(new Color(250, 128, 114)); // Salmon color

        // Create header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(250, 128, 114)); // Salmon color

        JLabel headerLabel = new JLabel("<html><div style='text-align: center;'>Welcome to the hospital management system!<br>Please select your role to access your login page:</div></html>");
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        // Create the user type selection dropdown
        userTypeComboBox = new JComboBox<>(new String[]{"Patient", "Worker"});
        userTypeComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        userTypeComboBox.setMaximumSize(new Dimension(200, 20)); // Set maximum size for height
        userTypeComboBox.setPreferredSize(new Dimension(200, 20)); // Set preferred size for height
        userTypeComboBox.setMinimumSize(new Dimension(200, 20)); // Set minimum size for height

        // Create the patient login panel
        patientLoginPanel = new JPanel();
        patientLoginPanel.setLayout(new BoxLayout(patientLoginPanel, BoxLayout.Y_AXIS));
        patientNameLabel = new JLabel("Full Name:");
        patientLoginPanel.add(patientNameLabel);
        patientNameField = new JTextField(20);
        patientNameField.setMaximumSize(new Dimension(200, 30));
        patientLoginPanel.add(patientNameField);
        patientPasswordLabel = new JLabel("Password:");
        patientLoginPanel.add(patientPasswordLabel);
        patientPasswordField = new JPasswordField(20);
        patientPasswordField.setMaximumSize(new Dimension(200, 30));
        patientLoginPanel.add(patientPasswordField);
        patientLoginButton = new JButton("Login");
        patientLoginPanel.add(patientLoginButton);

        // Create the worker login panel
        workerLoginPanel = new JPanel();
        workerLoginPanel.setLayout(new BoxLayout(workerLoginPanel, BoxLayout.Y_AXIS));
        workerIdLabel = new JLabel("Worker ID:");
        workerLoginPanel.add(workerIdLabel);
        workerIdField = new JTextField(20);
        workerIdField.setMaximumSize(new Dimension(200, 30));
        workerLoginPanel.add(workerIdField);
        workerPasswordLabel = new JLabel("Password:");
        workerLoginPanel.add(workerPasswordLabel);
        workerPasswordField = new JPasswordField(20);
        workerPasswordField.setMaximumSize(new Dimension(200, 30));
        workerLoginPanel.add(workerPasswordField);
        workerRoleLabel = new JLabel("Role:");
        workerLoginPanel.add(workerRoleLabel);
        workerRoleComboBox = new JComboBox<>(new String[]{"Doctor", "Nurse"});
        workerRoleComboBox.setMaximumSize(new Dimension(200, 30));
        workerLoginPanel.add(workerRoleComboBox);
        workerLoginButton = new JButton("Login");
        workerLoginPanel.add(workerLoginButton);

        // Add action listener to the user type selection dropdown
        userTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedType = (String) userTypeComboBox.getSelectedItem();
                if (selectedType.equals("Patient")) {
                    setContentPane(patientLoginPanel);
                    revalidate();
                    repaint();
                } else if (selectedType.equals("Worker")) {
                    setContentPane(workerLoginPanel);
                    revalidate();
                    repaint();
                }
            }
        });

        // Add action listener to the patient login button
        patientLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fullName = patientNameField.getText();
                String password = new String(patientPasswordField.getPassword());

                if (validatePatientLogin(fullName, password)) {
                    JOptionPane.showMessageDialog(null, "Welcome back, " + fullName + "!");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid login credentials for patient.");
                }
            }
        });

        // Add action listener to the worker login button
        workerLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String workerId = workerIdField.getText();
                String password = new String(workerPasswordField.getPassword());
                String role = (String) workerRoleComboBox.getSelectedItem();

                if (validateWorkerLogin(workerId, password)) {
                    String fullName = getWorkerFullName(workerId);
                    JOptionPane.showMessageDialog(null, "Welcome back, " + role + " " + fullName + "!");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid login credentials for worker.");
                }
            }
        });

        // Initialize the credentials data
        initializeCredentials();

        // Create a main panel to hold all the components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(250, 128, 114)); // Salmon color
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(20)); // Add vertical spacing
        mainPanel.add(userTypeComboBox);

        // Add the main panel to the main frame
        add(mainPanel);

        setVisible(true);
    }

    // Simulated validation methods
    private boolean validatePatientLogin(String fullName, String password) {
        // Validate patient login credentials
        String storedPassword = patientCredentials.get(fullName);
        return storedPassword != null && storedPassword.equals(password);
    }

    private boolean validateWorkerLogin(String workerId, String password) {
        // Validate worker login credentials
        String storedPassword = workerCredentials.get(workerId);
        return storedPassword != null && storedPassword.equals(password);
    }

    private String getWorkerFullName(String workerId) {
        // Get worker full name based on worker ID
        // Replace this with your own logic or data retrieval from a database
        if (workerId.equals("1001")) {
            return "John Smith";
        } else if (workerId.equals("1002")) {
            return "Jane Doe";
        } else if (workerId.equals("1003")) {
            return "Michael Johnson";
        } else if (workerId.equals("1004")) {
            return "Emily Brown";
        } else if (workerId.equals("1005")) {
            return "David Wilson";
        }
        return "";
    }

    private void initializeCredentials() {
        // Initialize the patient and worker credentials data
        patientCredentials = new HashMap<>();
        patientCredentials.put("Worthy Chukwuemeka", "password123");
        patientCredentials.put("Grace Emeka", "password456");
        patientCredentials.put("Daniella Hope", "password789");
        patientCredentials.put("Samuel Gabriel", "password012");
        patientCredentials.put("Michael Wilson", "password345");

        workerCredentials = new HashMap<>();
        workerCredentials.put("1001", "password1001");
        workerCredentials.put("1002", "password1002");
        workerCredentials.put("1003", "password1003");
        workerCredentials.put("1004", "password1004");
        workerCredentials.put("1005", "password1005");
    }

    public static void main(String[] args) {
        // Run the GUI on the Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HospitalManagementGUI();
            }
        });
    }
}
