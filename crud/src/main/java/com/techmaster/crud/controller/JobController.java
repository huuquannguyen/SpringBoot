package com.techmaster.crud.controller;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.techmaster.crud.model.Job;
import com.techmaster.crud.repository.JobDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class JobController {
    
    @Autowired
    private JobDao jobDao;

    @GetMapping("/job")
    public String listJob(Model model){
        model.addAttribute("jobs", jobDao.getAll());
        return "listJob";
    }

    @GetMapping("/job/add")
    public String addJob(Model model){
        model.addAttribute("job", new Job());
        return ("jobForm");
    }

    @PostMapping("/job/save")
    public String processAddJob(@Valid Job job, BindingResult bindingResult){
        List<Job> list = new ArrayList<>();
        list = jobDao.getAll();
        for (Job job2 : list) {
            if(job2.getName().equalsIgnoreCase(job.getName())){
                bindingResult.addError(new FieldError("job", "name", "This name is already exist"));
            }
        }
        if(!bindingResult.hasErrors()){
            jobDao.add(job);
            return "redirect:/people/add";
        }
        return "jobForm";
    }

}
