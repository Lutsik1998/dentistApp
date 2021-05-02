package com.dentistapp.dentistappdevelop.service;

import com.dentistapp.dentistappdevelop.model.Doctor;
import com.dentistapp.dentistappdevelop.repository.DoctorRepository;

public interface DoctorService{

    public DoctorRepository doctorRepository();

    public Doctor update(Doctor doctorDetails);
    public Doctor save(Doctor doctor);
    public boolean existsById(String id);
}
