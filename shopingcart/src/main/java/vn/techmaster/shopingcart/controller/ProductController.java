package vn.techmaster.shopingcart.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import vn.techmaster.shopingcart.model.Info;
import vn.techmaster.shopingcart.repository.ProductRepository;
import vn.techmaster.shopingcart.service.CartService;
import vn.techmaster.shopingcart.service.EmailService;

@Controller
public class ProductController {
  @Autowired private ProductRepository productRepository;
  @Autowired private CartService cartService;
  @Autowired private EmailService emailService;

  @GetMapping("/")
  public String showHomePage(HttpSession session, Model model) {
    model.addAttribute("products", productRepository.getAll());
    model.addAttribute("cartCount", cartService.countItemInCart(session));
    return "index";
  }

  @GetMapping("/buy/{id}")
  public String buy(@PathVariable(name = "id") Long id, HttpSession session, Model model) {
    cartService.addToCart(id, session);
    return "redirect:/";
  }

  @GetMapping("/checkout")
  public String checkout(HttpSession session, Model model) {
    model.addAttribute("cart", cartService.getCart(session));
    model.addAttribute("info", new Info());
    return "checkout";
  }

  @PostMapping("/checkout")
  public String processForm(Info info, BindingResult bindingResult, HttpSession session){
    if(bindingResult.hasErrors()){
      return "checkout";
    }
    emailService.sendEmail(info.getEmail(), cartService.getCart(session).toString());
    return "redirect:/";
  }

  @GetMapping("/increase/{id}")
  public String increaseProduct(@PathVariable(name = "id") Long id, HttpSession session, Model model){
    cartService.addToCart(id, session);
    model.addAttribute("cart", cartService.getCart(session));
    return "redirect:/checkout";
  }

  @GetMapping("/decrease/{id}")
  public String decreaseProduct(@PathVariable(name = "id")Long id, HttpSession session, Model model){
    cartService.decreaseProduct(id, session);
    model.addAttribute("cart", cartService.getCart(session));
    return "redirect:/checkout";
  }
}
