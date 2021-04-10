package com.dentistapp.dentistappdevelop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
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
    private Roles role;

    public LoginUser(String id, @Email String email, @NotNull String password, @NotNull Roles role) {
        super(id);
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public LoginUser() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
