//package com.example.SCM.servicesImpl;
//
//import com.example.SCM.Constants.AppConstants;
//import com.example.SCM.Exception.ResourceNotFoundException;
//import com.example.SCM.entity.User;
//import com.example.SCM.helper.Helper;
//import com.example.SCM.repo.UserRepo;
//import com.example.SCM.services.EmailService;
//import com.example.SCM.services.UserServices;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//public class UserServicesImpl implements UserServices {
//
//    @Autowired
//    private UserRepo userRepo;
//
//    @Autowired
//    private EmailService emailService;
//
//    private Logger logger= LoggerFactory.getLogger(this.getClass());
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    public User saveUser(User user) {
//
//        String userId = UUID.randomUUID().toString();
//        user.setUserId(userId);
//        //password Encode
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        //set User Role
//        user.setRoleList(List.of(AppConstants.ROLE_USER));
//        // user.setEnabled(true);
//
//        String emailToken = UUID.randomUUID().toString();
//        user.setEmailToken(emailToken);
//        User savedUser = userRepo.save(user);
//        String emailLink = Helper.getLinkForEmailVerification(emailToken);
//
//        emailService.sendEmail(savedUser.getEmail(),"Verify Account : Smart Contact Manager",emailLink);
//        return savedUser;
//    }
//
//    @Override
//    public Optional<User> getUserById(String id) {
//        return userRepo.findById(id);
//    }
//
//    @Override
//    public Optional<User> updateUser(User user) {
//        User user1 = userRepo.findById(user.getUserId()).orElseThrow(()-> new  ResourceNotFoundException("User Not Found"));
//
//        user1.setName(user.getName());
//        user1.setEmail(user.getEmail());
//        if(!user.getPassword().isEmpty())
//        {
//            //password Encode
//            user1.setPassword(passwordEncoder.encode(user.getPassword()));
//        }
//        user1.setAbout(user.getAbout());
//        user1.setPhoneNumber(user.getPhoneNumber());
//        user1.setProfilePic(user.getProfilePic());
//        user1.setEnabled(user.isEnabled());
//        user1.setEmailVerified(user.isEmailVerified());
//        user1.setPhoneVerified(user.isPhoneVerified());
//        user1.setProvider(user.getProvider());
//        user1.setProviderId(user.getProviderId());
//
//        //save user
//
//        User save = userRepo.save(user1);
//
//        return Optional.ofNullable(save);
//
//    }
//
//    @Override
//    public void deleteUser(String id) {
//        User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found") );
//        userRepo.delete(user);
//    }
//
//    @Override
//    public boolean isUSerExits(String userId) {
//        User user = userRepo.findById(userId).orElse(null);
//
//        return user != null ? true : false;
//    }
//
//    @Override
//    public boolean isUserExitsByEmail(String email) {
//
//        User user=userRepo.findByEmail(email).orElse(null);
//
//        return user != null ? true : false;
//    }
//
//    @Override
//    public List<User> getAllUserList() {
//        return userRepo.findAll();
//    }
//
//    @Override
//    public User getEmailByUser(String email) {
//        return  userRepo.findByEmail(email).orElse(null);
//    }
//}

package com.example.SCM.servicesImpl;

import com.example.SCM.Constants.AppConstants;
import com.example.SCM.Exception.ResourceNotFoundException;
import com.example.SCM.entity.User;
import com.example.SCM.helper.Helper;
import com.example.SCM.repo.UserRepo;
import com.example.SCM.services.EmailService;
import com.example.SCM.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmailService emailService;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {

        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        //password Encode
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //set User Role
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        // user.setEnabled(true);

        String emailToken = UUID.randomUUID().toString();
        user.setEmailToken(emailToken);
        User savedUser = userRepo.save(user);
        String emailLink = Helper.getLinkForEmailVerification(emailToken);

        emailService.sendEmail(savedUser.getEmail(),"Verify Account : Smart Contact Manager",emailLink);
        return savedUser;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User user1 = userRepo.findById(user.getUserId()).orElseThrow(()-> new  ResourceNotFoundException("User Not Found"));

        user1.setName(user.getName());
        user1.setEmail(user.getEmail());
        if(!user.getPassword().isEmpty())
        {
            //password Encode
            user1.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user1.setAbout(user.getAbout());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setProfilePic(user.getProfilePic());
        user1.setEnabled(user.isEnabled());
        user1.setEmailVerified(user.isEmailVerified());
        user1.setPhoneVerified(user.isPhoneVerified());
        user1.setProvider(user.getProvider());
        user1.setProviderId(user.getProviderId());

        //save user

        User save = userRepo.save(user1);

        return Optional.ofNullable(save);

    }


    @Override
    public void deleteUser(String id) {
        User user = userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User Not Found") );
        userRepo.delete(user);
    }

    @Override
    public boolean isUSerExits(String userId) {
        User user = userRepo.findById(userId).orElse(null);

        return user != null ? true : false;
    }

    @Override
    public boolean isUserExitsByEmail(String email) {

        User user=userRepo.findByEmail(email).orElse(null);

        return user != null ? true : false;
    }

    @Override
    public List<User> getAllUserList() {
        return userRepo.findAll();
    }

    @Override
    public User getEmailByUser(String email) {
        return  userRepo.findByEmail(email).orElse(null);
    }
}