package com.techmaster.crud.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    @Size(min = 2, max = 30, message = "Job must between 2 and 30 characters")
    private String name;
    private int id;

    public Job(String name) {
        this.name = name;
    }
}


