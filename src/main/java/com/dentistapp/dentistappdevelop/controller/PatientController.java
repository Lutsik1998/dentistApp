package com.dentistapp.dentistappdevelop.controller;


import com.dentistapp.dentistappdevelop.model.Patient;
import com.dentistapp.dentistappdevelop.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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








}
