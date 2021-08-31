package q.tiger.sstore2.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import q.tiger.sstore2.model.Product;
import q.tiger.sstore2.service.CartService;
import q.tiger.sstore2.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;

    @ModelAttribute("itemNumber")
    public int numberOfItems(HttpSession session){
        return cartService.getNumberOfProductInCart(session);
    }
    
    @GetMapping
    public String showProducts(HttpSession session, Model model, RedirectAttributes redirectAttributes){
        var isLogin = session.getAttribute("userSession");
        if(isLogin != null){   
            model.addAttribute("products", productService.getAllProduct());
            return "product";
        }else{
            redirectAttributes.addFlashAttribute("loginFromProduct", true);
            return "redirect:/login";
        }
    }

    @PostMapping
    public String processSearch(@RequestParam("key") String key, Model model){
        List<Product> products = productService.searchProduct(key);
        model.addAttribute("products", products);
        return "product";
    }

    @GetMapping("/{id}")
    public String showProductDetail(@PathVariable("id") int id, Model model){
        Optional<Product> product = productService.findById(id);
        if(product.isPresent()){
            model.addAttribute("product", product.get());
            return "productDetail";
        }else{
            return "product";
        }
    }

    
}
