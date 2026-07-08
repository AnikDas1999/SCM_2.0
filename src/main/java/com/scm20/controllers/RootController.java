package com.scm20.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken; // 🔑 NEEDED FOR CHECK
import org.springframework.security.core.Authentication; 
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm20.entities.User;
import com.scm20.helpers.Helper;
import com.scm20.services.UserService; 

@ControllerAdvice
public class RootController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService; 

    @ModelAttribute
    public void addLoggedInUserInformation(Model model, Authentication authentication) {
        
        // 🔑 FIXED: Clear check for null OR anonymous guest authentication
        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            logger.info("Guest user session detected - skipping layout attribute binding.");
            return;
        }
        
        System.out.println("Adding logged in user information to the model");
        String username = Helper.getEmailOfLoggedInUser(authentication);
        logger.info("User logged in: {}", username);
        
        User user = userService.getUserByEmail(username);
        
        if (user != null) {
            System.out.println(user.getName());
            System.out.println(user.getEmail());
            model.addAttribute("loggedInUser", user);
        }
    }
}