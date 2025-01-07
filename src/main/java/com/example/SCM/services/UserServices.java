package com.example.SCM.services;

import com.example.SCM.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserServices {

     User saveUser(User user);

     Optional<User> getUserById(String id);

     Optional<User> updateUser(User user);

     void deleteUser(String id);

     boolean isUSerExits(String userId); // Fixed typo

     boolean isUserExitsByEmail(String email);

     List<User> getAllUserList ();

     User getEmailByUser(String email);
}
