package com.techmaster.crud.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.techmaster.crud.Exception.StorageException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PeopleService {
    
    @Value("${upload.path}")
    private String path;

    public void uploadFile(MultipartFile file){
        if(file.isEmpty()){
            throw new StorageException("Failed to add this photo");
        }
        String fileName = file.getOriginalFilename();
        try {
            var is = file.getInputStream();
            Files.copy(is, Paths.get(path + fileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            var mgs = String.format("Failed to store file %s", fileName);
            throw new StorageException(mgs, e);
        }
    }
}
