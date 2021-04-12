package com.dentistapp.dentistappdevelop.service.impl;

import com.dentistapp.dentistappdevelop.model.Patient;
import com.dentistapp.dentistappdevelop.repository.PatientRepository;
import com.dentistapp.dentistappdevelop.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @Override
    public PatientRepository patientRepository() {
        return this.patientRepository;
    }

    @Override
    public Patient update(Patient patientsDetails) {
        Patient patient = patientRepository.findById(patientsDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id: " + patientsDetails.getId()));
        if (patientsDetails.getFirstName() != null && !patientsDetails.getFirstName().equals("")) {
            patient.setFirstName(patientsDetails.getFirstName());
        }
        if (patientsDetails.getLastName() != null && !patientsDetails.getLastName().equals("")) {

            patient.setLastName(patientsDetails.getLastName());
        }
        if (patientsDetails.getSecondName() != null && !patientsDetails.getSecondName().equals("")) {
            patient.setSecondName(patientsDetails.getSecondName());
        }
        if (patientsDetails.getBirthDate() != null && !patientsDetails.getBirthDate().equals("")) {
            patient.setBirthDate(patientsDetails.getBirthDate());
        }
        if (patientsDetails.getSex() != null && !patientsDetails.getSex().equals("")) {
            patient.setSex(patientsDetails.getSex());
        }
        if (patientsDetails.getCardNumber() != 0) {
            patient.setCardNumber(patientsDetails.getCardNumber());
        }
        if (patientsDetails.getAddres() != null && !patientsDetails.getAddres().equals("")) {
            patient.setAddres(patientsDetails.getAddres());
        }
        if (patientsDetails.getPhoneNumber() != null && !patientsDetails.getPhoneNumber().equals("")) {
            patient.setPhoneNumber(patientsDetails.getPhoneNumber());
        }
        return patient;
    }

    @Override
    public Patient save(Patient patient) {
        patient.setPassword(passwordEncoder.encode(patient.getPassword()));
        patientRepository.save(patient);
        return patient;
    }

    @Override
    public List<Patient> findAll() {
        List<Patient> patients = patientRepository.findAll();
        for (Patient patient : patients) {
            patient.toDTO();
        }
        return patients;
    }
}
