package com.dentistapp.dentistappdevelop.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Address {

    @NotNull
    private String country;
    @NotNull
    private String region;
    @NotNull
    private String city;
    @NotNull
    private String postalCode;
    @NotNull
    private String street;
    @NotNull
    private String houseNr;

    private String roomNr;
    private String information;

    public Address() {
    }

    public Address(@NotNull String country, @NotNull String region, @NotNull String city, @NotNull String postalCode, @NotNull String street, @NotNull String houseNr, String roomNr, String information) {
        this.country = country;
        this.region = region;
        this.city = city;
        this.postalCode = postalCode;
        this.street = street;
        this.houseNr = houseNr;
        this.roomNr = roomNr;
        this.information = information;
    }


}
