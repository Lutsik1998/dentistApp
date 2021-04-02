package com.dentistapp.dentistappdevelop.service.impl;

import com.dentistapp.dentistappdevelop.model.Patient;
import com.dentistapp.dentistappdevelop.repository.PatientRepository;
import com.dentistapp.dentistappdevelop.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    PatientService patientService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if(!patientService.patientRepository().existsByEmail(email)){
            throw new UsernameNotFoundException("User Not Found with username: " + email);
        }
        Patient patient = patientService.patientRepository().findByEmail(email);

        return UserDetailsImpl.build(patient);
    }

}