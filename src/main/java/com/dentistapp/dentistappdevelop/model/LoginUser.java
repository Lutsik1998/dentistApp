package com.dentistapp.dentistappdevelop.model;

import org.springframework.data.annotation.Id;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class LoginUser extends BaseEntity {
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private Set<Roles> roles;

    public LoginUser(String id, @Email String email, @NotNull String password, @NotNull Set<Roles> roles) {
        super(id);
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public LoginUser() {
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

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }
}
