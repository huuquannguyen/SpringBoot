package com.example.pageable.service;

import java.util.List;
import java.util.Optional;

import com.example.pageable.domain.Product;
import com.example.pageable.repository.ProductRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;



@Service
public class ProductService {
    
    @Autowired
    private ProductRepo productRepo;

    public List<Product> showProductsInPageN(int pageNum, String sortField, String sortDir){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum, 10, sort);
        return productRepo.findAllProductPageble(pageable);
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
