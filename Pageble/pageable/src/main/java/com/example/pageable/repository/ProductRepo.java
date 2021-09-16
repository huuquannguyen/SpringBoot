package com.example.pageable.repository;

import java.util.List;

import com.example.pageable.domain.Product;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepo extends CrudRepository<Product, Long> {
    
    @Query("SELECT new com.example.pageable.domain.Product(p.id, p.name, p.brand, p.madeIn, p.price) FROM product as p")
    public List<Product> findAllProductPageble(Pageable pageable);
}
