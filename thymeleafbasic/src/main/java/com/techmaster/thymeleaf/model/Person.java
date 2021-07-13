package com.techmaster.thymeleaf.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Person {
    private String name;
    private String nationality;
    private String birthdate;
    private String gender;
}


