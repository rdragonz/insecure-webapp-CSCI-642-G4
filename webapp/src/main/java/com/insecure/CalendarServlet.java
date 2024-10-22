package com.insecure;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.lang.String;

@WebServlet("/calendar")
public class CalendarServlet extends HttpServlet {

    private List<Appointment> appointments = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // For simplicity, we are using hardcoded appointments. 
        // In a real app, you'd retrieve these from a database.
        appointments.add(new Appointment("Team Meeting", new Date(), new Date()));
        req.setAttribute("appointments", appointments);
        
        req.getRequestDispatcher("/calendar.jsp").forward(req, resp);
    }
}
