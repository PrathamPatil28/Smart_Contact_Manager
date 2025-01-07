package com.example.SCM.controller;

import com.example.SCM.entity.User;
import com.example.SCM.helper.Helper;
import com.example.SCM.helper.Message;
import com.example.SCM.helper.MessageType;
import com.example.SCM.services.UserServices;
import com.example.SCM.services.ContactService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {
    // User Dashboard Page
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserServices userServices;

    @Autowired
    private ContactService contactService;

    @Autowired
    private com.example.SCM.services.ImageServices imageServices;

    @GetMapping("/dashboard")
    public String userDashboard(Model model, Authentication authentication){
        // Check if user is authenticated
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userServices.getEmailByUser(username); // Get user details
        model.addAttribute("loggedInUser", user); // Add user to model

        // Additional data for the dashboard
        long totalContacts = contactService.countByUser(user);
        long favoriteContacts = contactService.countFavByUser(user);
        long verifiedEmails= user.isEmailVerified() ? 1:0; // this can be enhanced as required
        model.addAttribute("totalContacts", totalContacts);
        model.addAttribute("favoriteContacts", favoriteContacts);
        model.addAttribute("verifiedEmails", verifiedEmails);

        //get contacts
        List<String> contactLabels = contactService.getContactAddedTime(user);
        model.addAttribute("contactLabels", contactLabels);

        List<Integer> contactData = contactService.getContactAddedCount(user);
        model.addAttribute("contactData", contactData);

        List<String> contactCategoryLabels = contactService.getContactCategory(user);
        model.addAttribute("contactCategoryLabels", contactCategoryLabels);

        List<Integer> contactCategoryData = contactService.getContactCountCategory(user);
        model.addAttribute("contactCategoryData", contactCategoryData);

        return "user/dashboard";
    }

    // User Profile
    @GetMapping("/profile")
    public String userProfile(Model model, Authentication authentication){
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userServices.getEmailByUser(username);
        model.addAttribute("loggedInUser", user); // Add user to model
        model.addAttribute("user", user);
        return "user/profile";
    }

    // Profile update form
    @GetMapping("/profile/update")
    public String updateUserProfileFormView(Model model, Authentication authentication){
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userServices.getEmailByUser(username);
        model.addAttribute("loggedInUser", user); // Add user to model
        model.addAttribute("user", user);
        return "user/update_profile_view";
    }

    @PostMapping("/profile/update")
    public String updateUserProfile(
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
            Model model,
            Authentication authentication,
            HttpSession session,
            @RequestParam("profileImage") MultipartFile file,
            @RequestParam(value = "password", required = false) String password) {

        if (bindingResult.hasErrors()) {
            String username = Helper.getEmailOfLoggedInUser(authentication);
            User userDetail = userServices.getEmailByUser(username);
            model.addAttribute("loggedInUser", userDetail); // Add user to model
            model.addAttribute("user", user);
            return "user/update_profile_view";
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User userDetail = userServices.getEmailByUser(username);

        if (!userDetail.getUserId().equals(user.getUserId())) {
            session.setAttribute("message", new Message("User ID does not match.", MessageType.red));
            return "redirect:/user/profile";
        }

        user.setEmail(userDetail.getEmail()); // Setting email
        // set old password if new password is empty
        if (password != null && !password.isEmpty()) {
            user.setPassword(password);
        } else {
            user.setPassword(userDetail.getPassword());
        }

        String fileURL = null;
        if (file != null && !file.isEmpty()) {
            try {
                String filename = UUID.randomUUID().toString();
                fileURL = imageServices.uploadImage(file, filename);
            } catch (Exception e) {
                logger.error("Error uploading profile image: ", e);
            }
        }
        if (fileURL != null)
            user.setProfilePic(fileURL);

        try {
            Optional<User> updatedUser = userServices.updateUser(user);
            if (updatedUser.isPresent()) {
                session.setAttribute("message", new Message("Profile updated successfully.", MessageType.green));
            } else {
                session.setAttribute("message", new Message("Error updating profile.", MessageType.red));
            }
        } catch (Exception e) {
            logger.error("Error updating profile: ", e);
        }

        return "redirect:/user/profile";
    }
}