package com.scm20.entities;

import jakarta.persistence.CascadeType; // 🔑 Added this import to fix the CascadeType compilation error!
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Contact {

    @Id
    private String id;
    
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String picture;

    @Column(columnDefinition = "TEXT")
    private String description;

    private boolean favorite = false;
    private String websiteLink;
    private String linkedInLink;

    // Relationship mapping back to User
    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<SocialLink> links = new ArrayList<>();
}