package com.example.SCM.services;

import com.example.SCM.form.ContactUsForm;

public interface ContactUsFormService {
    void saveContactForm(ContactUsForm contactUsForm, String userId);
}
