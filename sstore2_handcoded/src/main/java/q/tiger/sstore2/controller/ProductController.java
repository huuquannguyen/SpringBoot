package q.tiger.sstore2.controller;

import javax.servlet.http.HttpSession;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("product")
public class ProductController {
    
    @GetMapping
    public String showProducts(HttpSession session, RedirectAttributes rAttributes){
        var isLogin = session.getAttribute("isLogin");
        if(isLogin != null){
            return "product";
        }else{
            rAttributes.addFlashAttribute("loginFromProduct", true);
            return "redirect:/login";
        }
    }
}
