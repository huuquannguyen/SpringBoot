package com.techmaster.cookie.controller;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.techmaster.cookie.entity.Style;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showHomePage(){
        return "home";
    }

    @GetMapping("/blog")
    public String showBlog(){
        return "blog";
    }

    @GetMapping("/setting")
    public String showSetting(Model model){
        model.addAttribute("style", new Style());
        return "setting";
    }

    @PostMapping("/setting")
    public String processSetting(Style style, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response){
        if(bindingResult.hasErrors()){
            return "setting";
        }
        Cookie bgColor = new Cookie("background-color", style.getBgColor());
        bgColor.setMaxAge(10);
        bgColor.setPath("/");
        bgColor.setSecure(true);
        bgColor.setHttpOnly(false);
        bgColor.setDomain(request.getServerName());
        response.addCookie(bgColor);

        Cookie textColor = new Cookie("color", style.getTextColor());
        textColor.setMaxAge(10);
        textColor.setPath("/");
        textColor.setSecure(true);
        textColor.setHttpOnly(false);
        textColor.setDomain(request.getServerName());
        response.addCookie(textColor);

        Cookie fontSize = new Cookie("font-size", style.getTextSize());
        fontSize.setMaxAge(10);
        fontSize.setPath("/");
        fontSize.setSecure(true);
        fontSize.setHttpOnly(false);
        fontSize.setDomain(request.getServerName());
        response.addCookie(fontSize);

        return "setting";
    }
}
