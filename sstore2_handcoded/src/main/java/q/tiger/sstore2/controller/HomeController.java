package q.tiger.sstore2.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import q.tiger.sstore2.service.CartService;

@Controller
@RequestMapping("/")
public class HomeController {
    
    @Autowired
    private CartService cartService;

    @ModelAttribute("itemNumber")
    public int numberOfItems(HttpSession session){
        return cartService.getNumberOfProductInCart(session);
    }

    @GetMapping
    public String showHome(Model model){
        return "home";
    }
}
