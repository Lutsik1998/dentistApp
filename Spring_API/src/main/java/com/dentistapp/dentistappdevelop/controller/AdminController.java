package com.dentistapp.dentistappdevelop.controller;

import com.dentistapp.dentistappdevelop.dto.LoginDto;
import com.dentistapp.dentistappdevelop.model.*;
import com.dentistapp.dentistappdevelop.security.payload.MessageResponse;
import com.dentistapp.dentistappdevelop.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private DoctorService doctorService;

    @Value("${spring.admin.password}")
    String adminPassword;

    @GetMapping("/initialize")
    public ResponseEntity<?> initAdmin(@RequestParam (value="admin") String password) {

        if(!password.equals(adminPassword)){
            System.out.println("pass");
            return new ResponseEntity<>(null, HttpStatus.NOT_ACCEPTABLE);
        }
        System.out.println("create cor");
        Doctor doctorAdmin = new Doctor();
        doctorAdmin.setFirstName("Admin");
        doctorAdmin.setLastName("Admin");
        doctorAdmin.setSecondName("Admin");
        doctorAdmin.setLicence("Admin");
        doctorAdmin.setEmail("admin@admin");
        doctorAdmin.setPassword("admin");
        doctorAdmin.setPesel(1234);
        doctorAdmin.setSex(Sex.MAN);
        doctorAdmin.setAddress(new Address());
        doctorAdmin.setPhoneNumber(new HashSet<>());
        doctorAdmin.setRoles(new LinkedHashSet<Roles>(){{
            add(Roles.ROLE_DOCTOR);
            add(Roles.ROLE_ADMIN);
        }});
        System.out.println("before cor");
        if (doctorService.doctorRepository().existsByEmail(doctorAdmin.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User with email: " + doctorAdmin.getEmail() +" is already taken!"));
        }
        doctorAdmin = doctorService.save(doctorAdmin);

        return ResponseEntity.ok(doctorAdmin);
    }


}
