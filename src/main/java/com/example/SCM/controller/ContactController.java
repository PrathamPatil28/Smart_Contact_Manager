//package com.example.SCM.controller;
//
//
//import com.example.SCM.Constants.AppConstants;
//import com.example.SCM.entity.Contact;
//import com.example.SCM.entity.User;
//import com.example.SCM.form.ContactForm;
//import com.example.SCM.form.ContactSearchForm;
//import com.example.SCM.helper.Helper;
//import com.example.SCM.helper.Message;
//import com.example.SCM.helper.MessageType;
//import com.example.SCM.services.ContactService;
//import com.example.SCM.services.ImageServices;
//import com.example.SCM.services.UserServices;
//import jakarta.servlet.http.HttpSession;
//import jakarta.validation.Valid;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//
//import java.util.UUID;
//
//@Controller
//@RequestMapping("/user/contact")
//public class ContactController {
//
//    @Autowired
//    UserServices userServices;
//
//    @Autowired
//    ContactService contactService;
//
//    @Autowired
//    ImageServices imageServices;
//
//    Logger logger = LoggerFactory.getLogger(ContactController.class);
//
//    // add Contact
//    @GetMapping("/add")
//    public String addContactView(Model model , Authentication authentication){
//
//
//
//        String username = Helper.getEmailOfLoggedInUser(authentication);
//        User user = userServices.getEmailByUser(username); // Fetch logged-in user details
//
//        model.addAttribute("loggedInUser", user); // Add the user to the model
//
//        ContactForm contactForm = new ContactForm();
//        contactForm.setFavourite(true);
//        model.addAttribute("contactForm" , contactForm);
//        return "user/add_contact";
//        }
//
//
//    // Process Contact
////    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    @PostMapping("/add")
//    public String saveContact(@Valid @ModelAttribute("contactForm") ContactForm contactForm, BindingResult bindingResult, Model model, Authentication authentication , HttpSession session) {
//        if (bindingResult.hasErrors()) {
//            String username = Helper.getEmailOfLoggedInUser(authentication);
//            User user = userServices.getEmailByUser(username);
//            session.setAttribute("message", new Message("Please correct the following errors", MessageType.red));
//            model.addAttribute("loggedInUser", user);
//
//            return "user/add_contact";
//        }
//
//        String username = Helper.getEmailOfLoggedInUser(authentication);
//        User user = userServices.getEmailByUser(username);
//
//      //  String filename= UUID.randomUUID().toString();
//
//      //  String fileURL= imageServices.uploadImage(contactForm.getProfileImage(),filename);
//        Contact contact = new Contact();
//        contact.setName(contactForm.getName());
//        contact.setFavourite(contactForm.isFavourite()); // Ensure favourite is set correctly
//        contact.setEmail(contactForm.getEmail());
//        contact.setPhoneNumber(contactForm.getPhoneNumber());
//        contact.setDescription(contactForm.getDescription());
//        contact.setLinkedinLink(contactForm.getLinkedinLink()!= null && !contactForm.getLinkedinLink().isEmpty() ? contactForm.getLinkedinLink() : "https://www.linkedin.com/in/pratham-patil-b49b03210/"); // Set default link if not provided
//        contact.setWebSiteLink(contactForm.getWebSiteLink() != null && !contactForm.getWebSiteLink().isEmpty() ? contactForm.getWebSiteLink() : "https://pratham-patil-portfolio.netlify.app/"); // Set default link if not provided
//        contact.setAddress(contactForm.getAddress());
//
//        if (contactForm.getProfileImage() != null && !contactForm.getProfileImage().isEmpty()) {
//            String filename = UUID.randomUUID().toString();
//            String fileURL =imageServices.uploadImage(contactForm.getProfileImage(), filename);
//            contact.setPicture(fileURL);
//            contact.setCloudinaryImagePublicId(filename);
//
//        }
//      //  contact.setPicture(fileURL);
//      //  contact.setCloudinaryImagePublicId(filename);
//        contact.setUser(user);
//
//
//        contactService.save(contact);
//
//        session.setAttribute("message", new Message("You have successfully added a new contact", MessageType.green));
//
//        return "redirect:/user/contact/add"; // Temporary redirect, change it for testing
//    }
//
//    //view all contact
//    @GetMapping()
//    public String viewContact(
//            @RequestParam(value = "page",defaultValue = "0") int page,
//            @RequestParam(value = "size",defaultValue = "4") int size,
//            @RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
//            @RequestParam(value = "direction",defaultValue = "asc") String direction,
//            Model model,Authentication authentication){
//
//        String username = Helper.getEmailOfLoggedInUser(authentication);
//        User user = userServices.getEmailByUser(username);
//
//        Page<Contact> pageContact = contactService.getByUser(user,page,size,sortBy,direction);
//
//        ContactSearchForm contactSearchForm = new ContactSearchForm(); // Initialize the form
//
//        model.addAttribute("loggedInUser", user); // Add the user to the model
//        model.addAttribute("pageContact" ,pageContact );
//        model.addAttribute("pageSize" , size); // Ensure pageSize is set correctly
//        model.addAttribute("contactSearchForm", contactSearchForm); // Add the form to the model
//        return "user/contact";
//    }
//
//    // search handler
//
//    @RequestMapping("/search")
//    public String searchHandler(
//            @ModelAttribute ContactSearchForm contactSearchForm,
//            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
//            @RequestParam(value = "page", defaultValue = "0") int page,
//            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
//            @RequestParam(value = "direction", defaultValue = "asc") String direction,
//            Model model,
//            Authentication authentication) {
//
//        logger.info("field {} keyword {}", contactSearchForm.getField(), contactSearchForm.getValue());
//
//        var user = userServices.getEmailByUser(Helper.getEmailOfLoggedInUser(authentication));
//
//        Page<Contact> pageContact = null;
//        String field = contactSearchForm.getField();
//
//        if (field != null && !field.isEmpty()) {
//            if (field.equalsIgnoreCase("name")) {
//                pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction, user);
//            } else if (field.equalsIgnoreCase("email")) {
//                pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction, user);
//            } else if (field.equalsIgnoreCase("phone")) {
//                pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy, direction, user);
//            }
//        } else {
//            logger.warn("Search field is empty. Returning an empty Page.");
//        }
//
//        // Check if pageContact is still null; if it is, set it to an empty page to avoid null pointer in the HTML
//        if (pageContact == null) {
//            logger.warn("pageContact is null set it to an empty page ");
//            pageContact = Page.empty();
//        }
//        logger.info("pageContact totalElements: {}", pageContact.getTotalElements());
//
//        model.addAttribute("contactSearchForm", contactSearchForm);
//        model.addAttribute("pageContact", pageContact);
//        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
//        model.addAttribute("loggedInUser", user); // Ensure loggedInUser is added to the model
//        return "user/search";
//    }
//
//
//
//    //delete contact using id
//
//    @GetMapping("/delete/{contactId}")
//    public String deleteContact(@PathVariable String contactId, HttpSession session){
//        Contact contact = contactService.getById((contactId));
//        if(contact==null){
//            session.setAttribute("message", new Message("Something went wrong contact not found", MessageType.red));
//            return "redirect:/user/contact";
//        }
//
//        contactService.delete((contactId));
//
//        session.setAttribute("message", new Message("Contact Successfully deleted", MessageType.green));
//
//        return "redirect:/user/contact";
//    }
//
//    //update Contact
//    @GetMapping("/view/{contactId}")
//    public String updateContactFormView(@PathVariable String contactId ,Model model,Authentication authentication){
//
//        var contact = contactService.getById(contactId);
//        String username = Helper.getEmailOfLoggedInUser(authentication);
//        User user = userServices.getEmailByUser(username);
//
//        ContactForm contactForm = new ContactForm();
//
//        contactForm.setName(contact.getName());
//        contactForm.setEmail(contact.getEmail());
//        contactForm.setPhoneNumber(contact.getPhoneNumber());
//        contactForm.setAddress(contact.getAddress());
//        contactForm.setDescription(contact.getDescription());
//        contactForm.setFavourite(contact.isFavourite());
//        contactForm.setWebSiteLink(contact.getWebSiteLink());
//        contactForm.setLinkedinLink(contact.getLinkedinLink());
//        contactForm.setPicture(contact.getPicture());
//
//        model.addAttribute("loggedInUser", user);
//        model.addAttribute("contactForm" ,contactForm);
//        model.addAttribute("contactId" , contactId);
//        return "user/update_contact_view";
//    }
//
//
//    @PostMapping("/update/{contactId}")
//    public String updateContact(@PathVariable String contactId, @Valid @ModelAttribute ContactForm contactForm, BindingResult bindingResult, Model model, Authentication authentication, HttpSession session) {
//        if (bindingResult.hasErrors()) {
//            String username = Helper.getEmailOfLoggedInUser(authentication);
//            User user = userServices.getEmailByUser(username);
//            model.addAttribute("loggedInUser", user);
//            model.addAttribute("contactForm", contactForm);
//            model.addAttribute("contactId", contactId);
//            return "user/update_contact_view";
//        }
//
//        var con = contactService.getById(contactId);
//        con.setId(contactId);
//        con.setName(contactForm.getName());
//        con.setEmail(contactForm.getEmail());
//        con.setPhoneNumber(contactForm.getPhoneNumber());
//        con.setAddress(contactForm.getAddress());
//        con.setDescription(contactForm.getDescription());
//        con.setFavourite(contactForm.isFavourite());
//        con.setWebSiteLink(contactForm.getWebSiteLink());
//        con.setLinkedinLink(contactForm.getLinkedinLink());
//
//        // process image:
//        if (contactForm.getProfileImage() != null && !contactForm.getProfileImage().isEmpty()) {
//            logger.info("file is not empty");
//            String fileName = UUID.randomUUID().toString();
//            String imageUrl = imageServices.uploadImage(contactForm.getProfileImage(), fileName);
//            con.setCloudinaryImagePublicId(fileName);
//            con.setPicture(imageUrl);
//            contactForm.setPicture(imageUrl);
//        } else {
//            logger.info("file is empty");
//        }
//
//        Contact updated = contactService.update(con);
//        session.setAttribute("message", new Message("Contact updated successfully", MessageType.green));
//
//        return "redirect:/user/contact/view/" + contactId;
//    }
//}
package com.example.SCM.controller;


import com.example.SCM.Constants.AppConstants;
import com.example.SCM.entity.Contact;
import com.example.SCM.entity.User;
import com.example.SCM.form.ContactForm;
import com.example.SCM.form.ContactSearchForm;
import com.example.SCM.helper.Helper;
import com.example.SCM.helper.Message;
import com.example.SCM.helper.MessageType;
import com.example.SCM.services.ContactService;
import com.example.SCM.services.ImageServices;
import com.example.SCM.services.UserServices;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@Controller
@RequestMapping("/user/contact")
public class ContactController {

    @Autowired
    UserServices userServices;

    @Autowired
    ContactService contactService;

    @Autowired
    ImageServices imageServices;

    Logger logger = LoggerFactory.getLogger(ContactController.class);

    // add Contact
    @GetMapping("/add")
    public String addContactView(Model model , Authentication authentication){

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userServices.getEmailByUser(username); // Fetch logged-in user details

        model.addAttribute("loggedInUser", user); // Add the user to the model

        ContactForm contactForm = new ContactForm();
        contactForm.setFavourite(true);
        model.addAttribute("contactForm" , contactForm);
        return "user/add_contact";
    }


    // Process Contact
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @PostMapping("/add")
    public String saveContact(@Valid @ModelAttribute("contactForm") ContactForm contactForm, BindingResult bindingResult, Model model, Authentication authentication , HttpSession session) {
        if (bindingResult.hasErrors()) {
            String username = Helper.getEmailOfLoggedInUser(authentication);
            User user = userServices.getEmailByUser(username);
            session.setAttribute("message", new Message("Please correct the following errors", MessageType.red));
            model.addAttribute("loggedInUser", user);

            return "user/add_contact";
        }

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userServices.getEmailByUser(username);


        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setFavourite(contactForm.isFavourite()); // Ensure favourite is set correctly
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setDescription(contactForm.getDescription());
        contact.setLinkedinLink(contactForm.getLinkedinLink()!= null && !contactForm.getLinkedinLink().isEmpty() ? contactForm.getLinkedinLink() : "https://www.linkedin.com/in/pratham-patil-b49b03210/"); // Set default link if not provided
        contact.setWebSiteLink(contactForm.getWebSiteLink() != null && !contactForm.getWebSiteLink().isEmpty() ? contactForm.getWebSiteLink() : "https://pratham-patil-portfolio.netlify.app/"); // Set default link if not provided
        contact.setAddress(contactForm.getAddress());

        if (contactForm.getProfileImage() != null && !contactForm.getProfileImage().isEmpty()) {
            String filename = UUID.randomUUID().toString();
            String fileURL =imageServices.uploadImage(contactForm.getProfileImage(), filename);
            contact.setPicture(fileURL);
            contact.setCloudinaryImagePublicId(filename);

        }
        contact.setUser(user);


        contactService.save(contact);

        session.setAttribute("message", new Message("You have successfully added a new contact", MessageType.green));

        return "redirect:/user/contact/add"; // Temporary redirect, change it for testing
    }

    //view all contact
    @GetMapping()
    public String viewContact(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "4") int size,
            @RequestParam(value = "sortBy",defaultValue = "name") String sortBy,
            @RequestParam(value = "direction",defaultValue = "asc") String direction,
            Model model,Authentication authentication){

        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userServices.getEmailByUser(username);

        Page<Contact> pageContact = contactService.getByUser(user,page,size,sortBy,direction);

        ContactSearchForm contactSearchForm = new ContactSearchForm(); // Initialize the form

        model.addAttribute("loggedInUser", user); // Add the user to the model
        model.addAttribute("pageContact" ,pageContact );
        model.addAttribute("pageSize" , size); // Ensure pageSize is set correctly
        model.addAttribute("contactSearchForm", contactSearchForm); // Add the form to the model
        return "user/contact";
    }

    // search handler

    @RequestMapping("/search")
    public String searchHandler(
            @ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,
            Authentication authentication) {

        logger.info("field {} keyword {}", contactSearchForm.getField(), contactSearchForm.getValue());

        var user = userServices.getEmailByUser(Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContact = null;
        String field = contactSearchForm.getField();

        if (field != null && !field.isEmpty()) {
            if (field.equalsIgnoreCase("name")) {
                pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction, user);
            } else if (field.equalsIgnoreCase("email")) {
                pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction, user);
            } else if (field.equalsIgnoreCase("phone")) {
                pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy, direction, user);
            }
        } else {
            logger.warn("Search field is empty. Returning an empty Page.");
        }

        // Check if pageContact is still null; if it is, set it to an empty page to avoid null pointer in the HTML
        if (pageContact == null) {
            logger.warn("pageContact is null set it to an empty page ");
            pageContact = Page.empty();
        }
        logger.info("pageContact totalElements: {}", pageContact.getTotalElements());

        model.addAttribute("contactSearchForm", contactSearchForm);
        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("loggedInUser", user); // Ensure loggedInUser is added to the model
        return "user/search";
    }



    //delete contact using id

    @GetMapping("/delete/{contactId}")
    public String deleteContact(@PathVariable String contactId, HttpSession session){
        Contact contact = contactService.getById((contactId));
        if(contact==null){
            session.setAttribute("message", new Message("Something went wrong contact not found", MessageType.red));
            return "redirect:/user/contact";
        }

        contactService.delete((contactId));

        session.setAttribute("message", new Message("Contact Successfully deleted", MessageType.green));

        return "redirect:/user/contact";
    }

    //update Contact
    @GetMapping("/view/{contactId}")
    public String updateContactFormView(@PathVariable String contactId ,Model model,Authentication authentication){

        var contact = contactService.getById(contactId);
        String username = Helper.getEmailOfLoggedInUser(authentication);
        User user = userServices.getEmailByUser(username);

        ContactForm contactForm = new ContactForm();

        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavourite(contact.isFavourite());
        contactForm.setWebSiteLink(contact.getWebSiteLink());
        contactForm.setLinkedinLink(contact.getLinkedinLink());
        contactForm.setPicture(contact.getPicture());

        model.addAttribute("loggedInUser", user);
        model.addAttribute("contactForm" ,contactForm);
        model.addAttribute("contactId" , contactId);
        return "user/update_contact_view";
    }


    @PostMapping("/update/{contactId}")
    public String updateContact(@PathVariable String contactId, @Valid @ModelAttribute ContactForm contactForm, BindingResult bindingResult, Model model, Authentication authentication, HttpSession session) {
        if (bindingResult.hasErrors()) {
            String username = Helper.getEmailOfLoggedInUser(authentication);
            User user = userServices.getEmailByUser(username);
            model.addAttribute("loggedInUser", user);
            model.addAttribute("contactForm", contactForm);
            model.addAttribute("contactId", contactId);
            return "user/update_contact_view";
        }

        var con = contactService.getById(contactId);
        con.setId(contactId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavourite(contactForm.isFavourite());
        con.setWebSiteLink(contactForm.getWebSiteLink());
        con.setLinkedinLink(contactForm.getLinkedinLink());

        // process image:
        if (contactForm.getProfileImage() != null && !contactForm.getProfileImage().isEmpty()) {
            logger.info("file is not empty");
            String fileName = UUID.randomUUID().toString();
            String imageUrl = imageServices.uploadImage(contactForm.getProfileImage(), fileName);
            con.setCloudinaryImagePublicId(fileName);
            con.setPicture(imageUrl);
            contactForm.setPicture(imageUrl);
        } else {
            logger.info("file is empty");
        }

        Contact updated = contactService.update(con);
        session.setAttribute("message", new Message("Contact updated successfully", MessageType.green));

        return "redirect:/user/contact/view/" + contactId;
    }
}
