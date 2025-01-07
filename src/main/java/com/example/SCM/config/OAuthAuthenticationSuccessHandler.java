package com.example.SCM.config;

import com.example.SCM.Constants.AppConstants;
import com.example.SCM.entity.Provider;
import com.example.SCM.entity.User;
import com.example.SCM.repo.UserRepo;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

       Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);

       @Autowired
       private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

            logger.info("OAuthAuthenticationSuccessHandler");

        //identify Provider
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

        logger.info(authorizedClientRegistrationId);


         DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
           logger.info(oauthUser.getName());

        oauthUser.getAttributes().forEach((key, value) -> {
            logger.info("{} : {}", key, value);
        });

        User user = new User();
        user.setUserId(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailVerified(true);
        user.setEnabled(true);
        user.setPassword("dummy");

        if (authorizedClientRegistrationId.equalsIgnoreCase("google")){
            //google
            //google Attribute
            user.setEmail(Objects.requireNonNull(oauthUser.getAttribute("email")).toString());
            user.setProfilePic(Objects.requireNonNull(oauthUser.getAttribute("picture")).toString());
            user.setName(Objects.requireNonNull(oauthUser.getAttribute("name")).toString());
            user.setProviderId(oauthUser.getName());
            user.setProvider(Provider.GOOGLE);
            user.setAbout("this account is created using google");

        } else if (authorizedClientRegistrationId.equalsIgnoreCase("github")) {
            String email = oauthUser.getAttribute("email") != null ? Objects.requireNonNull(oauthUser.getAttribute("email")).toString()
                    : Objects.requireNonNull(oauthUser.getAttribute("login")).toString() + "@gmail.com";
            String picture = Objects.requireNonNull(oauthUser.getAttribute("avatar_url")).toString();
            String name = Objects.requireNonNull(oauthUser.getAttribute("login")).toString();
            String providerId = oauthUser.getName();

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderId(providerId);
            user.setProvider(Provider.GITHUB);
            user.setAbout("this account is created using github");

        }

        else {
            logger.info("OAuthAuthenticationSuccessHandler: Unknown provider");
        }

        //save data in db

        User user2 = userRepo.findByEmail(user.getEmail()).orElse(null);

        if (user2==null){
            userRepo.save(user);
            logger.info("user Saved :{}", user.getEmail());
        }

        new DefaultRedirectStrategy().sendRedirect(request , response , "/user/profile");

        /*  DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
        logger.info(user.getName());

        user.getAttributes().forEach((key,value)->{
            logger.info("{} : {}" , key , value);
        });

        logger.info(user.getAuthorities().toString());


        //save data into database
        String email = user.getAttribute("email").toString();
        String name = user.getAttribute("name").toString();
        String picture = user.getAttribute("picture").toString();


        User user1  = new User();
        user1.setEmail(email);
        user1.setName(name);
        user1.setProfilePic(picture);
        user1.setUserId(UUID.randomUUID().toString());
        user1.setProvider(Provider.GOOGLE);
        user1.setEnabled(true);
        user1.setEmailVerified(true);
        user1.setProviderId(user.getName());
        user1.setRoleList(List.of(AppConstants.ROLE_USER));
        user1.setAbout("this account is created using google");


        User user2 = userRepo.findByEmail(email).orElse(null);

        if (user2==null){
            userRepo.save(user1);
            logger.info("user Saved :{}", email);
        }

         */
    }
}
