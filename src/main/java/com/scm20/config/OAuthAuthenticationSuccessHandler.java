package com.scm20.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User; 
import org.springframework.security.web.DefaultRedirectStrategy; 
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm20.entities.User; 
import com.scm20.helpers.AppConstants;
import com.scm20.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(OAuthAuthenticationSuccessHandler.class);
    
    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        
        logger.info("OAuthAuthenticationSuccessHandler triggered");
        
        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
        
        // 🔄 Identify the provider (google or github)
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        String provider = authToken.getAuthorizedClientRegistrationId().toUpperCase(); // GOOGLE or GITHUB
        logger.info(provider);
        String email = "";
        String name = "";
        String picture = "";
        
        if (provider.equals("GOOGLE")) {
            // Google extraction
            email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString() : "";
            name = oauthUser.getAttribute("name") != null ? oauthUser.getAttribute("name").toString() : "";
            picture = oauthUser.getAttribute("picture") != null ? oauthUser.getAttribute("picture").toString() : "";
        } 
        else if (provider.equals("GITHUB")) {
            // GitHub extraction
            email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString() : "";
            name = oauthUser.getAttribute("name") != null ? oauthUser.getAttribute("name").toString() : 
                   (oauthUser.getAttribute("login") != null ? oauthUser.getAttribute("login").toString() : "GitHub User");
            picture = oauthUser.getAttribute("avatar_url") != null ? oauthUser.getAttribute("avatar_url").toString() : "";
            
            // Handle private GitHub email fallback safely
            if (email.isEmpty()) {
                String login = oauthUser.getAttribute("login") != null ? oauthUser.getAttribute("login").toString() : oauthUser.getName();
                email = login.toLowerCase() + "@github.com";
            }
        }

        logger.info("Processing login for user email: {} via provider: {}", email, provider);
        
        // Setup user domain structure
        User user1 = new User();
        user1.setEmail(email);
        user1.setName(name);
        user1.setProfilePic(picture);
        user1.setPassword("password");
        user1.setUserId(UUID.randomUUID().toString());
        user1.setProvider(provider); // Dynamic setting: GOOGLE or GITHUB
        user1.setEnabled(true);
        user1.setEmailVerified(true);
        user1.setProviderUserId(oauthUser.getName());
        user1.setRoleList(List.of(AppConstants.ROLE_USER));
        user1.setAbout("This account was created using " + provider.toLowerCase() + ".");
        
        // Save strategy
        User user2 = userRepo.findByEmail(email).orElse(null);
        if (user2 == null) {
            userRepo.save(user1);
            logger.info("New OAuth profile saved contextually: {}", email);
        } else {
            logger.info("Existing profile identified. Logging in: {}", email);
        }
        
        new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile");
    }
}