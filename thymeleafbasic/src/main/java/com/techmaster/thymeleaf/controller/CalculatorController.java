package com.techmaster.thymeleaf.controller;

import com.techmaster.thymeleaf.model.Input;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ex2")
public class CalculatorController {
    
    @GetMapping
    public String calculte(Model model){
        model.addAttribute("input", new Input());
        return "ex2";
    }

    @PostMapping
    public String processAdding(@ModelAttribute Input input, Model model, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            int result = input.getNums1() + input.getNums2();
            model.addAttribute("result", result);
        }
        return "ex2";
    }
}
