package com.example.pageable.controller;


import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.validation.Valid;

import com.example.pageable.domain.Product;
import com.example.pageable.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(path = {"/", "product"})
    public String showFirstTenProduct(Model model){
        List<Product> products = productService.showProductsInPageN(0);
        model.addAttribute("products", products);
        model.addAttribute("currPage", 0);
        return "product";
    }
    
    @GetMapping("/product/{page}")
    public String showProducts(@PathVariable int page, Model model){
        List<Product> products = productService.showProductsInPageN(page);
        model.addAttribute("products", products);
        model.addAttribute("currPage", page);
        return "product";
    }

    @GetMapping("product/delete/{id}")
    public String deleteProduct(@PathVariable Long id, Model model){
        productService.deleteProduct(id);
        return "redirect:/product";
    }

    @GetMapping("/product/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model){
        Optional<Product> product = productService.findProductById(id);
        if(product.isPresent()){
            model.addAttribute("product", product.get());
            return "editProduct";
        }else{
            return "redirect:/product";
        }
    }

    @PostMapping("/product/edit/{id}")
    public String processUpdate(@Valid Product product, BindingResult result){
        if(!result.hasErrors()){
            productService.updateProduct(product);
            return "redirect:/product";
        }else{
            return "editProduct";
        }
    }

    @ModelAttribute
    public void getNumerOfPage(Model model){
        int productNum = (int) productService.getNumerOfProduct();
        model.addAttribute("productNum", productNum);
        int num = 0;
        if(productNum % 10 != 0){
            num = (productNum / 10) + 1;
        }else{
            num = productNum / 10;
        }
        int[] pageNum = IntStream.range(0, num).toArray();
        model.addAttribute("pageNum", pageNum);
    }
}
