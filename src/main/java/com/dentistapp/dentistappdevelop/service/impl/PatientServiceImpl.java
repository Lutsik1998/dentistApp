package com.dentistapp.dentistappdevelop.service.impl;

import com.dentistapp.dentistappdevelop.model.Patient;
import com.dentistapp.dentistappdevelop.model.Review;
import com.dentistapp.dentistappdevelop.model.Visit;
import com.dentistapp.dentistappdevelop.repository.PatientRepository;
import com.dentistapp.dentistappdevelop.service.PatientService;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    PatientRepository patientRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public PatientRepository patientRepository() {
        return this.patientRepository;
    }


    @Override
    public void updatePassword(String patientId, String newPassword) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(patientId));
        Update update = new Update();
        update.set("password", passwordEncoder.encode(newPassword));
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Patient.class, "patient");
        if (updateResult.getModifiedCount() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
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
        if (patientsDetails.getAddress() != null && !patientsDetails.getAddress().equals("")) {
            patient.setAddress(patientsDetails.getAddress());
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
        patient.toDTO();
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

    @Override
    public boolean existsById(String id) {
        if (id == null || id.equals("") || id.length() != 24) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong patient id \"" + id + "\"");
        }
        return patientRepository.existsById(id);
    }
}
