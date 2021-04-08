package com.ept.conference.controller.dto;

public class UserRegistrationDto {

    private String username;
    private String email;
    private String speciality;
    private String password;

    public UserRegistrationDto() {
    }

    public UserRegistrationDto(String username, String speciality, String email, String password) {
        this.username = username;
        this.email = email;
        this.speciality = speciality;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSpeciality() {
        return this.speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
