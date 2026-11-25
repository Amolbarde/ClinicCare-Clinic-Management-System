package clinic901;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorDAO {
    private Connection connection;

    public DoctorDAO() throws SQLException {
        // Initialize your database connection here
        String url = "jdbc:postgresql://localhost:5432/clinic"; // Replace with your database URL
        String user = "postgres"; // Replace with your database username
        String password = "Amol@123"; // Replace with your database password

        // Establish the connection
        connection = DriverManager.getConnection(url, user, password);
    }

    // Method to add a doctor
    public void addDoctor(Doctor doctor) throws SQLException {
        String sql = "INSERT INTO doctors (name, specialty, contact, email, room) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, doctor.getName());
            statement.setString(2, doctor.getSpecialty());
            statement.setString(3, doctor.getContactNumber());
            statement.setString(4, doctor.getEmail());
            statement.setString(5, doctor.getRoomNumber());
            statement.executeUpdate();
        }
    }

    public List<Doctor> getAllDoctors() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/clinic", "postgres", "Amol@123");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT doctor_id, name, specialty, contact, email, room FROM doctors")) { // <--- ADD doctor_id here

            while (rs.next()) {
                Doctor doctor = new Doctor();
                doctor.setDoctorId(rs.getInt("doctor_id")); // <-- Use correct column name
                doctor.setName(rs.getString("name"));
                doctor.setSpecialty(rs.getString("specialty"));
                doctor.setContactNumber(rs.getString("contact"));
                doctor.setEmail(rs.getString("email"));
                doctor.setRoomNumber(rs.getString("room"));
                doctors.add(doctor);
            }
        }
        return doctors;
    }    // Method to retrieve a doctor by ID
    public Doctor getDoctorById(int doctorId) throws SQLException, ClassNotFoundException {
        Doctor doctor = null;
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/clinic", "postgres", "Amol@123")) {
            String sql = "SELECT doctor_id,name,specialty, contact, email, room FROM doctors WHERE doctor_id=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, doctorId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        doctor = new Doctor();
                        doctor.setDoctorId(rs.getInt("doctor_id"));
                        doctor.setName(rs.getString("name"));
                        doctor.setSpecialty(rs.getString("specialty"));
                        doctor.setContactNumber(rs.getString("contact"));
                        doctor.setEmail(rs.getString("email"));
                        doctor.setRoomNumber(rs.getString("room"));
                    }
                }
            }
        }
        return doctor;
    }
    // Optional: Method to close the connection
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}