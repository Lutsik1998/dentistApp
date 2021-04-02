package com.dentistapp.dentistappdevelop.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class Patient extends User{

    private int cardNumber;

    public Patient(String id, @Email String email, @NotNull String password, @NotNull Roles role, @NotNull String firstName, String secondName, @NotNull String lastName, @NotNull int pesel, LocalDate birthDate, @NotNull Sex sex, Addres addres, PhoneNumber phoneNumber, int cardNumber) {
        super(id, email, password, role, firstName, secondName, lastName, pesel, birthDate, sex, addres, phoneNumber);
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
}
