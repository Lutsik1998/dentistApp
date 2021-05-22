package com.dentistapp.dentistappdevelop.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class UserTest {
    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }


    @Test
    public void createAndDeleteUser() {
        User user = new User();
        Address address = new Address();
        address.setCountry("Poland");
        address.setRegion("Dolnoslaskie");
        address.setCity("Wroclaw");
        address.setPostalCode("123");
        address.setStreet("Wrobleskiego");
        address.setHouseNr("27");
        user.setAddress(address);
        user.setBirthDate(LocalDate.now());
        user.setFirstName("unittest1");
        user.setLastName("unittest1");
        user.setPesel(0000000);
        user.setSex(Sex.MAN);
        user.setPassword("asdadasda");
        user.setEmail("unittest1@unittest1");
        user.setRoles(new LinkedHashSet<>(Arrays.asList(Roles.ROLE_PATIENT)));
        user.setPhoneNumber(new HashSet<>(Arrays.asList(new PhoneNumber("+0775312412"))));
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        for (ConstraintViolation<User> violation : violations) {
           Assertions.assertEquals("",violation.getMessage());
        }
    }
}
