package q.tiger.sstore2.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import q.tiger.sstore2.model.Product;
import q.tiger.sstore2.repository.ProductRepo;

@Controller
@RequestMapping("product")
public class ProductController {
    @Autowired
    private ProductRepo productRepo;
    
    @GetMapping
    public String showProducts(HttpSession session, RedirectAttributes rAttributes, Model model){
        var isLogin = session.getAttribute("userSession");
        if(isLogin != null){   
            model.addAttribute("products", productRepo.getall());
            return "product";
        }else{
            rAttributes.addFlashAttribute("loginFromProduct", true);
            return "redirect:/login";
        }
    }

    @GetMapping("/{id}")
    public String showProductDetail(@PathVariable("id") int id, Model model){
        Optional<Product> product = productRepo.searchById(id);
        if(product.isPresent()){
            model.addAttribute("product", product.get());
            return "productDetail";
        }else{
            return "product";
        }
    }


}
