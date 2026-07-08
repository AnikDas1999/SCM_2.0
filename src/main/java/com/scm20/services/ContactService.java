package com.scm20.services;

import java.util.List;
import org.springframework.data.domain.Page;
import com.scm20.entities.Contact;
import com.scm20.entities.User;

public interface ContactService {

    // Save contacts
    Contact save(Contact contact);

    // Update contact
    Contact update(Contact contact);

    // Get contacts
    List<Contact> getAll();

    // Get contact by id
    Contact getById(String id);

    // Delete contact
    void delete(String id);

    // Search contact
    List<Contact> search(String name, String email, String phoneNumber);

    // Paginated discovery lookups
    Page<Contact> getByUserId(String userId, int page, int size);

    // Get contacts by user object
    Page<Contact> getByUser(User user, int page, int size);
}