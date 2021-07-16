package com.techmaster.crud.controller;

import com.techmaster.crud.repository.PersonDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PersonDao personDao;
    
    @GetMapping
    public String showPeople(Model model){
        model.addAttribute("people", personDao.getAll());
        return "people";
    }
}
