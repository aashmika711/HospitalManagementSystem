package hms;
import java.sql.*;
import java.util.*;

public class Doctor {
    private Connection connection;

    // Constructor to initialize database connection
    public Doctor(Connection connection) {
        this.connection = connection;
    }

    // Show all doctors from the database
    public void viewDoctors() {
        String query = "select * from doctors";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultset = preparedStatement.executeQuery();

            // Print table heading
            System.out.println("Doctors: ");
            System.out.println("+--------------+----------------------+-------------------+");
            System.out.println("| Doctors ID   |  Name                | Specialization    |");
            System.out.println("+--------------+----------------------+-------------------+");

            // Print each doctor record
            while (resultset.next()) {
                int id = resultset.getInt("id");
                String name = resultset.getString("name");
                String specialization = resultset.getString("Specialization");
                System.out.printf("|%-13s|%-21s|%-17s|\n", id, name, specialization);
                System.out.println("+--------------+----------------------+-------------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Check if a doctor exists by given ID
    public boolean getDoctorByID(int id) {
        String query = "select * from doctors where ID =?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultset = preparedStatement.executeQuery();

            // Return true if doctor exists
            if (resultset.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
