package com.example.pageable.service;

import java.util.List;
import java.util.Optional;

import com.example.pageable.domain.Product;
import com.example.pageable.repository.ProductRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;



@Service
public class ProductService {
    
    @Autowired
    private ProductRepo productRepo;

    public List<Product> showProductsInPageN(int n){
        return productRepo.findAllProductPageble(PageRequest.of(n, 10));
    }

    public long getNumerOfProduct(){
        return productRepo.count();
    }

    public void deleteProduct(Long id){
        productRepo.deleteById(id);
    }

    public Optional<Product> findProductById(Long id){
        return productRepo.findById(id);
    }

    public void updateProduct(Product product){
        productRepo.save(product);
    }
}
