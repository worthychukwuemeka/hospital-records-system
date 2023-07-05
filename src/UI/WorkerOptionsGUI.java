package UI;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WorkerOptionsGUI extends JFrame {
    public WorkerOptionsGUI() {
        setTitle("Worker Options");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        // Create the header label
        JLabel headerLabel = new JLabel("What would you like to do today?");
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(headerLabel);

        // Create the buttons
        JButton viewAppointmentsButton = new JButton("View Appointments");
        JButton checkRecordsButton = new JButton("Check Past Records");
        viewAppointmentsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkRecordsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(viewAppointmentsButton);
        add(checkRecordsButton);

        // Add action listener to the view appointments button
        viewAppointmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAppointmentsCSV();
            }
        });

        checkRecordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showPatientRecordsDropdown();
            }
        });
    }

    // Method to save the appointment details to a CSV file
    private void saveAppointmentToFile(String patientName, String appointmentDate, String complaint) {
        String csvFilePath = "C:\\path\\to\\appointments.csv"; // Replace with the actual file path

        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFilePath, true))) {
            // Write the appointment details as a new row in the CSV file
            String[] appointmentData = {patientName, appointmentDate, complaint};
            writer.writeNext(appointmentData);

            System.out.println("Appointment saved successfully to the CSV file.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Method to read appointment details from the CSV file
    private void readAppointmentsFromFile() {
        String csvFilePath = "C:\\path\\to\\appointments.csv"; // Replace with the actual file path

        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                // Process each row of the CSV file
                // Display the appointment details as desired
                String patientName = nextLine[0];
                String appointmentDate = nextLine[1];
                String complaint = nextLine[2];
                // Display the data in a suitable format
                System.out.println("Patient: " + patientName);
                System.out.println("Appointment Date: " + appointmentDate);
                System.out.println("Complaint: " + complaint);
                System.out.println("------------------------");
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    // Method to open the appointments.csv file with the default program associated with CSV files
    private void openAppointmentsCSV() {
        String csvFilePath = "C:\\Users\\worth\\IdeaProjects\\hospitalMgmt\\src\\appointments.csv"; // Replace with the actual file path

        try {
            Path path = Paths.get(csvFilePath);
            URI uri = path.toUri();

            // Open the URI using the default program associated with CSV files
            Desktop.getDesktop().open(new java.io.File(uri));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // Method to show the dropdown menu with patient names for record selection
    private void showPatientRecordsDropdown() {
        JFrame dropdownFrame = new JFrame("Select Patient Record");
        dropdownFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dropdownFrame.setSize(300, 150);
        dropdownFrame.setLocationRelativeTo(null);
        dropdownFrame.setLayout(new FlowLayout());

        // Create the header label
        JLabel headerLabel = new JLabel("Select a patient's record to be displayed:");
        dropdownFrame.add(headerLabel);

        // Create the dropdown menu
        String[] patientNames = {"Worthy Chukwuemeka", "Grace Emeka", "Daniella Hope", "Samuel Gabriel", "Michael Babatunde"};
        JComboBox<String> patientDropdown = new JComboBox<>(patientNames);
        dropdownFrame.add(patientDropdown);

        // Create the button to open the selected patient's record
        JButton openRecordButton = new JButton("Open Record");
        dropdownFrame.add(openRecordButton);

        // Add action listener to the open record button
        openRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedPatient = (String) patientDropdown.getSelectedItem();
                openPatientRecordsCSV(selectedPatient);
            }
        });

        dropdownFrame.setVisible(true);
    }

    // Method to open the patient's records CSV file with the default program associated with CSV files
    private void openPatientRecordsCSV(String patientName) {
        String csvFileName = patientName.replaceAll("\\s+", "_") + "_records.csv";
        String csvFilePath = "C:\\Users\\worth\\IdeaProjects\\hospitalMgmt\\" + csvFileName;

        try {
            Path path = Paths.get(csvFilePath);
            URI uri = path.toUri();

            Desktop.getDesktop().open(new java.io.File(uri));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                WorkerOptionsGUI optionsGUI = new WorkerOptionsGUI();
                optionsGUI.setVisible(true);
            }
        });
    }
}
