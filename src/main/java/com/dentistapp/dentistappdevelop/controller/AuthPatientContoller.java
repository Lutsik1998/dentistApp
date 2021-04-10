package com.dentistapp.dentistappdevelop.controller;

import com.dentistapp.dentistappdevelop.model.Patient;
import com.dentistapp.dentistappdevelop.model.Roles;
import com.dentistapp.dentistappdevelop.security.jwt.JwtUtils;
import com.dentistapp.dentistappdevelop.security.payload.JwtResponse;
import com.dentistapp.dentistappdevelop.dto.LoginDto;
import com.dentistapp.dentistappdevelop.security.payload.MessageResponse;
import com.dentistapp.dentistappdevelop.service.PatientService;
import com.dentistapp.dentistappdevelop.service.impl.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth/patient")
public class AuthPatientContoller {


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PatientService patientService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;





    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginUser) {
        return login(loginUser);
    }

    @PostMapping("/signup")
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
        return login(loginUser);
    }

    private ResponseEntity<?> login(LoginDto loginUser){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getEmail(),
                roles.toString()));
    }

}
