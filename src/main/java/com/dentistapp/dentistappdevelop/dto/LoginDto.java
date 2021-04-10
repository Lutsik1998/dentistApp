package com.dentistapp.dentistappdevelop.dto;

import com.dentistapp.dentistappdevelop.model.Roles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
public class LoginDto {
    @Id
    @JsonIgnore
    private String id;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;

    private Set<Roles> roles;

    public LoginDto(String id, @NotNull @Email String email, @NotNull String password, Set<Roles> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public LoginDto() {
    }

}
