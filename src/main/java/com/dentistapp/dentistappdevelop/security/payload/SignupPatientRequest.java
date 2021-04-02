package com.dentistapp.dentistappdevelop.security.payload;

import com.dentistapp.dentistappdevelop.model.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

public class SignupPatientRequest extends Patient {

    @Email
    private String email;

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    @NotNull
    private String password;

    @NotNull
    private Roles roles;

    public SignupPatientRequest(String id, @Email String email, @NotNull String password, @NotNull Roles role, @NotNull String firstName, String secondName, @NotNull String lastName, @NotNull int pesel, LocalDate birthDate, @NotNull Sex sex, Addres addres, PhoneNumber phoneNumber, int cardNumber) {
        super(id, email, password, role, firstName, secondName, lastName, pesel, birthDate, sex, addres, phoneNumber, cardNumber);
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
