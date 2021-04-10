package com.dentistapp.dentistappdevelop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
public class LoginUser extends BaseEntity {
    @Email
    @NotNull
    private String email;
    @NotNull
    @JsonIgnore
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
}
