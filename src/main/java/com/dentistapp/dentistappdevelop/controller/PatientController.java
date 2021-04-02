package com.dentistapp.dentistappdevelop.controller;


import com.dentistapp.dentistappdevelop.model.Patient;
import com.dentistapp.dentistappdevelop.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    PatientService patientService;



    @PostMapping(value = "/save")
    public ResponseEntity<?> savePatient(@RequestBody Patient patient) {

        patientService.patientRepository().save(patient);
        return new ResponseEntity(patient, HttpStatus.CREATED);
    }



    @GetMapping (value = "/getAllPatients")
    public ResponseEntity<List<Patient>> getAllUsers() {
        List<Patient> patients = patientService.patientRepository().findAll();
        if(patients == null){
            return new ResponseEntity( null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Patient>>(patients, HttpStatus.OK);
    }










}
