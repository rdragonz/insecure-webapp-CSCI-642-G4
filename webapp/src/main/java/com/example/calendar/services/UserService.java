package com.example.calendar.services;

import spark.Request;

public class UserService {
    public boolean authenticate(String username, String password) {
        return "john".equals(username) && "password123".equals(password); // Hardcoded for simplicity
    }

    public boolean isAuthenticated(Request req) {
        // Check session or request for user authentication
        return req.session().attribute("user") != null;
    }
}
