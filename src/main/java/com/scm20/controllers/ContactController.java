package com.scm20.controllers;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm20.entities.Contact;
import com.scm20.entities.User;
import com.scm20.forms.ContactForm;
import com.scm20.helpers.Helper;
import com.scm20.services.ContactService;
import com.scm20.services.UserService;
import com.scm20.services.imageService; 

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private imageService imageService; 

    @RequestMapping("/add")
    public String addContactView(Model model) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @PostMapping("/add")
    public String saveContact(
            @Valid @ModelAttribute("contactForm") ContactForm contactForm, 
            BindingResult bindingResult,                                    
            Authentication authentication, 
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            logger.error("Validation failed for contact form submission: {}", bindingResult.getAllErrors());
            return "user/add_contact";
        }
        
        String userName = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(userName);
        
        String fileURL = null;
        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            logger.info("file information : {}", contactForm.getContactImage().getOriginalFilename()); 
            fileURL = imageService.uploadImage(contactForm.getContactImage()); 
        }
        
        Contact contact = new Contact();
        contact.setName(contactForm.getName()); 
        contact.setFavorite(contactForm.isFavorite()); 
        contact.setEmail(contactForm.getEmail()); 
        contact.setPhoneNumber(contactForm.getPhoneNumber()); 
        contact.setAddress(contactForm.getAddress()); 
        contact.setDescription(contactForm.getDescription()); 
        contact.setUser(user); 
        
        contact.setLinkedInLink(contactForm.getLinkedinLink()); 
        contact.setWebsiteLink(contactForm.getWebsiteLink()); 
        contact.setPicture(fileURL); 

        contactService.save(contact);
        logger.info("Form Data Successfully Saved: {}", contactForm);

        return "redirect:/user/contacts/add?success";
    }

    @RequestMapping
    public String viewContacts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "2") int size, 
            Model model, 
            Authentication authentication
    ) { 
        String username = Helper.getEmailOfLoggedInUser(authentication); 
        User user = userService.getUserByEmail(username); 
        
        Page<Contact> pageContacts = contactService.getByUser(user, page, size); 
        
        model.addAttribute("contacts", pageContacts);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size); 
        
        return "user/contacts"; 
    }
}