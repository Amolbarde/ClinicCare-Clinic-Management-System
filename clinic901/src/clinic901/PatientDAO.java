package clinic901;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class PatientDAO {

    // Database connection details (ensure these are correct for your setup)
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/clinic";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Amol@123";

    public void addPatient(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (name, contact, email, address, age) VALUES (?, ?, ?, ?, ?)"; // Include age
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, patient.getName());
            pstmt.setString(2, patient.getContactNumber());
            pstmt.setString(3, patient.getEmail());
            pstmt.setString(4, patient.getAddress());
            pstmt.setInt(5, patient.getAge()); // Set age here
            pstmt.executeUpdate();
        }
    }

    public List<Patient> getAllPatients() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT patient_id, name, contact, email, address, age FROM patients"; // **Corrected SQL: Include age**
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Patient patient = new Patient();
                patient.setPatientId(rs.getInt("patient_id"));
                patient.setName(rs.getString("name"));
                patient.setContactNumber(rs.getString("contact"));
                patient.setEmail(rs.getString("email"));
                patient.setAddress(rs.getString("address"));
                patient.setAge(rs.getInt("age")); // **Set age from ResultSet**
                patients.add(patient);
            }
        }
        return patients;
    }

    public Patient getPatientById(int patientId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT patient_id, name, contact, email, address, age FROM patients WHERE patient_id = ?"; // **Corrected SQL: Include age**
        Patient patient = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, patientId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                patient = new Patient();
                patient.setPatientId(rs.getInt("patient_id"));
                patient.setName(rs.getString("name"));
                patient.setContactNumber(rs.getString("contact"));
                patient.setEmail(rs.getString("email"));
                patient.setAddress(rs.getString("address"));
                patient.setAge(rs.getInt("age")); // **Set age from ResultSet**
            }
        }
        return patient;
    }

        public List<Patient> getAllPatients1() throws SQLException {
        List<Patient> patients = new ArrayList<>();
        String sql = "SELECT patient_id, name, contact, email, address, age FROM patients"; // 
        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/clinic", "postgres", "Amol@123");
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) { // <--- ADD patient_id here

        	while (rs.next()) {
        	    Patient patient = new Patient();
        	    patient.setPatientId(rs.getInt("patient_id"));
        	    patient.setName(rs.getString("name"));
        	    patient.setContactNumber(rs.getString("contact"));
        	    patient.setEmail(rs.getString("email"));
        	    patient.setAddress(rs.getString("address"));
        	    patient.setAge(rs.getInt("age")); // **Make sure this line is present**
        	    patients.add(patient);
        	}

        }
        return patients;
    }
}
