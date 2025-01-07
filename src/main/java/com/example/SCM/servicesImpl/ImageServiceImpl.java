package com.example.SCM.servicesImpl;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.example.SCM.Constants.AppConstants;
import com.example.SCM.services.ImageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageServices {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile profileImage,String fileName ) {



        try {
            byte[] data = new byte[profileImage.getInputStream().available()];
            profileImage.getInputStream().read(data);

            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                    "public_id",fileName
            ));

            return this.getUrlFromPublicId(fileName);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getUrlFromPublicId(String publicId) {
        return  cloudinary
                .url()
                .transformation(
                        new Transformation<>()
                                .width(AppConstants.CONTACT_IMAGE_WIDTH)
                                .height(AppConstants.CONTACT_IMAGE_HEIGHT)
                                .crop(AppConstants.CONTACT_IMAGE_CROP)
                )
                .generate(publicId);
    }
}
