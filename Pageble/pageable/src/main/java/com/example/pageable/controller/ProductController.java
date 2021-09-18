package com.example.pageable.controller;


import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.validation.Valid;

import com.example.pageable.domain.Product;
import com.example.pageable.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @GetMapping(path = {"/", "/product/{page}"})
    public String showProducts(@PathVariable(name = "page", required = false) Integer page,
                                @RequestParam(name = "sortField", required = false, defaultValue = "id") String sortField,
                                @RequestParam(name = "sortDir", required = false, defaultValue = "asc") String sortDir,
                                 Model model){
        if(page == null){
            page = 0;
        }
        List<Product> products = productService.showProductsInPageN(page, sortField, sortDir);
        String reverseDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("reverseDir", reverseDir);
        String temp = (String) model.getAttribute("reverseDir");
        System.out.println(temp);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("products", products);
        model.addAttribute("currPage", page);
        return "product";
    }

    @GetMapping("product/delete/{currPage}/{id}")
    public String deleteProduct(@PathVariable Long id, @PathVariable String currPage, Model model){
        productService.deleteProduct(id);
        return "redirect:/product/" + currPage;
    }

    @GetMapping("/product/edit/{currPage}/{id}")
    public String editProduct(@PathVariable Long id, @PathVariable String currPage, Model model){
        Optional<Product> product = productService.findProductById(id);
        if(product.isPresent()){
            model.addAttribute("product", product.get());
            return "editProduct";
        }else{
            return "redirect:/product/" + currPage;
        }
    }

    @PostMapping("/product/edit/{currPage}/{id}")
    public String processUpdate(@Valid Product product, BindingResult result, Model model, @PathVariable int currPage){
        if(!result.hasErrors()){
            productService.updateProduct(product);
            return "redirect:/product/" + currPage;
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
