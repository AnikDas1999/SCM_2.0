package com.scm20.config;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Autowired
    private OAuthAuthenticationSuccessHandler handler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        
        // 1. URL Protection Rules
        httpSecurity.authorizeHttpRequests(authorize -> authorize
            .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
            .requestMatchers("/user/**").authenticated()
            .anyRequest().permitAll()
        );

        // 2. Custom Login Settings
        httpSecurity.formLogin(formLogin -> formLogin
            .loginPage("/login")
            .loginProcessingUrl("/authenticate")
            .usernameParameter("username")
            .passwordParameter("password")
            .failureHandler((request, response, exception) -> response.sendRedirect("/login?error=true"))
            .successHandler((request, response, authentication) -> response.sendRedirect("/user/profile"))
            .permitAll()
        );

        // 3. Disable CSRF (Allows standard GET links to trigger logout safely)
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // 4. Configure Logout Handling (Using the direct string url)
        httpSecurity.logout(logoutForm -> {
            logoutForm.logoutUrl("/do-logout"); 
            logoutForm.logoutSuccessUrl("/login?logout=true");
            logoutForm.invalidateHttpSession(true);
            logoutForm.clearAuthentication(true);
            logoutForm.permitAll();
        });

        // 5. Custom OAuth2 Login Settings
        httpSecurity.oauth2Login(oauth2 -> oauth2
            .loginPage("/login") // Tells Spring to use your custom login page instead of the white screen
            .successHandler(handler) // 🔑 FIXED: Changed from 'null' to use your injected custom success handler
        );

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}