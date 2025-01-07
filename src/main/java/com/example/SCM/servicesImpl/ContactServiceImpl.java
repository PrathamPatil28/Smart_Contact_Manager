package com.example.SCM.servicesImpl;

import com.example.SCM.Exception.ResourceNotFoundException;
import com.example.SCM.entity.Contact;
import com.example.SCM.entity.User;
import com.example.SCM.repo.ContactRepo;
import com.example.SCM.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    ContactRepo contactRepo;

    @Override
    public void save(Contact contact) {
        contactRepo.save(contact);
    }

    @Override
    public Contact getById(String id) {
        return contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact Not Found"));
    }

    @Override
    public Contact update(Contact contact) {
        return contactRepo.save(contact);
    }

    @Override
    public void delete(String id) {
        Contact contact = contactRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Contact Not Found"));
        contactRepo.delete(contact);
    }

    @Override
    public Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction) {
        Sort sort = Sort.by(sortBy);
        if (direction.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        }
        return contactRepo.findByUser(user, PageRequest.of(page, size, sort));
    }

    @Override
    public Page<Contact> searchByName(String value, int size, int page, String sortBy, String direction, User user) {
        Sort sort = Sort.by(sortBy);
        if (direction.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        }
        return contactRepo.findByNameContainingAndUser(value, user, PageRequest.of(page, size, sort));
    }

    @Override
    public Page<Contact> searchByEmail(String value, int size, int page, String sortBy, String direction, User user) {
        Sort sort = Sort.by(sortBy);
        if (direction.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        }
        return contactRepo.findByEmailContainingAndUser(value, user, PageRequest.of(page, size, sort));
    }

    @Override
    public Page<Contact> searchByPhoneNumber(String value, int size, int page, String sortBy, String direction, User user) {
        Sort sort = Sort.by(sortBy);
        if (direction.equalsIgnoreCase("desc")) {
            sort = sort.descending();
        }
        return contactRepo.findByPhoneNumberContainingAndUser(value, user, PageRequest.of(page, size, sort));
    }

    @Override
    public long countByUser(User user) {
        return contactRepo.countByUser(user);
    }

    @Override
    public long countFavByUser(User user) {
        return contactRepo.countByUserAndFavourite(user, true);
    }

    @Override
    public List<String> getContactAddedTime(User user) {
        List<Contact> contacts = contactRepo.findByUser(user);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        Map<String, Integer> monthMap = new HashMap<>();

        for (Contact c : contacts) {
            LocalDateTime addedDate = c.getAddedDate();
            if (addedDate == null) {
                addedDate = LocalDateTime.now();
                c.setAddedDate(addedDate);
            }
            String formattedDate = addedDate.format(formatter);
            monthMap.put(formattedDate, monthMap.getOrDefault(formattedDate, 0) + 1);
        }
        return new ArrayList<>(monthMap.keySet());
    }

    @Override
    public List<Integer> getContactAddedCount(User user) {
        List<Contact> contacts = contactRepo.findByUser(user);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        Map<String, Integer> monthMap = new HashMap<>();
        for (Contact c : contacts) {
            LocalDateTime addedDate = c.getAddedDate();
            if (addedDate == null) {
                addedDate = LocalDateTime.now();
                c.setAddedDate(addedDate);
            }
            String formattedDate = addedDate.format(formatter);
            monthMap.put(formattedDate, monthMap.getOrDefault(formattedDate, 0) + 1);
        }
        return new ArrayList<>(monthMap.values());
    }

    @Override
    public List<String> getContactCategory(User user) {
        List<Contact> contactList = contactRepo.findByUser(user);
        Map<String, Integer> categoryMap = new HashMap<>();
        for (Contact c : contactList) {
            String category = "";
            if (c.getName().contains("family") || c.getName().contains("Family"))
                category = "Family";
            else if (c.getName().contains("friend") || c.getName().contains("Friend"))
                category = "Friends";
            else if (c.getName().contains("work") || c.getName().contains("Work"))
                category = "Work";
            else
                category = "Others";
            categoryMap.put(category, categoryMap.getOrDefault(category, 0) + 1);
        }
        return new ArrayList<>(categoryMap.keySet());
    }

    @Override
    public List<Integer> getContactCountCategory(User user) {
        List<Contact> contactList = contactRepo.findByUser(user);
        Map<String, Integer> categoryMap = new HashMap<>();
        for (Contact c : contactList) {
            String category = "";
            if (c.getName().contains("family") || c.getName().contains("Family"))
                category = "Family";
            else if (c.getName().contains("friend") || c.getName().contains("Friend"))
                category = "Friends";
            else if (c.getName().contains("work") || c.getName().contains("Work"))
                category = "Work";
            else
                category = "Others";
            categoryMap.put(category, categoryMap.getOrDefault(category, 0) + 1);
        }
        return new ArrayList<>(categoryMap.values());
    }
}