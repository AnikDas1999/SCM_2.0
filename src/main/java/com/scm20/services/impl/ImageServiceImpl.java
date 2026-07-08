package com.scm20.services.impl;

import java.io.IOException;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation; // 🔑 Required for applying transformations
import com.cloudinary.utils.ObjectUtils;
import com.scm20.services.imageService; // 🔑 Corrected capitalization reference 

@Service
public class ImageServiceImpl implements imageService {

    private final Cloudinary cloudinary;

    public ImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String uploadImage(MultipartFile contactImage) {
        
        // 🔑 1. Generate a completely unique file name string using UUID
        String filename = UUID.randomUUID().toString();
        
        try {
           
            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            
           
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                "public_id", filename
            ));
            
            
            return this.getUrlFromPublicId(filename);
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        
    }

    @Override
    public String getUrlFromPublicId(String publicId) {
        
        // 🔑 5. Generates optimized asset Delivery URLs featuring a auto-cropped aspect ratio
        return cloudinary.url()
                .transformation(
                    new Transformation<>()
                        .width(500)
                        .height(500)
                        .crop("fill")
                )
                .generate(publicId);
    }
}