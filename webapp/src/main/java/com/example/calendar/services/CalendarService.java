package com.example.calendar.services;

import com.example.calendar.models.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CalendarService {
    private Connection conn;

    // Constructor accepting a database connection
    public CalendarService(Connection conn) {
        this.conn = conn;
    }

    // Default constructor (used in production, creates a real connection)
    public CalendarService() {
        try {
            this.conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/Calendar", "your_username", "your_password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}
