package com.techmaster.crud.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.techmaster.crud.model.Person;
import com.techmaster.crud.repository.JobDao;
import com.techmaster.crud.repository.PersonDao;
import com.techmaster.crud.request.SearchRequest;
import com.techmaster.crud.service.PeopleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PersonDao personDao;
    @Autowired
    private PeopleService peopleService;
    @Autowired
    private JobDao jobDao;
    
    @GetMapping
    public String showPeople(Model model){
        model.addAttribute("people", personDao.getAll());
        model.addAttribute("search", new SearchRequest());
        return "people";
    }

    @GetMapping(value = "/{id}")
    public String showDetail(@PathVariable("id") int id, Model model){
        Optional<Person> person = personDao.get(id);
        if(person.isPresent()){
            model.addAttribute("person", person.get());
        }
        return "personDetail";
    }

    @GetMapping("/add")
    public String addPerson(Model model){
        model.addAttribute("person", new Person());
        model.addAttribute("jobs", jobDao.getAll());
        return "add";
    }

    @PostMapping(value = "/save", consumes = {"multipart/form-data"})
    public String processAdd(@Valid Person person, BindingResult bindingResult){
        if(person.getPhoto().getOriginalFilename().isEmpty()){
            bindingResult.addError(new FieldError("person", "photo", "Photo is required"));
        }
        if(bindingResult.hasErrors()){
            return "add";
        }
        peopleService.uploadFile(person.getPhoto());
        if(person.getId() > 0){
            personDao.update(person);
        }else{
            personDao.add(person);
        }      
        return "Success";
    }

    @GetMapping(value = "/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model){
        Optional<Person> person = personDao.get(id);
        if(person.isPresent()){
            model.addAttribute("person", person.get());
        }
        return "add";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id){
        personDao.deleteByID(id);
        return "redirect:/people";
    }


    @PostMapping
    public String processSearch(SearchRequest searchRequest, BindingResult bindingResult, Model model){
        if(!bindingResult.hasErrors()){
            List<Person> list = personDao.searchByKeyword(searchRequest.getKeyWord());
            model.addAttribute("searchList", list);
        }
        return "search";
    }


}
