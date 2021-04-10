package com.dentistapp.dentistappdevelop.service.impl;

import com.dentistapp.dentistappdevelop.model.Doctor;
import com.dentistapp.dentistappdevelop.repository.DoctorRepository;
import com.dentistapp.dentistappdevelop.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public DoctorRepository doctorRepository() {
        return doctorRepository;
    }

    @Override
    public Doctor update(Doctor doctorDetails) {
        return null;
    }

    @Override
    public Doctor save(Doctor doctor) {
        return null;
    }
}
