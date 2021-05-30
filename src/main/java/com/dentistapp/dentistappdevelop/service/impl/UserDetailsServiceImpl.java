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
import java.util.LinkedHashSet;
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
        LinkedHashSet<Roles> rolesSet = new LinkedHashSet<>();
        LinkedHashSet<String> stringSetId = new LinkedHashSet<>();
        LoginUser user = null;
        if (doctorService.doctorRepository().existsByEmail(email)) {
            rolesSet.add(Roles.ROLE_DOCTOR);
            user = doctorService.doctorRepository().findByEmail(email);
            if (user.getRoles().contains(Roles.ROLE_ADMIN)){
                rolesSet.add(Roles.ROLE_ADMIN);
            }
            stringSetId.add(user.getId());
        }
        if (patientService.patientRepository().existsByEmail(email)) {
            rolesSet.add(Roles.ROLE_PATIENT);
            user = patientService.patientRepository().findByEmail(email);
            stringSetId.add(user.getId());
        }
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found with email: " + email);
        }
        user.setRoles(rolesSet);
        user.setId(stringSetId.toString());
        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        return userDetails;
    }

}