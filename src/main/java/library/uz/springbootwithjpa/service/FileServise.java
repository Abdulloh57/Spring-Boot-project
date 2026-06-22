package library.uz.springbootwithjpa.service;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


public interface FileServise {
    String upload(MultipartFile file);
}

