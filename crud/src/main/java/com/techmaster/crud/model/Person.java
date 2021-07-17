package com.techmaster.crud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class Person {
    @JsonIgnore
    int id;
    
    String name;
    String job;
    boolean gender;
    String birthDay;
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getJob() {
        return job;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public boolean isGender() {
        return gender;
    }
    public void setGender(boolean gender) {
        this.gender = gender;
    }
    public String getBirthDay() {
        return birthDay;
    }
    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }
    
    public boolean matchByKeyword(String keyword){
        String keyGender;
        if(gender){
            keyGender = "female";
        }else{
            keyGender = "male";
        } 
        return (name.toLowerCase().contains(keyword.toLowerCase()) || 
                job.toLowerCase().contains(keyword.toLowerCase()) ||
                keyGender.contains(keyword.toLowerCase()) || 
                birthDay.contains(keyword));
    }
}


