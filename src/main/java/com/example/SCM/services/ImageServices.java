package com.example.SCM.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageServices {

    String uploadImage(MultipartFile profileImage, String filename);

    String getUrlFromPublicId(String publicId);
}
