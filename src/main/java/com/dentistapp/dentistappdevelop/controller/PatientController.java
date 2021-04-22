package com.dentistapp.dentistappdevelop.controller;


import com.dentistapp.dentistappdevelop.dto.LoginDto;
import com.dentistapp.dentistappdevelop.model.Patient;
import com.dentistapp.dentistappdevelop.model.Roles;
import com.dentistapp.dentistappdevelop.security.payload.MessageResponse;
import com.dentistapp.dentistappdevelop.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerErrorException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    PatientService patientService;
    @Autowired
    private AuthController authController;


    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody Patient signUpPatientRequest, HttpServletResponse httpServletResponse) {
        if (patientService.patientRepository().existsByEmail(signUpPatientRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User with email: " + signUpPatientRequest.getEmail() +" is already taken!"));
        }
        // Create new patient's account
        Patient patient = signUpPatientRequest;

        Set<Roles> roles = new HashSet<>();
        roles.add(Roles.ROLE_PATIENT);
        patient.setRoles(roles);
        LoginDto loginUser = new LoginDto();
        loginUser.setEmail(patient.getEmail());
        loginUser.setPassword(patient.getPassword());
        loginUser.setRoles(patient.getRoles());
        loginUser.setId(patient.getId());
        patientService.save(patient);
        return authController.login(loginUser);
    }

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
        patient.toDTO();
        return ResponseEntity.ok(updatedPatient);
    }


//    @PreAuthorize("hasRole('ROLE_DOCTOR')" + " || " + "hasRole('ROLE_ADMIN')")
    @GetMapping (value = "/all")
    public ResponseEntity<List<Patient>> getAllUsers() {
        List<Patient> patients = patientService.findAll();
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
        patient.toDTO();
        return new ResponseEntity<Patient>(patient, HttpStatus.OK);
    }

}
