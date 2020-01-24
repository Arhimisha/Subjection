package com.arhimisha.intech.registration;

public class RegistrationDetails {
    private String username;
    private String password;
    private String conform;
    private String email;

    public RegistrationDetails(String username, String password, String conform, String email) {
        this.username = username;
        this.password = password;
        this.conform = conform;
        this.email = email;
    }

    public RegistrationDetails() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConform() {
        return conform;
    }

    public void setConform(String conform) {
        this.conform = conform;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
