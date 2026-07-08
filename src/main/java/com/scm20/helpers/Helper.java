package com.scm20.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

public class Helper {

    // 🔑 FIXED: Changed parameter type from Principal to Authentication
    public static String getEmailOfLoggedInUser(Authentication authentication) {
        
        if (authentication == null) {
            return null;
        }

        // 1. Check if login is via OAuth2 Providers (Google / GitHub)
        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
            DefaultOAuth2User oauthUser = (DefaultOAuth2User) authToken.getPrincipal();
            String provider = authToken.getAuthorizedClientRegistrationId().toUpperCase();

            if (provider.equals("GOOGLE")) {
                return oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString() : "";
            } 
            else if (provider.equals("GITHUB")) {
                String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString() : "";
                
                if (email.isEmpty()) {
                    String login = oauthUser.getAttribute("login") != null ? oauthUser.getAttribute("login").toString() : oauthUser.getName();
                    email = login.toLowerCase() + "@github.com";
                }
                return email;
            }
        }
        
        // 2. Check if login is via Standard Form Login
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            return authentication.getName(); 
        }

        return authentication.getName();
    }
}