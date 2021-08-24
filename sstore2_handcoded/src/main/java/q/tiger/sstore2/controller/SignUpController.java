package q.tiger.sstore2.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import q.tiger.sstore2.model.User;
import q.tiger.sstore2.service.UserService;

@Controller
@RequestMapping("/signup")
public class SignUpController {
    
    @Autowired
    private UserService userService;

    @GetMapping
    public String signUp(Model model){
        model.addAttribute("user", new User());
        return "signUpForm";
    }

    @PostMapping
    public String processSignUp(@Valid @ModelAttribute User user, BindingResult result){
        if(!userService.signUp(user)){
            result.addError(new FieldError("user", "account.username", "This username is already existed"));
        }
        if(result.hasErrors()){
            return "signUpForm";
        }else{
            userService.addUserToDB(user);
            return "redirect:/login";
        }
    }
}
