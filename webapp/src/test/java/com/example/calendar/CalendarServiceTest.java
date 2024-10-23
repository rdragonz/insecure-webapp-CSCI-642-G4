package com.example.calendar;

import com.example.calendar.models.Appointment;
import com.example.calendar.services.CalendarService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CalendarServiceTest {

    private CalendarService calendarService;
    private Connection mockConnection;
    private Statement mockStatement;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @Before
    public void setUp() throws Exception {
        // Mock the connection and other JDBC components
        mockConnection = mock(Connection.class);
        mockStatement = mock(Statement.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Inject the mock connection into the CalendarService
        calendarService = new CalendarService(mockConnection);
    }

    @Test
    public void testGetAllAppointments() throws Exception {
        // Mock the behavior of the result set
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery("SELECT * FROM appointments")).thenReturn(mockResultSet);
        
        // Mock the behavior of ResultSet for appointments
        when(mockResultSet.next()).thenReturn(true, true, false); // Simulating two rows in ResultSet
        when(mockResultSet.getInt("id")).thenReturn(1, 2);
        when(mockResultSet.getString("date")).thenReturn("2024-10-01", "2024-10-02");
        when(mockResultSet.getString("time")).thenReturn("09:00", "10:00");
        when(mockResultSet.getString("description")).thenReturn("Meeting 1", "Meeting 2");

        // Get all appointments
        List<Appointment> appointments = calendarService.getAllAppointments();

        // Validate the result
        assertEquals(2, appointments.size());
        assertEquals("2024-10-01", appointments.get(0).getDate());
        assertEquals("09:00", appointments.get(0).getTime());
        assertEquals("Meeting 1", appointments.get(0).getDescription());

        assertEquals("2024-10-02", appointments.get(1).getDate());
        assertEquals("10:00", appointments.get(1).getTime());
        assertEquals("Meeting 2", appointments.get(1).getDescription());
    }

    @Test
    public void testAddAppointment() throws Exception {
        // Mock the behavior of the prepared statement for adding an appointment
        when(mockConnection.prepareStatement("INSERT INTO appointments (date, time, description) VALUES (?, ?, ?)"))
            .thenReturn(mockPreparedStatement);

        // Add an appointment
        calendarService.addAppointment("2024-10-05", "14:00", "Dentist Appointment");

        // Verify if the PreparedStatement set the correct parameters and executed the update
        verify(mockPreparedStatement).setString(1, "2024-10-05");
        verify(mockPreparedStatement).setString(2, "14:00");
        verify(mockPreparedStatement).setString(3, "Dentist Appointment");
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testGetAllAppointmentsWithSQLException() throws Exception {
        // Simulate SQLException
        when(mockConnection.createStatement()).thenThrow(new RuntimeException("Database error"));

        // Ensure that an empty list is returned if there is an exception
        List<Appointment> appointments = calendarService.getAllAppointments();
        assertEquals(0, appointments.size());
    }

    @Test
    public void testAddAppointmentWithSQLException() throws Exception {
        // Simulate SQLException during adding an appointment
        when(mockConnection.prepareStatement("INSERT INTO appointments (date, time, description) VALUES (?, ?, ?)"))
            .thenThrow(new RuntimeException("Database error"));

        // Try adding an appointment (it should not throw an exception but should be caught internally)
        try {
            calendarService.addAppointment("2024-10-05", "14:00", "Dentist Appointment");
        } catch (Exception e) {
            fail("Exception should have been caught inside the addAppointment method.");
        }
    }
}
