package q.tiger.sstore2.service;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import q.tiger.sstore2.exception.StorageException;
import q.tiger.sstore2.model.Product;
import q.tiger.sstore2.repository.ProductRepo;

public class ProductService {
    @Value("${upload.path.product}")
    private String path;

    private ProductRepo productRepo;

    public void uploadFile(Product p, MultipartFile file){
        if(file.isEmpty()){
            throw new StorageException("Failed to store empty file.");
        }
        String fileName = file.getOriginalFilename();
        try {
            var is = file.getInputStream();
            Files.copy(is, Paths.get(path + p.getId() + ".jpg"), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            var msg = String.format("Failed to store file %s", fileName);
            throw new StorageException(msg, e);
        }
    }
    
}
