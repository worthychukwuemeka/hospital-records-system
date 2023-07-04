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
                readAppointmentsFromFile();
            }
        });
        checkRecordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Check Past Records button clicked!");
            }
        });
    }

    // Method to save the appointment details to a CSV file
    private void saveAppointmentToFile(String patientName, String appointmentDate, String complaint) {
        String csvFilePath = "path/to/your/csv/file/appointments.csv";

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
        String csvFilePath = "/Users/worth/IdeaProjects/hospitalMgmt/src/appointments.csv";

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
}
