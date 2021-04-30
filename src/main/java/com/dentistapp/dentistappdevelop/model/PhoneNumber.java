package com.dentistapp.dentistappdevelop.model;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.Set;

@Data
public class PhoneNumber {

    @Pattern(regexp="^[+]?((\\d{1}|\\d{2}|\\d{3})([-]|[ ])?[(][0-9]+[)]([-]|[ ])?)?[0-9]+((([-]|[ ])?[0-9]+))+$")
    private String number;
/*
false, ""
false, "+48 504 203 260@@"
false, "+48.504.203.260"
false, "+55(123) 456-78-90-"
false, "+55(123) - 456-78-90"
false, "504.203.260"
false, " "
false, "-"
false, "()"
false, "() + ()"
false, "(21 7777"
false, "+48 (21)"
false, "+"
true , " 1"
true , "1"
true, "555-5555-555"
true, "+48 504 203 260"
true, "+48 (12) 504 203 260"
true, "+48 (12) 504-203-260"
true, "+48(12)504203260"
true, "+4812504203260"
true, "4812504203260
 */
//^([+][0-9]+)?([0-9]{3}|[(]?[0-9]+[)])?([-]?[\s]?[0-9])+$
    public PhoneNumber(@Pattern(regexp = "^[+]?((\\d{1}|\\d{2}|\\d{3})([-]|[ ])?[(][0-9]+[)]([-]|[ ])?)?[0-9]+((([-]|[ ])?[0-9]+))+$") String number) {
        this.number = number;
    }

    public PhoneNumber() {
    }
}
