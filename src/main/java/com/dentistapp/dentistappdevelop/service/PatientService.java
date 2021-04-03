package com.dentistapp.dentistappdevelop.service;

import com.dentistapp.dentistappdevelop.model.Patient;
import com.dentistapp.dentistappdevelop.repository.PatientRepository;

public interface PatientService {

    public PatientRepository patientRepository();

    public Patient save(Patient patient);


}
