package com.dentistapp.dentistappdevelop.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;

@Data
public class Patient extends User{

    private int cardNumber;

    public Patient(String id, @Email String email, @NotNull String password, @NotNull Set<Roles> roles, @NotNull String firstName, String secondName, @NotNull String lastName, @NotNull int pesel, LocalDate birthDate, @NotNull Sex sex, Address address, PhoneNumber phoneNumber, int cardNumber) {
        super(id, email, password, roles, firstName, secondName, lastName, pesel, birthDate, sex, address, phoneNumber);
        this.cardNumber = cardNumber;
    }

    public Patient() {
    }



    public int getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(int cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void toDTO(){
        super.toDTO();
    }

}
