package com.dentistapp.dentistappdevelop.model;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

@Data
public class Jaw extends HashMap<Integer,Tooth> {

    private String id;
    private List<String> fileNames;

    public Jaw() {
        super(32);

    }
}
