package com.dentistapp.dentistappdevelop.model;

import lombok.Data;
import org.springframework.data.annotation.Id;


import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class Office extends BaseEntity{


    @NotNull
    private String name;
    @NotNull
    private int NIP;
    @NotNull
    private Address address;
    @NotNull
    private PhoneNumber phoneNumber;

    private Set<String> listDoctorsId;

    public Office() {
    }

    public Office(String id, @NotNull String name, @NotNull int NIP, @NotNull Address address, @NotNull PhoneNumber phoneNumber, Set<String> listDoctorsId) {
        super(id);
        this.name = name;
        this.NIP = NIP;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.listDoctorsId = listDoctorsId;
    }
}
