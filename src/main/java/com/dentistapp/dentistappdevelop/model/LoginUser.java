package com.dentistapp.dentistappdevelop.model;

import org.springframework.data.annotation.Id;


import javax.validation.constraints.NotNull;

public class LoginUser  {

    @Id
    public String id;

    @NotNull
    private String login;
    @NotNull
    private String password;
    @NotNull
    private Roles role;


    public LoginUser(String id, @NotNull String login, @NotNull String password, @NotNull Roles role) {
        this.id = id;
        this.login = login;
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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
