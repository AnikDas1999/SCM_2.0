package com.scm20.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.scm20.repositories.UserRepo;

@Service // 🔑 CRITICAL: This annotation registers the bean so SecurityConfig can find it!
public class SecurityCustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo; // cite: image_749599.png

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 🔑 Integrated exact custom user fetching logic from screenshot (image_749599.png)
        return userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username)); // cite: image_749599.png
    }
}