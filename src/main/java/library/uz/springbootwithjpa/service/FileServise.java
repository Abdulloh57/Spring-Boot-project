package library.uz.springbootwithjpa.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileServise {

    private static final String BASE_PATH ="C:/Users/Abdulloh/Desktop/spring-boot-with-jpa/images/";

    public String upload(MultipartFile file) throws IOException {

        String fileName =
                UUID.randomUUID() + "_" +
                        file.getOriginalFilename();

        File dest = new File(BASE_PATH + fileName);

        file.transferTo(dest);

        return fileName;
    }



    public byte[] getImage(String imageUrl) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(
                BASE_PATH+"/"+imageUrl
        );
        byte[] bytes = fileInputStream.readAllBytes();
        fileInputStream.close();
        return bytes;
    }


    public String uploadFile(MultipartFile multipartFile) throws IOException {
        System.out.println("FILE_NAME" + multipartFile.getName());
        System.out.println("ORGINAL FILE_NAME" + multipartFile.getOriginalFilename());
        String path = UUID.randomUUID() + ".png";
        FileOutputStream outputStream = new FileOutputStream(
                BASE_PATH + "/" + path
        );
        outputStream.write(multipartFile.getInputStream().readAllBytes());
        outputStream.close();
        return path;
    }
}

