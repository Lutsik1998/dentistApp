package com.dentistapp.dentistappdevelop.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
public class User extends LoginUser {
    @NotNull
    private String firstName;
    private String secondName;
    @NotNull
    private String lastName;
    @NotNull
    private int pesel;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    @NotNull
    private Sex sex;
    @NotNull
    private Address address;
    @Valid
    @NotNull
    public Set< PhoneNumber> phoneNumber;

//    public User(String id, @Email String email, @NotNull String password, @NotNull Set<Roles> roles, @NotNull String firstName, String secondName, @NotNull String lastName, @NotNull int pesel, LocalDate birthDate, @NotNull Sex sex, @NotNull Address address, @NotNull Set<PhoneNumber> phoneNumber) {
//        super(id, email, password, roles);
//        this.firstName = firstName;
//        this.secondName = secondName;
//        this.lastName = lastName;
//        this.pesel = pesel;
//        this.birthDate = birthDate;
//        this.sex = sex;
//        this.address = address;
//        this.phoneNumber = phoneNumber;
//    }

    public User() {
    }

    public void toDTO() {
        super.toDTO();
    }

}
