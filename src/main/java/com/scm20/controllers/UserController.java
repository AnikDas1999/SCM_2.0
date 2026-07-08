package com.scm20.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute; // 🔑 Added for sharing user data
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm20.entities.User;
import com.scm20.helpers.Helper;
import com.scm20.services.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    
    // 🔑 UPDATED FROM SCREENSHOT: Common method to add logged-in user details to model attributes
    
    
    // User dashboard page
    @RequestMapping(value = "/dashboard")
    public String userDashboard() {
        return "user/dashboard";
    }
    
    // User profile page
    @RequestMapping(value = "/profile")
    public String userProfile() { 
        // 🔑 Cleaned up: Parameters are handled globally by addLoggedInUserInformation above
        return "user/profile";
    }
}