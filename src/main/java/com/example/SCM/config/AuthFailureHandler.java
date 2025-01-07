package com.example.SCM.config;

import com.example.SCM.helper.Message;
import com.example.SCM.helper.MessageType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        HttpSession session = request.getSession();

        if (exception instanceof DisabledException) {
            // User is disabled
            session.setAttribute("message", new Message("User is disabled, Email with verification link is sent on your email id !!", MessageType.red));
        } else if (exception instanceof BadCredentialsException) {
            // User has entered invalid credentials
            session.setAttribute("message", new Message("Invalid username or password", MessageType.red));
        } else {
            session.setAttribute("message", new Message("Authentication failed", MessageType.red));
        }

        response.sendRedirect("/login");
    }
}