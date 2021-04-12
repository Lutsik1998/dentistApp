package com.dentistapp.dentistappdevelop.controller;

import com.dentistapp.dentistappdevelop.dto.LoginDto;
import com.dentistapp.dentistappdevelop.model.Doctor;
import com.dentistapp.dentistappdevelop.model.Roles;
import com.dentistapp.dentistappdevelop.security.payload.MessageResponse;
import com.dentistapp.dentistappdevelop.service.DoctorService;
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
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;
    @Autowired
    private AuthController authController;

    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody Doctor signUpDoctorRequest, HttpServletResponse httpServletResponse) {
        if (doctorService.doctorRepository().existsByEmail(signUpDoctorRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: User with email: " + signUpDoctorRequest.getEmail() +" is already taken!"));
        }
        // Create new patient's account
        Doctor doctor = signUpDoctorRequest;

        Set<Roles> roles = new HashSet<>();
        roles.add(Roles.ROLE_DOCTOR);
        doctor.setRoles(roles);
        LoginDto loginUser = new LoginDto();
        loginUser.setEmail(doctor.getEmail());
        loginUser.setPassword(doctor.getPassword());
        loginUser.setRoles(doctor.getRoles());
        loginUser.setId(doctor.getId());
        doctorService.save(doctor);
        return authController.login(loginUser);
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')" + " || " + "hasRole('ROLE_ADMIN')")
    @PostMapping(value = "/save")
    public ResponseEntity<Doctor> saveDoctor(@RequestBody Doctor doctor) {

        doctorService.save(doctor);
        return new ResponseEntity(doctor, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')" + " || " + "hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteDoctorByID(@PathVariable(value = "id") String doctorId) throws ResourceNotFoundException {

        try {
            doctorService.doctorRepository().deleteById(doctorId);
        }catch (ResourceNotFoundException e){
            return new ResponseEntity("Patient not found for this id: " + doctorId , HttpStatus.NOT_FOUND);
        }catch (ServerErrorException e1){
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity("Deleted successfully" , HttpStatus.OK);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable(value = "id") String id, @RequestBody Doctor doctorDetails) throws ResourceNotFoundException {

        doctorDetails.setId(id);
        Doctor doctor = doctorService.update(doctorDetails);
        final Doctor updatedDoctor =  doctorService.doctorRepository().save(doctor);
        return ResponseEntity.ok(updatedDoctor);
    }

    //    @PreAuthorize("hasRole('ROLE_DOCTOR')" + " || " + "hasRole('ROLE_ADMIN')")
    @GetMapping (value = "/all")
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.doctorRepository().findAll();
        if(doctors == null){
            return new ResponseEntity( null, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Doctor>>(doctors, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_DOCTOR')" + " || " + "hasRole('ROLE_ADMIN')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Doctor> getDoctorById(@PathVariable String id){
        Optional<Doctor> optionalDoctor = doctorService.doctorRepository().findById(id);
        if (optionalDoctor.isEmpty()) {
            return new ResponseEntity(null, HttpStatus.NO_CONTENT);
        }
        Doctor doctor = optionalDoctor.get();
        return new ResponseEntity<Doctor>(doctor, HttpStatus.OK);
    }
}
