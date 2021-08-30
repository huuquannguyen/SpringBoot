package q.tiger.sstore2.service;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import q.tiger.sstore2.exception.StorageException;
import q.tiger.sstore2.model.Product;
import q.tiger.sstore2.repository.ProductRepo;

@Service
public class ProductService {
    @Value("${upload.path.product}")
    private String path;
    @Autowired
    private ProductRepo productRepo;

    public List<Product> searchProduct(String key){
        return productRepo.getall()
        .stream()
        .filter(p -> p.getName().toLowerCase().contains(key.toLowerCase()))
        .toList();
    }

    public List<Product> getAllProduct(){
        return productRepo.getall();
    }

    public Optional<Product> findById(int id){
        return productRepo.searchById(id);
    }

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
