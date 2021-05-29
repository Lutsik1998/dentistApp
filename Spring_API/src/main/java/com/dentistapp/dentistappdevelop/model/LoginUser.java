package com.dentistapp.dentistappdevelop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
public class LoginUser extends BaseEntity {
    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private LinkedHashSet<Roles> roles;

    public LoginUser(String id, @Email String email, @NotNull String password, @NotNull LinkedHashSet<Roles> roles) {
        super(id);
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public LoginUser() {
    }

    public void toDTO(){
        this.setPassword(null);
    }
}
