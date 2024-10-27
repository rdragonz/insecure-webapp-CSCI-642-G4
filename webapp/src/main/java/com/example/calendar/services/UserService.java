package com.example.calendar.services;

import spark.Request;

public class UserService {
    // Hardcoded credentials for simplicity
    public boolean authenticate(String username, String password) {
        return "john".equals(username) && "password123".equals(password);
    }

    public boolean isAuthenticated(Request req) {
        // Check session for authentication status
        Boolean isAuthenticated = req.session().attribute("authenticated");
        return isAuthenticated != null && isAuthenticated;
    }

    public void logout(Request req) {
        req.session().removeAttribute("authenticated");
    }
}
