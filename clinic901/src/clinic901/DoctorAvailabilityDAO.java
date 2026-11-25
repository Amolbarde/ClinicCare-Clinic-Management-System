package clinic901;

import java.sql.*;

public class DoctorAvailabilityDAO {
    private Connection connection;

    public DoctorAvailabilityDAO() throws SQLException {
        // Initialize your database connection here
        try {
            String dbUrl = "jdbc:postgresql://localhost:5432/clinic"; // Replace with your database URL
            String dbUser = "postgres";       // Replace with your database username
            String dbPassword = "Amol@123";   // Replace with your database password
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            System.err.println("Error initializing database connection in DoctorAvailabilityDAO: " + e.getMessage());
            throw e; // Re-throw the exception to be handled by the caller
        }
    }

    public void setAvailability(DoctorAvailability availability) throws SQLException {
        String sql = "INSERT INTO doctor_availability (doctor_id, available_date, available_time) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, availability.getDoctorId());
            statement.setString(2, availability.getAvailableDate());
            statement.setString(3, availability.getAvailableTime());
            statement.executeUpdate();
        }
    }

    // You can add more methods to retrieve availability, etc.
}