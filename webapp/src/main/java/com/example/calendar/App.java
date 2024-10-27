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

        get("/logout", (req, res) -> {
            req.session().removeAttribute("authenticated");
            res.redirect("/login");
            return null;
        });


    }
}
