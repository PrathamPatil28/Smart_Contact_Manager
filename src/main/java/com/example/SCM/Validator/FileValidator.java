package com.example.SCM.Validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FileValidator implements ConstraintValidator <ValidFile, MultipartFile>{

    private static final long MAX_FILE_SIZE=1024*1024*5;  //5MB File


    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
          if (file==null || file.isEmpty()){
//              constraintValidatorContext.disableDefaultConstraintViolation();
//              constraintValidatorContext.buildConstraintViolationWithTemplate("File Can't be empty").addConstraintViolation();

              return true;
          }

          //file size
        if (file.getSize()>MAX_FILE_SIZE){
            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext.buildConstraintViolationWithTemplate("File size should be less than 2MB").addConstraintViolation();
            return false;
        }
//        //resolution
//        try {
//            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
//            if (bufferedImage.getHeight())
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

            return true;

    }
}
