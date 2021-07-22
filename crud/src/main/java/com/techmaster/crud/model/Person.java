package com.techmaster.crud.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.web.multipart.MultipartFile;


public class Person {
    @JsonIgnore
    private int id;
    @Size(min = 5, max = 30, message = "Name must between 5 and 30")
    private String name;
    @Size(min = 2, max = 30, message = "Job must between 2 and 30")
    private String job;
    private boolean gender;
    @NotBlank(message = "Birthday is required")
    private String birthDay;
    private MultipartFile photo;
    private String photoName;
    
    
    public String getPhotoName() {
        return photoName;
    }
    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }
    public MultipartFile getPhoto() {
        return photo;
    }
    public void setPhoto(MultipartFile photo) {
        this.photo = photo;
    }
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


