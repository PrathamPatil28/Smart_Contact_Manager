package com.example.SCM.controller;

import com.example.SCM.entity.User;
import com.example.SCM.form.ContactUsForm;
import com.example.SCM.form.UserForm;
import com.example.SCM.helper.Helper;
import com.example.SCM.helper.Message;
import com.example.SCM.helper.MessageType;
import com.example.SCM.services.ContactUsFormService;
import com.example.SCM.services.UserServices;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @Autowired
    UserServices userServices;

    @Autowired
    ContactUsFormService contactUsFormService;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Model model, Authentication authentication) {
        if (authentication != null) {
            // Redirect logged-in user to their dashboard
            return "redirect:/user/profile";
        }

        // For non-logged-in users, return the normal home page
        model.addAttribute("title", "Home - SCM");
        return "home";
    }

    // Add a new mapping for /about
    @RequestMapping("/about")
    public String about(Model model, Authentication authentication) {
        if (authentication != null) {
            String username = Helper.getEmailOfLoggedInUser(authentication);
            User user = userServices.getEmailByUser(username);
            model.addAttribute("loggedInUser", user);
        }
        model.addAttribute("title", "About - SCM");
        return "about";
    }

    // Add a new mapping for /services
    @RequestMapping("/services")
    public String services(Model model, Authentication authentication) {
        if (authentication != null) {
            String username = Helper.getEmailOfLoggedInUser(authentication);
            User user = userServices.getEmailByUser(username);
            model.addAttribute("loggedInUser", user);
        }
        model.addAttribute("title", "Services - SCM");
        return "services";
    }

    @GetMapping("/contact")
    public String contact(Model model, Authentication authentication) {
        if (authentication != null) {
            String username = Helper.getEmailOfLoggedInUser(authentication);
            User user = userServices.getEmailByUser(username);
            model.addAttribute("loggedInUser", user);
        }
        ContactUsForm contactUsForm = new ContactUsForm();
        model.addAttribute("contactUsForm", contactUsForm);
        return "contactUs";
    }

    // Processing Contact form
    @PostMapping("/contact-submit")
    public String processContactForm(
            @Valid @ModelAttribute ContactUsForm contactUsForm,
            BindingResult bindingResult,
            HttpSession session) {

        if (bindingResult.hasErrors()) {
            return "contactUs"; // Correct template name
        }

        // Check if the user is logged in by checking session for userId
        String userId = (String) session.getAttribute("userId");
        if (userId == null) {
            session.setAttribute("message", new Message("You must log in to submit the form.", MessageType.red));
            return "redirect:/login";  // Redirect to login page
        }

        // If logged in, proceed with saving the contact form
        contactUsFormService.saveContactForm(contactUsForm, userId);

        session.setAttribute("message", new Message("Thank you for contacting us!", MessageType.green));
        return "redirect:/contact";  // Redirect back to contact page after submission
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "register";
    }

    // Processing register form
    @PostMapping("/do-register")
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setEnabled(false);
        user.setProfilePic("https://img.freepik.com/free-vector/blue-circle-with-white-user_78370-4707.jpg");

        User savedUser = userServices.saveUser(user);

        Message message = new Message("Registration Successful", MessageType.green);
        session.setAttribute("message", message);

        return "redirect:/register";
    }
}
