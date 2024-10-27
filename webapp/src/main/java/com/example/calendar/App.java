package com.example.calendar;

import static spark.Spark.*;
import com.example.calendar.services.CalendarService;
import com.example.calendar.services.UserService;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {
        // Initialize services
        CalendarService calendarService = new CalendarService();
        UserService userService = new UserService();

        // Setup Thymeleaf template engine
        ThymeleafTemplateEngine templateEngine = new ThymeleafTemplateEngine();

        // Route to view the calendar
        get("/", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            model.put("appointments", calendarService.getAllAppointments());
            return new ModelAndView(model, "calendar");
        }, templateEngine);

        get("/login", (req, res) -> {
            Map<String, Object> model = new HashMap<>();
            // Check if there is an "error" parameter in the request
            if (req.queryParams("error") != null) {
                model.put("error", true);
            }
            return new ModelAndView(model, "login");
        }, templateEngine);



        post("/login", (req, res) -> {
            String username = req.queryParams("username");
            String password = req.queryParams("password");

            if (userService.authenticate(username, password)) {
                // Set session attribute to indicate the user is logged in
                req.session().attribute("authenticated", true);
                res.redirect("/admin");
            } else {
                res.redirect("/login?error=true");
            }
            return null;
        });


        get("/admin", (req, res) -> {
            // Check if the user is authenticated
            Boolean isAuthenticated = req.session().attribute("authenticated");
            if (isAuthenticated != null && isAuthenticated) {
                Map<String, Object> model = new HashMap<>();
                model.put("appointments", calendarService.getAllAppointments());
                return new ModelAndView(model, "admin");
            } else {
                res.redirect("/login");
                return null;
            }
        }, templateEngine);

        post("/admin/add", (req, res) -> {
            // Get form parameters
            String date = req.queryParams("date");
            String time = req.queryParams("time");
            String description = req.queryParams("description");

            // Add the new appointment using CalendarService
            calendarService.addAppointment(date, time, description);

            // Redirect back to the admin page after adding the appointment
            res.redirect("/admin");
            return null;
        });

        // Route to display the edit form for a specific appointment
        get("/admin/edit", (req, res) -> {
            int id = Integer.parseInt(req.queryParams("id"));
            Appointment appointment = calendarService.getAppointmentById(id);

            Map<String, Object> model = new HashMap<>();
            model.put("appointment", appointment);
            return new ModelAndView(model, "edit"); // Render an "edit.html" template
        }, templateEngine);

        // Route to handle the edit form submission
        post("/admin/edit", (req, res) -> {
            int id = Integer.parseInt(req.queryParams("id"));
            String date = req.queryParams("date");
            String time = req.queryParams("time");
            String description = req.queryParams("description");

            // Update the appointment in the database
            calendarService.updateAppointment(id, date, time, description);

            // Redirect back to the admin page after updating
            res.redirect("/admin");
            return null;
        });



        get("/logout", (req, res) -> {
            req.session().removeAttribute("authenticated");
            res.redirect("/login");
            return null;
        });


    }
}
