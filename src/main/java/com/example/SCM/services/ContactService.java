//package com.example.SCM.services;
//
//import java.util.List;
//
//import com.example.SCM.entity.Contact;
//import com.example.SCM.entity.User;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//
//
//public interface ContactService {
//    // save contacts
//    Contact save(Contact contact);
//
//    // update contact
//    Contact update(Contact contact);
//
//    // get contacts
//    List<Contact> getAll();
//
//    // get contact by id
//
//    Contact getById(String id);
//
//    // delete contact
//
//    void delete(String id);
//
//    // search contact
//    Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user);
//
//    Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user);
//
//    Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order, User user);
//
//    // get contacts by userId
//    List<Contact> getByUserId(String userId);
//
//    Page<Contact> getByUser(User user, int page, int size, String sortField, String sortDirection);
//
//}

package com.example.SCM.services;

import com.example.SCM.entity.Contact;
import com.example.SCM.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ContactService {
    public void save(Contact contact);

    public Contact getById(String id);

    public Contact update(Contact contact);

    public void delete(String id);

    public Page<Contact> getByUser(User user, int page, int size, String sortBy, String direction);

    public Page<Contact> searchByName(String value, int size, int page, String sortBy, String direction, User user);
    public Page<Contact> searchByEmail(String value, int size, int page, String sortBy, String direction, User user);
    public Page<Contact> searchByPhoneNumber(String value, int size, int page, String sortBy, String direction, User user);

    public long countByUser(User user);

    public long countFavByUser(User user);

    public List<String> getContactAddedTime(User user);

    public List<Integer> getContactAddedCount(User user);

    List<String> getContactCategory(User user);
    List<Integer> getContactCountCategory(User user);


}