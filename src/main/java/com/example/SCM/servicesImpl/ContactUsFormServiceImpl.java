package com.example.SCM.servicesImpl;

import com.example.SCM.entity.ContactUsForm;
import com.example.SCM.entity.User;
import com.example.SCM.repo.ContactUsFormRepository;

import com.example.SCM.repo.UserRepo;
import com.example.SCM.services.ContactUsFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ContactUsFormServiceImpl implements ContactUsFormService {

    @Autowired
    private ContactUsFormRepository contactUsFormRepository;
    @Autowired
    UserRepo userRepo;

    @Override
    public void saveContactForm(com.example.SCM.form.ContactUsForm contactUsForm, String userId) {
        // Map form data to entity
        ContactUsForm contactFormEntity = new ContactUsForm();
        contactFormEntity.setName(contactUsForm.getName());
        contactFormEntity.setEmail(contactUsForm.getEmail());
        contactFormEntity.setSubject(contactUsForm.getSubject());
        contactFormEntity.setMessage(contactUsForm.getMessage());

        // If userId is provided (i.e., the user is logged in), associate the user
        if (userId != null) {
            User user = userRepo.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
            contactFormEntity.setUser(user);  // Associate user with the contact form
        }

        // Save to database
        contactUsFormRepository.save(contactFormEntity);
    }
}
