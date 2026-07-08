package com.scm20.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm20.entities.User;
import com.scm20.helpers.AppConstants;
import com.scm20.helpers.ResourceNotFoundException;
import com.scm20.repositories.UserRepo;
import com.scm20.services.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo; 

    private Logger logger = LoggerFactory.getLogger(this.getClass()); 

    @Override
    public User saveUser(User user) {
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
        
        // Encode password input safely
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Set standard baseline user roles
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        
        logger.info("Saving new user registration via Form Login: " + user.getEmail());
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id); 
    }

    @Override
    public Optional<User> updateUser(User user) {
        User user2 = userRepo.findById(user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found")); 

        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVerified(user.isEmailVerified());
        user2.setPhoneVerified(user.isPhoneVerified());
        user2.setProvider(user.getProvider());
        user2.setProviderUserId(user.getProviderUserId());
        
        User save = userRepo.save(user2);
        return Optional.ofNullable(save);
    }

    @Override
    public void deleteUser(String id) {
        User user2 = userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepo.delete(user2);
    }

    @Override
    public boolean isUserExist(String userId) {
        return userRepo.findById(userId).isPresent();
    }

    @Override
    public boolean isUserExistByEmail(String email) {
        return userRepo.findByEmail(email).isPresent();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // 🔑 UPDATED: Matches the logic shown in your screenshot perfectly
    @Override
    public User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElse(null);
    }
}