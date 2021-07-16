package com.techmaster.crud.config;

import com.techmaster.crud.repository.PersonDao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepoConfig {
    
    @Bean
    public PersonDao personDao(){
        return new PersonDao("people.csv");
    }
}
