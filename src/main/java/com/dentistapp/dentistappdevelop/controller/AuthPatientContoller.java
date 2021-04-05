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
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginDto) {
        return login(loginDto);
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
//        Patient patient = new Patient(
//                signUpPatientRequest.getEmail(),
//                encoder.encode(signUpPatientRequest.getPassword()),
//                signUpPatientRequest.getRole(),
//                signUpPatientRequest.getFirstName(),
//                signUpPatientRequest.getSecondName(),
//                signUpPatientRequest.getLastName(),
//                signUpPatientRequest.getPesel(),
//                signUpPatientRequest.getBirthDate(),
//                signUpPatientRequest.getSex(),
//                signUpPatientRequest.getAddres(),
//                signUpPatientRequest.getPhoneNumber(),
//                signUpPatientRequest.getCardNumber()
//                );

        //Set<String> strRoles = signUpPatientRequest.getRole();
        Set<Roles> roles = new HashSet<>();
//        if (strRoles == null) {
//            Roles userRole = roleRepository.findByName(Roles.PATIENT)
//                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//            roles.add(userRole);
//        } else {
//            strRoles.forEach(role -> {
//                switch (role) {
//                    case "admin":
//                        Roles adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(adminRole);
//
//                        break;
//                    case "mod":
//                        Roles modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(modRole);
//
//                        break;
//                    default:
//                        Roles userRole = roleRepository.findByName(ERole.ROLE_USER)
//                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//                        roles.add(userRole);
//                }
//            });
//        }
        roles.add(Roles.ROLE_PATIENT);
        patient.setRole(Roles.ROLE_PATIENT);
        LoginDto loginDto = new LoginDto(patient.getEmail(),patient.getPassword(), patient.getRole().name());
        patientService.save(patient);
        return login(loginDto);
    }

    private ResponseEntity<?> login(LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

            System.out.println("test");


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
