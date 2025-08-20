package hms;
import java.sql.*;
import java.util.*;

public class HospitalManagementSystem {

    // Database connection details
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "Aash@0711";

    public static void main(String[] args) {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded !");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);

        try {
            // Connect to the database
            Connection connection = DriverManager.getConnection(url, username, password);

            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);

            // displaying a user friendly menu repeatedly
            while (true) {
                System.out.println("Hospital Management System");
                System.out.println("1.Add Patient");
                System.out.println("2.View Patients");
                System.out.println("3.View Doctors");
                System.out.println("4.Book Appointment");
                System.out.println("5.Exit");
                System.out.println("Please enter Your Choice");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        // Add a new patient
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        // Show all patients
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        // Show all doctors
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        // Book an appointment
                        bookAppointment(patient, doctor, connection, scanner);
                        System.out.println();
                        break;
                    case 5:
                        // Exit the system
                        System.out.println("THANK YOU! FOR USING HOSPITAL MANAGEMENT SYSTEM!");
                        return;
                    default:
                        // Wrong input
                        System.out.println("Please enter valid input");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Book appointment between patient and doctor
    public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
        System.out.println("Please Enter Patient ID:");
        int patientID = scanner.nextInt();

        System.out.println("Please Enter Doctor ID:");
        int doctorID = scanner.nextInt();

        System.out.println("Please Enter Appointment Date(YYYY-MM-DD): ");
        String appointmentDate = scanner.next();

        // Check if patient and doctor exist
        if (patient.getPatientByID(patientID) && doctor.getDoctorByID(doctorID)) {
            // Check if doctor is free on that date
            if (checkDoctorAvailability(doctorID, appointmentDate, connection)) {
                String appointmentQuery = "INSERT INTO appointments (patients_id, doctors_id, appointments_date) VALUES (?, ?, ?)";
                try {
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1, patientID);
                    preparedStatement.setInt(2, doctorID);
                    preparedStatement.setString(3, appointmentDate);

                    int rowsAffected = preparedStatement.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("Appointment Booked");
                    } else {
                        System.out.println("Appointment could not be booked");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Doctor not available on this date!");
            }
        } else {
            System.out.println("Either doctor or patient doesn't exist!");
        }
    }

    // Check if doctor already has an appointment on given date
    public static boolean checkDoctorAvailability(int doctorID, String appointmentDate, Connection connection) {
        String query = "SELECT COUNT(*) FROM appointments WHERE doctors_id=? AND appointments_date=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, doctorID);
            preparedStatement.setString(2, appointmentDate);

            ResultSet resultset = preparedStatement.executeQuery();

            if (resultset.next()) {
                int count = resultset.getInt(1);
                return count == 0; // true if no appointment found
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
