package com.scm20.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    private String userId;
    // Find your name property inside com.scm20.entities.User and change it to this:
    @Column(name = "user_name", nullable = false)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    
    @Column(length = 1000)
    private String about;
    private String phoneNumber;
    private String profilePic;

    // Standard flags needed for verification tracking
    private boolean enabled = true;
    private boolean emailVerified = false;
    private boolean phoneVerified = false;

    // Provider fields for social log-ins (e.g., SELF, GOOGLE)
    private String provider = "SELF";
    private String providerUserId;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList;

    // 🔑 Spring Security UserDetails Custom Methods Mapping
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roleList.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}