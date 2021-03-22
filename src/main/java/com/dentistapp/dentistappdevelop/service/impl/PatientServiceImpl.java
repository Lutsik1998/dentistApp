package com.dentistapp.dentistappdevelop.service.impl;

import com.dentistapp.dentistappdevelop.repository.PatientRepository;
import com.dentistapp.dentistappdevelop.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;


    @Override
    public PatientRepository patientRepository() {
        return this.patientRepository;
    }
}
