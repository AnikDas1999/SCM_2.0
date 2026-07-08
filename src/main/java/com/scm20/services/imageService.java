package com.scm20.services;

import org.springframework.web.multipart.MultipartFile;

public interface imageService {
    String uploadImage(MultipartFile contactImage);
    String getUrlFromPublicId(String publicId);
}