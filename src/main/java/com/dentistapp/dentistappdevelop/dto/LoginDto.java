package com.dentistapp.dentistappdevelop.dto;

public class LoginDto {
    private String email;
    private String password;
    private String Role;

    public LoginDto(String email, String password, String role) {
        this.email = email;
        this.password = password;
        Role = role;
    }

    public LoginDto() {
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
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
}
