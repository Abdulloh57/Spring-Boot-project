package library.uz.springbootwithjpa.service.impl;

import library.uz.springbootwithjpa.service.FileServise;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileServiseImpl implements FileServise {
    private static final String BASE_PATH ="C:/Users/Abdulloh/Desktop/spring-boot-with-jpa/images/";

    public String upload(MultipartFile file){

        String fileName =
                UUID.randomUUID() + "_" +
                        file.getOriginalFilename();

        File dest = new File(BASE_PATH + fileName);

        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new RuntimeException("Rasm yuklashda xatolik : \n"+e);
        }

        return fileName;
    }
}
