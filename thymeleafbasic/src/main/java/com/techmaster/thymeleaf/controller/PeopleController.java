package com.techmaster.thymeleaf.controller;


import com.techmaster.thymeleaf.repository.PersonRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class PeopleController {
    
    @Autowired
    private PersonRepo repo;

    @GetMapping("/ex1")
    public String showListPeople(Model model){
        model.addAttribute("people", repo.getPeople());
        return "ex1";
    }

    @GetMapping("/ex3")
    public String showTwoColumn(Model model){
        model.addAttribute("people", repo.getPeople());
        return "ex3";
    }
}
