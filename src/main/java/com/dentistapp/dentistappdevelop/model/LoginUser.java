package com.dentistapp.dentistappdevelop.model;

import org.springframework.data.annotation.Id;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class LoginUser  {

    @Id
    public String id;

    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private Roles role;

    public LoginUser(String id, @Email String email, @NotNull String password, @NotNull Roles role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public LoginUser() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }
}