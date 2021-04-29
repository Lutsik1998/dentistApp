package com.dentistapp.dentistappdevelop.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.HashSet;

@Data
public class PhoneNumber {

    @Schema(pattern = "^\\+(?:[0-9] ?){6,14}[0-9]$" )
    HashSet<String> numbers = new HashSet<>();

    public PhoneNumber(HashSet<String> numbers) {
        this.numbers = numbers;
    }

    public PhoneNumber() {
    }
}
