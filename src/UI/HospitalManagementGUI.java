package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class HospitalManagementGUI extends JFrame {
    private final JComboBox<String> userTypeComboBox;

    private final JPanel patientLoginPanel;
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

    private String selectedRole;

    private Map<String, String> patientCredentials;
    private Map<String, String> workerCredentials;

    public String getSelectedRole () {
        return selectedRole;
    }

    public HospitalManagementGUI() {
        setTitle("Hospital Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        getContentPane().setBackground(new Color(250, 128, 114)); // The colour I used for the content box's background is Salmon

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(250, 128, 114)); // The colour I used for the header background is Salmon

        JLabel headerLabel = new JLabel("<html><div style='text-align: center;'>Welcome to the hospital management system!<br>Please select your role to access your login page:</div></html>");
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        userTypeComboBox = new JComboBox<>(new String[]{"Patient", "Worker"});
        userTypeComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        userTypeComboBox.setMaximumSize(new Dimension(200, 20)); // This is the window's maximum height
        userTypeComboBox.setPreferredSize(new Dimension(200, 20)); // Added this as the window's preferred size for height
        userTypeComboBox.setMinimumSize(new Dimension(200, 20)); // This is the window's minimum size for height


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

        // Action listener for the patient login button
        patientLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fullName = patientNameField.getText();
                String password = new String(patientPasswordField.getPassword());

                if (validatePatientLogin(fullName, password)) {
                    JOptionPane.showMessageDialog(null, "Welcome back, " + fullName + "!");

                    // PatientWindowGUI open
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            new PatientWindowGUI(fullName);
                        }
                    });
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid login credentials for patient.");
                }
            }
        });

        // Action listener for the worker login button
        workerLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String workerId = workerIdField.getText();
                String password = new String(workerPasswordField.getPassword());
                String role = (String) workerRoleComboBox.getSelectedItem();

                if (validateWorkerLogin(workerId, password)) {
                    String fullName = getWorkerFullName(workerId);
                    JOptionPane.showMessageDialog(null, "Welcome back, " + role + " " + fullName + "!");

                    WorkerOptionsGUI workerOptionsGUI = new WorkerOptionsGUI();
                    workerOptionsGUI.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid login credentials for worker.");
                }
            }
        });
        initializeCredentials();


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(250, 128, 114)); 
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(userTypeComboBox);

        // Main panel for the main frame
        add(mainPanel);

        setVisible(true);
    }

    // Hospital Management GUI's validation methods
    private boolean validatePatientLogin(String fullName, String password) {
        // Validate patient login credentials
        String storedPassword = patientCredentials.get(fullName);
        return storedPassword != null && storedPassword.equals(password);
    }

    private boolean validateWorkerLogin(String workerId, String password) {
        // Here, the worker's login credentials will be validated
        String storedPassword = workerCredentials.get(workerId);
        return storedPassword != null && storedPassword.equals(password);
    }

    private String getWorkerFullName(String workerId) {
        // This section helps get worker's full name based on worker ID
        if (workerId.equals("1001")) {
            return "John Smith";
        } else if (workerId.equals("1002")) {
            return "Jane Doe";
        } else if (workerId.equals("1003")) {
            return "Gbenga Johnson";
        } else if (workerId.equals("1004")) {
            return "Whitney Adebayo";
        } else if (workerId.equals("1005")) {
            return "Catherine Olukanmi";
        }
        return "";
    }

    private void initializeCredentials() {
        // This section is to initialize the patient and worker's credentials data
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HospitalManagementGUI();
            }
        });
    }
}
