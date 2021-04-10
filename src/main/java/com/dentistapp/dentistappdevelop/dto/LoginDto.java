package com.dentistapp.dentistappdevelop.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

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
    @NotNull
    private String Role;

    public LoginDto(String email, String password, String role) {
        this.email = email;
        this.password = password;
        Role = role;
    }

    public LoginDto() {
    }

}
