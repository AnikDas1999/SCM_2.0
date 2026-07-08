package com.scm20.services.impl;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.scm20.entities.Contact;
import com.scm20.entities.User;
import com.scm20.repositories.ContactRepo;
import com.scm20.services.ContactService;

@Service 
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo; 

    @Override
    public Contact save(Contact contact) {
        if (contact.getId() == null || contact.getId().isEmpty()) {
            contact.setId(UUID.randomUUID().toString());
        }
        return contactRepo.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        Contact contactOld = contactRepo.findById(contact.getId())
                .orElseThrow(() -> new RuntimeException("Contact not found"));
                
        contactOld.setName(contact.getName());
        contactOld.setEmail(contact.getEmail());
        contactOld.setPhoneNumber(contact.getPhoneNumber());
        contactOld.setAddress(contact.getAddress());
        contactOld.setDescription(contact.getDescription());
        contactOld.setFavorite(contact.isFavorite());
        contactOld.setWebsiteLink(contact.getWebsiteLink());
        contactOld.setLinkedInLink(contact.getLinkedInLink());
        
        if (contact.getPicture() != null) {
            contactOld.setPicture(contact.getPicture());
        }
        return contactRepo.save(contactOld);
    }

    @Override
    public List<Contact> getAll() { return contactRepo.findAll(); }

    @Override
    public Contact getById(String id) {
        return contactRepo.findById(id).orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    @Override
    public void delete(String id) {
        Contact contact = contactRepo.findById(id).orElseThrow(() -> new RuntimeException("Contact not found"));
        contactRepo.delete(contact);
    }

    @Override
    public List<Contact> search(String name, String email, String phoneNumber) { return null; }

    @Override
    public Page<Contact> getByUser(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return contactRepo.findByUser(user, pageable);
    }

    @Override
    public Page<Contact> getByUserId(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return contactRepo.findByUserId(userId, pageable);
    }
}