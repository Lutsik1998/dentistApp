package com.dentistapp.dentistappdevelop.service;

import com.dentistapp.dentistappdevelop.model.Patient;
import com.dentistapp.dentistappdevelop.repository.PatientRepository;

import java.util.List;

public interface PatientService {

    public PatientRepository patientRepository();

    public Patient update(Patient patientsDetails);
    public Patient save(Patient patient);
    public List<Patient> findAll();
    public boolean existsById(String id);

}
