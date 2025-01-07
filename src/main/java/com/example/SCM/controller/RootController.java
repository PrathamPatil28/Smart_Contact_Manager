package com.example.SCM.controller;

import com.example.SCM.entity.User;
import com.example.SCM.helper.Helper;
import com.example.SCM.services.UserServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class RootController {

     Logger logger = LoggerFactory.getLogger(RootController.class);
     @Autowired
    UserServices userServices;

    @ModelAttribute
    public void addLoginUserInformation(Model model , Authentication authentication){

        if (authentication==null){
            return;
        }

        System.out.println("Adding Login User Information to the Model");

        String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User Logged In : {}" ,username);

        //fetching data from database
        User user = userServices.getEmailByUser(username);

        System.out.println(user);

//        if (user==null){
//            model.addAttribute("loginUser" , null);
//        }
//        else {
//        System.out.println(user.getName());
//        System.out.println(user.getEmail());

        model.addAttribute("loginUser" , user);
//        }

    }

}
