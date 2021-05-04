package com.dentistapp.dentistappdevelop.service.impl;

import com.dentistapp.dentistappdevelop.model.LoginUser;
import com.dentistapp.dentistappdevelop.model.Roles;
import com.dentistapp.dentistappdevelop.service.DoctorService;
import com.dentistapp.dentistappdevelop.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    PatientService patientService;
    @Autowired
    DoctorService doctorService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Set<Roles> rolesSet = new HashSet<>();
        LoginUser user = null;
        LoginUser doctor = null;
        if (patientService.patientRepository().existsByEmail(email)) {
            rolesSet.add(Roles.ROLE_PATIENT);
            user = patientService.patientRepository().findByEmail(email);
        }
        if (doctorService.doctorRepository().existsByEmail(email)) {
            rolesSet.add(Roles.ROLE_DOCTOR);
            if (user == null) {
                user = doctorService.doctorRepository().findByEmail(email);
            }
        }
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found with email: " + email);
        }
        user.setRoles(rolesSet);

        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        return userDetails;
    }

}