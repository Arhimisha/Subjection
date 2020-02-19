package com.arhimisha.intech.registration;

public class RegistrationDetails {
    private String username;
    private String password;
    private String confirm;
    private String firstName;
    private String lastName;
    private String email;

    public RegistrationDetails(String username, String password, String conform, String firstName, String lastName, String email) {
        this.username = username;
        this.password = password;
        this.confirm = conform;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String conform) {
        this.confirm = conform;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
