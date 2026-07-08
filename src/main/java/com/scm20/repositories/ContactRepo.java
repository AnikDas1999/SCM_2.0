package com.scm20.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm20.entities.Contact;
import com.scm20.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact, String> {
    
    // 🔍 Find the contact by user
    Page<Contact> findByUser(User user, Pageable pageable);

    // 🔍 CUSTOM QUERY METHOD FIXED: Correct page metadata wrapper integration
    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId")
    Page<Contact> findByUserId(@Param("userId") String userId, Pageable pageable);
}