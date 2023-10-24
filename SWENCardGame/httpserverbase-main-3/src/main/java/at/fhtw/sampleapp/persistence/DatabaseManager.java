package at.fhtw.sampleapp.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum DatabaseManager {
    INSTANCE;

    public Connection getConnection()
    {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/swen1db",
                    "postgres",
                    "pwd123456");

            // Display a success message in the console
            System.out.println("Database connection established successfully.");

            return connection;
        } catch (SQLException e) {
            throw new DataAccessException("Datenbankverbindungsaufbau nicht erfolgreich", e);
        }
    }
}
