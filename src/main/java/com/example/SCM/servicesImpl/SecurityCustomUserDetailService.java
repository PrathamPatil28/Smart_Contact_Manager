package com.example.SCM.servicesImpl;

import com.example.SCM.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class SecurityCustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // apne user ko load karana hai
        UserDetails userDetails = userRepo.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + username));
        System.out.println("Fetched UserDetails: " + userDetails);
        return userDetails;
    }

}