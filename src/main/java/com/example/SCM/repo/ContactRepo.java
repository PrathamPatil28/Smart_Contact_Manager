//package com.example.SCM.repo;
//
//import java.util.*;
//
//import com.example.SCM.entity.Contact;
//import com.example.SCM.entity.User;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//
//@Repository
//public interface ContactRepo extends JpaRepository<Contact, String> {
//    // find the contact by user
//    // custom finder method
//    Page<Contact> findByUser(User user, Pageable pageable);
//
//    // custom query method
//    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
//    List<Contact> findByUserId(@Param("userId") String userId);
//
//    Page<Contact> findByUserAndNameContaining(User user, String namekeyword, Pageable pageable);
//
//    Page<Contact> findByUserAndEmailContaining(User user, String emailkeyword, Pageable pageable);
//
//    Page<Contact> findByUserAndPhoneNumberContaining(User user, String phoneNumberKeyword, Pageable pageable);
//
//}


package com.example.SCM.repo;

import com.example.SCM.entity.Contact;
import com.example.SCM.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ContactRepo extends JpaRepository<Contact,String> {
    //find all user related contact in pages

    Page<Contact> findByUser(User user , Pageable pageable);

    Page<Contact> findByNameContainingAndUser(String value, User user, Pageable pageable);

    Page<Contact> findByEmailContainingAndUser(String value,User user ,Pageable pageable);

    Page<Contact> findByPhoneNumberContainingAndUser(String value,User user, Pageable pageable);

    long countByUser(User user);

    long countByUserAndFavourite(User user , boolean favourite);

    List<Contact> findByUser(User user);
}