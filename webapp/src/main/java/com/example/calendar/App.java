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

        // Route to login page
        get("/login", (req, res) -> new ModelAndView(new HashMap<>(), "login"), templateEngine);


        // Route to handle login post request
        post("/login", (req, res) -> {
            String username = req.queryParams("username");
            String password = req.queryParams("password");
            if (userService.authenticate(username, password)) {
                res.redirect("/admin");
            } else {
                res.redirect("/login?error=true");
            }
            return null;
        });

        // Admin page to manage appointments
        get("/admin", (req, res) -> {
            if (userService.isAuthenticated(req)) {
                Map<String, Object> model = new HashMap<>();
                model.put("appointments", calendarService.getAllAppointments());
                return new ModelAndView(model, "admin");
            } else {
                res.redirect("/login");
                return null;
            }
        }, templateEngine);
    }
}
