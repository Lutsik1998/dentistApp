package com.dentistapp.dentistappdevelop.model;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class Doctor extends User {

    @NotNull
    private String licence;
    private List<String> specialization;


}
