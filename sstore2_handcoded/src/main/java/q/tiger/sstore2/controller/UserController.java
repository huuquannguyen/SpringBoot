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

import q.tiger.sstore2.exception.StorageException;
import q.tiger.sstore2.model.User;
import q.tiger.sstore2.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String showUserInfo(HttpSession session, Model model){
        var user = session.getAttribute("userSession");
        if(user instanceof User){
            return "userInfo";
        }else{
            return "redirect:/product";
        }
    }

    @GetMapping("/update")
    public String updateForm(HttpSession session, Model model){
        var user = session.getAttribute("userSession");
        if(user instanceof User){
            model.addAttribute("user", user);
            return "updateInfo";
        }else{
            return "redirect:/product";
        }
    }

    @PostMapping("/update")
    public String processUpdateUserInfo(@Valid @ModelAttribute User user, BindingResult result, HttpSession session){
        if(user.getPhoto() == null){
            result.addError(new FieldError("user", "photo", "Photo is required"));
        }
        if(result.hasErrors()){
            return "updateInfo";
        }
        else {
            try{
                userService.uploadFile(user.getPhoto());
            } catch (StorageException e) {
                result.addError(new FieldError("user", "photo", e.getMessage()));
            }
            if(result.hasErrors()){
                return "updateInfo";
            }else{
                userService.updateUserInfo(user, session);
                return "userInfo";
            }
        }
    }

}
