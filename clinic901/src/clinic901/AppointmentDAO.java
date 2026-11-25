
package clinic901;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentDAO {

    private Connection connection;

    public AppointmentDAO() throws SQLException, ClassNotFoundException {

        Class.forName("org.postgresql.Driver");

        connection = DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/clinic",
            "postgres",
            "Amol@123"
        );
    }

    // Book Appointment
    public void bookAppointment(Appointment appointment) throws SQLException {

        String sql = "INSERT INTO appointments " +
                "(doctor_id, patient_id, appointment_date, appointment_time) " +
                "VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, appointment.getDoctorId());
            statement.setInt(2, appointment.getPatientId());

            // Convert String Date to SQL Date (yyyy-MM-dd)
            LocalDate date = LocalDate.parse(
                appointment.getAppointmentDate()
            );

            statement.setDate(3, Date.valueOf(date));

            // Convert String Time to SQL Time (HH:mm or HH:mm:ss)
            LocalTime time = LocalTime.parse(
                appointment.getAppointmentTime()
            );

            statement.setTime(4, Time.valueOf(time));

            statement.executeUpdate();
        }
    }

    // Get All Appointments
    public List<Appointment> getAllAppointments() throws SQLException {

        List<Appointment> appointments = new ArrayList<>();

        String sql = "SELECT * FROM appointments";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {

                Appointment appointment = new Appointment();

                appointment.setAppointmentId(
                    resultSet.getInt("appointment_id")
                );

                appointment.setDoctorId(
                    resultSet.getInt("doctor_id")
                );

                appointment.setPatientId(
                    resultSet.getInt("patient_id")
                );

                appointment.setAppointmentDate(
                    resultSet.getString("appointment_date")
                );

                appointment.setAppointmentTime(
                    resultSet.getString("appointment_time")
                );

                appointments.add(appointment);
            }
        }

        return appointments;
    }

    // Close Database Connection
    public void close() {

        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}