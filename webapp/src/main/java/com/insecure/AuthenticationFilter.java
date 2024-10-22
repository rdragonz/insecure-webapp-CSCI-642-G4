package com.insecure;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.String;


public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);

        boolean isLoggedIn = (session != null && session.getAttribute("user") != null);
        String loginURI = req.getContextPath() + "/login.jsp";

        boolean isLoginRequest = req.getRequestURI().equals(loginURI);
        boolean isLoginPage = req.getRequestURI().endsWith("login.jsp");

        if (isLoggedIn && (isLoginRequest || isLoginPage)) {
            // If the user is already logged in and tries to go to the login page, redirect them to the main page
            ((HttpServletResponse) response).sendRedirect(req.getContextPath() + "/calendar");
        } else if (isLoggedIn || isLoginRequest || isLoginPage) {
            // The user is either logged in or trying to log in, allow the request
            chain.doFilter(request, response);
        } else {
            // The user is not logged in and is trying to access a protected page, redirect to the login page
            ((HttpServletResponse) response).sendRedirect(loginURI);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
