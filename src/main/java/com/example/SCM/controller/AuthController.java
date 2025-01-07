package com.example.SCM.controller;

import com.example.SCM.entity.User;
import com.example.SCM.helper.Message;
import com.example.SCM.helper.MessageType;
import com.example.SCM.repo.UserRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token, HttpSession session) {
        User user = userRepo.findByEmailToken(token).orElse(null);

        if (user != null) {
            if (user.getEmailToken().equals(token)) {
                user.setEmailVerified(true);
                user.setEnabled(true);
                userRepo.save(user);
                session.setAttribute("message", new Message("Your email is verified. Now you can login.", MessageType.green));
                return "success_page";
            }
            session.setAttribute("message", new Message("Email not verified! Token is not associated with user.", MessageType.red));
            return "error_page";
        }
        session.setAttribute("message", new Message("Email not verified! Token is not associated with user.", MessageType.red));
        return "error_page";
    }
}
