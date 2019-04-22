package com.google.codeu.data;

public class User {

    private String email;
    private String aboutMe;
    private String username;
    private String numOfDogs;
    private String dogName;
    private String breed;
    private String gender;
    private String description;



    public User(String email, String aboutMe, String username, String numOfDogs, String dogName, String breed, String gender, String description) {
        this.email = email;
        this.aboutMe = aboutMe;
        this.username = username;
        this.numOfDogs = numOfDogs;
        this.dogName = dogName;
        this.breed = breed;
        this.gender = gender;
        this.description = description;

    }

    public String getEmail(){
        return email;
    }

    public String getAboutMe() {
        return aboutMe;
    }
    public String getUsername() {
        return username;
    }
    public String getNumOfDogs() {
        return numOfDogs;
    }
    public String getDogName() {
        return dogName;
    }
    public String getBreed() {
        return breed;
    }
    public String getGender() {
        return gender;
    }

    public String getDescription() {
        return description;
    }

}

