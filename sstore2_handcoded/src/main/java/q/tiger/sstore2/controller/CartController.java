package q.tiger.sstore2.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import q.tiger.sstore2.model.Cart;
import q.tiger.sstore2.service.CartService;
import q.tiger.sstore2.service.UserService;

@Controller
@RequestMapping("/cart")
public class CartController {
    
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @ModelAttribute("itemNumber")
    public int numberOfItems(HttpSession session){
        return cartService.getNumberOfProductInCart(session);
    } 

    @GetMapping("/add/{id}")
    public String addtoCart(@PathVariable int id, HttpSession session){
        if(userService.isLogin(session)){
            cartService.addToCart(session, id);
        }
        return "redirect:/product";
    }

    @GetMapping("/mycart")
    public String showCart(HttpSession session, Model model){
        Cart cart = cartService.getCart(session);
        model.addAttribute("cart", cart);
        return "myCart";
    }

    @GetMapping("/increaseQuantity/{id}")
    public String increaseQuantity(HttpSession session, @PathVariable("id") int id){
        cartService.addToCart(session, id);
        return "redirect:/cart/mycart";
    }

    @GetMapping("/decreaseQuantity/{id}")
    public String decreaseQuantity(HttpSession session, @PathVariable("id") int id){
        cartService.decreaseProductQuantity(session, id);
        return "redirect:/cart/mycart";
    }

    @GetMapping("/deleteOrderLine/{id}")
    public String deleteOrderLine(HttpSession session, @PathVariable("id") int id){
        cartService.deleteOrderLine(id, session);
        return "redirect:/cart/mycart";
    }
}
