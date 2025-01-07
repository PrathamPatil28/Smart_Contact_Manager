package com.example.SCM.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Objects;

public class Helper {
    public static String getEmailOfLoggedInUser(Authentication authentication) {
        Logger logger = LoggerFactory.getLogger(Helper.class);

        Object principal = authentication.getPrincipal();

        // Handle OAuth2 logins
        if (principal instanceof OAuth2User) {
            OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            String clientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            OAuth2User oauth2User = (OAuth2User) principal;

            String email = "";
            if (clientRegistrationId.equalsIgnoreCase("google")) {
                logger.info("Getting email from Google");
                email = oauth2User.getAttribute("email");
            } else if (clientRegistrationId.equalsIgnoreCase("github")) {
                logger.info("Getting email from GitHub");
                email = oauth2User.getAttribute("email") != null
                        ? oauth2User.getAttribute("email")
                        : oauth2User.getAttribute("login") + "@gmail.com";
            }
            return email;
        }

        // Handle local database logins
        logger.info("Getting data From Local DB");
        return authentication.getName(); // Usually returns username or email for local logins
    }

    public static String getLinkForEmailVerification(String emailToken){
         String link= "http://localhost:8080/auth/verify-email?token=" + emailToken;

         return link;
    }
}
