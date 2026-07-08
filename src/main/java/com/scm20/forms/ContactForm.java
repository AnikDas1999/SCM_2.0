package com.scm20.forms;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ContactForm {

    @NotBlank(message = "Name is required") // 🔑 Ensures name is not empty or spaces
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email Address") // 🔑 Validates standard email structure (e.g., test@test.com)
    private String email;

    @NotBlank(message = "Phone Number is required") //
    @Pattern(regexp = "^[0-9]{10}$", message = "Invalid Phone Number") // 🔑 Restricts format to exactly 10 numeric digits
    private String phoneNumber;

    @NotBlank(message = "Address is required") // 🔑 Ensures contact address is filled
    private String address;

    private String description;
    
    // 🔑 Changed to match your instructor's code setup to seamlessly align with your controller methods
    private boolean favorite;
    
    private String websiteLink;
    private String linkedinLink;
    private MultipartFile contactImage;
}