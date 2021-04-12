package com.dentistapp.dentistappdevelop.controller;

import com.dentistapp.dentistappdevelop.model.Patient;
import com.dentistapp.dentistappdevelop.model.Roles;
import com.dentistapp.dentistappdevelop.service.DoctorService;
import com.dentistapp.dentistappdevelop.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    PatientService patientService;
    @Autowired
    private DoctorService doctorService;

//    @PreAuthorize("has('ROLE_DOCTOR')")
    @GetMapping(value = "/{email}/roles")
    public ResponseEntity<List<Roles>> fetUserRoles(@PathVariable String email) {
        System.out.println("---------------"+email+"---------------");
        List<Roles> roles = new ArrayList<>();
        if (patientService.patientRepository().existsByEmail(email)){
            roles.add(Roles.ROLE_PATIENT);
        }
        if (doctorService.doctorRepository().existsByEmail(email)){
            roles.add(Roles.ROLE_PATIENT);
        }
        if(roles.size() == 0){
            return new ResponseEntity( null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Roles>>(roles, HttpStatus.OK);
    }

}
