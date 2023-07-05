package UI;

import com.opencsv.CSVWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PatientWindowGUI extends JFrame {
    private String patientFullName;

    public PatientWindowGUI(String fullName) {
        this.patientFullName = fullName;

        setTitle("Patient Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        JLabel headerLabel = new JLabel("What would you like to do today?");
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(headerLabel);

        JButton bookAppointmentButton = new JButton("Book an Appointment");
        JButton checkRecordsButton = new JButton("Check Past Records");
        bookAppointmentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkRecordsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(bookAppointmentButton);
        add(checkRecordsButton);
        // Add action listener to the book appointment button
        bookAppointmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create the appointment form window
                JFrame appointmentFormFrame = new JFrame("Book an Appointment");
                appointmentFormFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                appointmentFormFrame.setSize(400, 300);
                appointmentFormFrame.setLocationRelativeTo(null);
                appointmentFormFrame.setLayout(new GridLayout(6, 2));

                // Create the labels
                JLabel dateLabel = new JLabel("Date of Complaint:");
                JLabel ageLabel = new JLabel("Age:");
                JLabel phoneNumberLabel = new JLabel("Phone Number:");
                JLabel emailLabel = new JLabel("Email:");
                JLabel complaintLabel = new JLabel("Complaint/Description:");

                // Create the input fields
                JTextField dateField = new JTextField();
                JTextField ageField = new JTextField();
                JTextField phoneField = new JTextField();
                JTextField emailField = new JTextField();
                JTextArea complaintArea = new JTextArea();

                // Create the submit button
                JButton submitButton = new JButton("Submit");

                // Add the labels, input fields, and submit button to the frame
                appointmentFormFrame.add(dateLabel);
                appointmentFormFrame.add(dateField);
                appointmentFormFrame.add(ageLabel);
                appointmentFormFrame.add(ageField);
                appointmentFormFrame.add(phoneNumberLabel);
                appointmentFormFrame.add(phoneField);
                appointmentFormFrame.add(emailLabel);
                appointmentFormFrame.add(emailField);
                appointmentFormFrame.add(complaintLabel);
                appointmentFormFrame.add(complaintArea);
                appointmentFormFrame.add(submitButton);

                // Add action listener to the submit button
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Get the form inputs
                        String date = dateField.getText();
                        String age = ageField.getText();
                        String phoneNumber = phoneField.getText();
                        String email = emailField.getText();
                        String complaint = complaintArea.getText();

                        // Create a new row with the form data
                        String[] rowData = {date, age, phoneNumber, email, complaint};

                        String filePath = "C:\\Users\\worth\\IdeaProjects\\hospitalMgmt\\src\\appointments.csv";

                        try {
                            // Created a FileWriter object with append mode
                            FileWriter csvWriter = new FileWriter(filePath, true);

                            // Created a CSVWriter object
                            CSVWriter writer = new CSVWriter(csvWriter);

                            // Wrote the row to the CSV file
                            writer.writeNext(rowData);

                            // Closed the writer
                            writer.close();

                            // Displayed a success message
                            JOptionPane.showMessageDialog(null, "Appointment submitted successfully!");

                            // Cleared the form inputs
                            dateField.setText("");
                            ageField.setText("");
                            phoneField.setText("");
                            emailField.setText("");
                            complaintArea.setText("");
                        } catch (IOException ex) {
                            // Showed an error message if there's an exception
                            JOptionPane.showMessageDialog(null, "Failed to save appointment: " + ex.getMessage());
                        }
                    }
                });

                // Displayed the appointment form window
                appointmentFormFrame.setVisible(true);
            }
        });


        checkRecordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPatientRecords();
            }
        });

        setVisible(true);
    }

    // This is the part where I created the CSV file reader
    private void showPatientRecords()
    {
        String fileName = patientFullName.replaceAll("\\s+", "_") + "_records.csv";

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("Check In Date & Time,Description,Diagnosis,Prescription");
            bufferedWriter.newLine();

            bufferedWriter.write("2023-07-01 10:30 AM,Headache,General checkup,Take pain reliever");
            bufferedWriter.newLine();
            bufferedWriter.write("2023-07-02 02:15 PM,Fever,Flu symptoms,Take plenty of rest");
            bufferedWriter.newLine();

            bufferedWriter.close();

            List<String[]> records = readCSVFile(fileName);
            displayRecords(records);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error creating or reading the patient's records.");
        }
    }

    // This is the exception handler for the CSV file.
    private List<String[]> readCSVFile(String fileName) {
        List<String[]> records = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");
                records.add(data);
            }

            bufferedReader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error reading the patient's records.");
        }

        return records;
    }

    private void displayRecords(List<String[]> records) {

        JFrame recordsFrame = new JFrame("Patient Records");
        recordsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        recordsFrame.setSize(600, 400);
        recordsFrame.setLocationRelativeTo(null);
        recordsFrame.setLayout(new BorderLayout());

        String[] columnNames = records.get(0);
        String[][] rowData = new String[records.size() - 1][columnNames.length];
        for (int i = 1; i < records.size(); i++) {
            rowData[i - 1] = records.get(i);
        }
        JTable table = new JTable(rowData, columnNames);

        JScrollPane scrollPane = new JScrollPane(table);
        recordsFrame.add(scrollPane, BorderLayout.CENTER);

        recordsFrame.setVisible(true);
    }
}
