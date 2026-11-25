package clinic901;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:postgresql://localhost:5432/clinic"; 
    private static final String USER = "postgres"; 
    //private static final String PASSWORD = "A@123";
    private static final String PASSWORD = "Amol@123";
    
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql."
            		+ "Driver"); // Load the MySQL JDBC driver
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC driver not found", e);
        }
    }
}