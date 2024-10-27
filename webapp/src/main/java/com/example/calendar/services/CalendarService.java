package com.example.calendar.services;

import com.example.calendar.models.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CalendarService {
    private Connection conn;

    // Constructor that sets up the database connection using environment variables
    public CalendarService() {
        try {
            // Load credentials from environment variables
            String dbUrl = System.getenv("DB_URL");              // Database URL
            String dbUsername = System.getenv("DB_USERNAME");    // Database username
            String dbPassword = System.getenv("DB_PASSWORD");    // Database password

            if (dbUrl == null || dbUsername == null || dbPassword == null) {
                throw new RuntimeException("Database credentials are not set in environment variables");
            }

            // Establish connection using environment variables
            this.conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database");
        }
    }

    // Constructor for testing (mocked connection)
    public CalendarService(Connection conn) {
        this.conn = conn;
    }

    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM appointments");
            while (rs.next()) {
                appointments.add(new Appointment(rs.getInt("id"), rs.getString("date"), rs.getString("time"), rs.getString("description")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public void addAppointment(String date, String time, String description) {
        try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO appointments (date, time, description) VALUES (?, ?, ?)")) {
            stmt.setString(1, date);
            stmt.setString(2, time);
            stmt.setString(3, description);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Appointment getAppointmentById(int id) {
        Appointment appointment = null;
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM appointments WHERE id = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                appointment = new Appointment(rs.getInt("id"), rs.getString("date"), rs.getString("time"), rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointment;
    }

    public void updateAppointment(int id, String date, String time, String description) {
        try (PreparedStatement stmt = conn.prepareStatement("UPDATE appointments SET date = ?, time = ?, description = ? WHERE id = ?")) {
            stmt.setString(1, date);
            stmt.setString(2, time);
            stmt.setString(3, description);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
