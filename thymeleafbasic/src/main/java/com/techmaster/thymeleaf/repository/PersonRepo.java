package com.techmaster.thymeleaf.repository;

import java.util.ArrayList;
import java.util.List;

import com.techmaster.thymeleaf.model.Person;

import org.springframework.stereotype.Repository;

@Repository
public class PersonRepo {
    
    private List<Person> people;
    
    public PersonRepo(){
        people = new ArrayList<>();
        people.add(new Person("Trinh Minh Cuong", "Viet Nam", "1957-11-27", "Male"));
        people.add(new Person("John Terry", "UK", "1972-11-02", "Male"));
        people.add(new Person("Chian Jian", "China", "2001-05-02", "Female"));
        people.add(new Person("Ly Hoang Duc", "Viet Nam", "2001-07-08", "Male"));
        people.add(new Person("Leo Messi", "Argentina", "1987-11-02", "Male"));
        people.add(new Person("Lyan Kuwaii", "Singapore", "2002-01-12", "Male"));
        people.add(new Person("Nguyen Huu Quan", "Viet Nam", "1998-07-07", "Male"));
        people.add(new Person("Nguyen Thi Hau", "Viet Nam", "2001-12-20", "Female"));
    }

    public List<Person> getPeople(){
        return people;
    }
}
