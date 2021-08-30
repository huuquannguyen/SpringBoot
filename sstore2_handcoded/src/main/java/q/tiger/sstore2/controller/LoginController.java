package q.tiger.sstore2.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import q.tiger.sstore2.model.Account;
import q.tiger.sstore2.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;
    
    @GetMapping
    public String loginForm(Model model){
        model.addAttribute("account", new Account());
        return "login";
    }

    @PostMapping
    public String processLogin(@Valid @ModelAttribute Account account, BindingResult result, HttpSession session){
        if(!userService.login(account)){
            ObjectError objectError = new ObjectError("accountWrong", "Wrong username or password");
            result.addError(objectError);
        }
        if(result.hasErrors()){
            return "login";
        }else{
            userService.saveUserToSession(account, session);
            return "redirect:/product";
        }
    }
}
