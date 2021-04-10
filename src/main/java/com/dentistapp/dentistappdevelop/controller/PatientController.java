package com.dentistapp.dentistappdevelop.controller;


import com.dentistapp.dentistappdevelop.model.Patient;
import com.dentistapp.dentistappdevelop.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerErrorException;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    PatientService patientService;


    @PreAuthorize("hasRole('ROLE_DOCTOR')" + " || " + "hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/save")
    public ResponseEntity<?> savePatient(@RequestBody Patient patient) {

        patientService.save(patient);
        return new ResponseEntity(patient, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')" + " || " + "hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deletePatientByID(@PathVariable(value = "id") String patientId) throws ResourceNotFoundException {

        try {
            patientService.patientRepository().deleteById(patientId);
        }catch (ResourceNotFoundException e){
           return new ResponseEntity("Patient not found for this id: " + patientId , HttpStatus.NOT_FOUND);
        }catch (ServerErrorException e1){
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity("Deleted successfully" , HttpStatus.OK);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable(value = "id") String patientId, @RequestBody Patient patientDetails) throws ResourceNotFoundException {

        patientDetails.setId(patientId);
        Patient patient = patientService.update(patientDetails);
        final Patient updatedPatient =  patientService.patientRepository().save(patient);
        return ResponseEntity.ok(updatedPatient);
    }


//    @PreAuthorize("hasRole('ROLE_DOCTOR')" + " || " + "hasRole('ROLE_ADMIN')")
    @GetMapping (value = "/all")
    public ResponseEntity<List<Patient>> getAllUsers() {
        List<Patient> patients = patientService.patientRepository().findAll();
        if(patients == null){
            return new ResponseEntity( null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Patient>>(patients, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')" + " || " + "hasRole('ROLE_ADMIN')" + " || " + "hasRole('ROLE_PATIENT')")
    @GetMapping(value = "/{patientId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Patient> getPatientById(@PathVariable String patientId){
        Optional<Patient> optionalPatient = patientService.patientRepository().findById(patientId);
        if (optionalPatient.isEmpty()) {
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
        }
        Patient patient = optionalPatient.get();
        return new ResponseEntity<Patient>(patient, HttpStatus.OK);
    }

}
