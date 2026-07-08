package com.scm20.controllers; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm20.entities.User; 
import com.scm20.forms.UserForm;
import com.scm20.helpers.Message;
import com.scm20.helpers.MessageType;
import com.scm20.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PageController {
    
    @Autowired
    private UserService userService;

    // Matches image_d33c8f.png exactly
    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

    // Home route
    @GetMapping({"/home"})
    public String home(Model model) {
        System.out.println("Home page controller");
        model.addAttribute("name", "Substring Factory");
        model.addAttribute("YoutubeChannel", "code with anik");
        model.addAttribute("Github", "https://github.com/");
        return "home";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @GetMapping("/services")
    public String servicesPage() {
        return "services";
    }

    @GetMapping("/contact")
    public String contactPage() {
        return "contact";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        UserForm userForm = new UserForm();
        model.addAttribute("userForm", userForm);
        return "register";
    }

    @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult, HttpSession session) {
        System.out.println("Processing registration");
        
        if(rBindingResult.hasErrors()){
            return "register";
        }
        
        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("https://www.learncodewithdurgesh.com/_next/image?url=%2Fimages%2Fdefault_profile.png&w=1080&q=75");
        
        // 🔑 FIXED: Bypasses the missing isEnabled() method in UserForm to fix the red line!
        user.setEnabled(true); 
                
        User savedUser = userService.saveUser(user);
        
        System.out.println("user saved :");
        Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();
        session.setAttribute("message", message);        
        
        return "redirect:/register";
    }
}