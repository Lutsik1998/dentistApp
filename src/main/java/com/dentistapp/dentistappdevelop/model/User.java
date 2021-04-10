package com.dentistapp.dentistappdevelop.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class User extends LoginUser{



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
    private Addres addres;
    public PhoneNumber phoneNumber;


    public User(String id, @Email String email, @NotNull String password, @NotNull Roles role, @NotNull String firstName, String secondName, @NotNull String lastName, @NotNull int pesel, LocalDate birthDate, @NotNull Sex sex, Addres addres, PhoneNumber phoneNumber) {
        super(id, email, password, role);
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.birthDate = birthDate;
        this.sex = sex;
        this.addres = addres;
        this.phoneNumber = phoneNumber;
    }

    public User() {
    }

}
