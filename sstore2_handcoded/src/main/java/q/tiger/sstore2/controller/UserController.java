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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import q.tiger.sstore2.exception.StorageException;
import q.tiger.sstore2.model.Account;
import q.tiger.sstore2.model.User;
import q.tiger.sstore2.service.CartService;
import q.tiger.sstore2.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @ModelAttribute("itemNumber")
    public int numberOfItems(HttpSession session){
        return cartService.getNumberOfProductInCart(session);
    }

    @GetMapping
    public String showUserInfo(HttpSession session, Model model) {
        var user = session.getAttribute("userSession");
        if (user instanceof User) {
            return "userInfo";
        } else {
            return "redirect:/product";
        }
    }

    @GetMapping("/update")
    public String updateForm(HttpSession session, Model model) {
        var user = session.getAttribute("userSession");
        if (user instanceof User) {
            model.addAttribute("user", user);
            return "updateInfo";
        } else {
            return "redirect:/product";
        }
    }

    @PostMapping("/update")
    public String processUpdateUserInfo(@Valid @ModelAttribute User user, BindingResult result, HttpSession session) {
        if (user.getPhoto() == null) {
            result.addError(new FieldError("user", "photo", "Photo is required"));
        }
        if (result.hasErrors()) {
            return "updateInfo";
        } else {
            try {
                userService.uploadFile(user.getPhoto());
            } catch (StorageException e) {
                result.addError(new FieldError("user", "photo", e.getMessage()));
            }
            if (result.hasErrors()) {
                return "updateInfo";
            } else {
                userService.updateUserInfo(user, session);
                return "userInfo";
            }
        }
    }

    @GetMapping("/confirm")
    public String confirmUser(HttpSession session, Model model) {
        var user = session.getAttribute("userSession");
        if (user instanceof User) {
            User userSession = (User) user;
            String username = userSession.getAccount().getUsername();
            String password = "";
            Account account = new Account(username, password);
            model.addAttribute("account", account);
            return "confirmUser";
        } else {
            return "redirect:/product";
        }
    }

    @PostMapping("/confirm")
    public String processConfirm(@Valid Account account, BindingResult result, HttpSession session, RedirectAttributes redirectAttributes){
        User user = (User) session.getAttribute("userSession");
        System.out.println(account.getUsername() + "-" + account.getPassword());
        System.out.println(user.getAccount().getUsername() + "-" + user.getAccount().getPassword());
        if(!account.equals(user.getAccount())){
            result.addError(new FieldError("account", "password", "Wrong password."));
        }
        if(result.hasErrors()){
            return "confirmUser";
        }else{
            redirectAttributes.addFlashAttribute("isConfirmed", true);
            return "redirect:/user/changePassword";
        }
    }

    @GetMapping("/changePassword")
    public String changePasswordForm(Model model, HttpSession session){
        var isConfirmed = model.getAttribute("isConfirmed");
        if(isConfirmed != null){
            User user = (User) session.getAttribute("userSession");
            String username = user.getAccount().getUsername();
            String password = "";
            Account account = new Account(username, password);
            model.addAttribute("account", account);
            return "changePassword";
        }else{
            return "redirect:/user/confirm";
        }
    }

    @PostMapping("/changePassword")
    public String processChanging (@Valid Account account,
                                   BindingResult result,
                                   @RequestParam("confirmNewPassword") String confirmNewPassword,
                                   HttpSession session){
        User user = (User) session.getAttribute("userSession");
        if(result.hasErrors()){
            return "changePassword";
        }else{
            if(account.equals(user.getAccount())){
                result.addError(new FieldError("account", "password", "New password must be different from current password"));
            }
            if(!confirmNewPassword.equals(account.getPassword())){
                result.addError(new FieldError("account", "password", "Confirmed password must be identical with new password."));
            }
            if(result.hasErrors()){
                return "changePassword";
            }else{
                user.setAccount(account);
                userService.updateUserInfo(user, session);
                return "changePasswordSuccess";
            }
        }
    }
}
