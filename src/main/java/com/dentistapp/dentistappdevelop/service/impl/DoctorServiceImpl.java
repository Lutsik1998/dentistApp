package com.dentistapp.dentistappdevelop.service.impl;

import com.dentistapp.dentistappdevelop.model.Doctor;
import com.dentistapp.dentistappdevelop.model.Patient;
import com.dentistapp.dentistappdevelop.repository.DoctorRepository;
import com.dentistapp.dentistappdevelop.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public DoctorRepository doctorRepository() {
        return doctorRepository;
    }


    @Override
    public Doctor update(Doctor doctorDetails) {
        Doctor doctor = doctorRepository.findById(doctorDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found for this id: " + doctorDetails.getId()));
        if (doctorDetails.getFirstName() != null && !doctorDetails.getFirstName().equals("")) {
            doctor.setFirstName(doctorDetails.getFirstName());
        }
        if (doctorDetails.getLastName() != null && !doctorDetails.getLastName().equals("")) {

            doctor.setLastName(doctorDetails.getLastName());
        }
        if (doctorDetails.getSecondName() != null && !doctorDetails.getSecondName().equals("")) {
            doctor.setSecondName(doctorDetails.getSecondName());
        }
        if (doctorDetails.getBirthDate() != null && !doctorDetails.getBirthDate().equals("")) {
            doctor.setBirthDate(doctorDetails.getBirthDate());
        }
        if (doctorDetails.getSex() != null && !doctorDetails.getSex().equals("")) {
            doctor.setSex(doctorDetails.getSex());
        }
        if (doctorDetails.getLicence() != null && !doctorDetails.getLicence().equals("")) {
            doctor.setLicence(doctorDetails.getLicence());
        }
        if (doctorDetails.getAddress() != null && !doctorDetails.getAddress().equals("")) {
            doctor.setAddress(doctorDetails.getAddress());
        }
        if (doctorDetails.getPhoneNumber() != null && !doctorDetails.getPhoneNumber().equals("")) {
            doctor.setPhoneNumber(doctorDetails.getPhoneNumber());
        }
        if (doctorDetails.getSpecialization() != null && !doctorDetails.getSpecialization().equals("")) {
            doctor.setSpecialization(doctorDetails.getSpecialization());
        }
        return doctor;
    }

    @Override
    public Doctor save(Doctor doctor) {
        doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));
        doctorRepository.save(doctor);
        return doctor;
    }

    @Override
    public boolean existsById(String id) {
        if (id == null || id.equals("") || id.length() != 24) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong doctor id \"" + id + "\"");
        }
        return doctorRepository.existsById(id);
    }

    @Override
    public Doctor findById(String id) {
        Optional<Doctor> optionalDoctor = doctorRepository.findById(id);
        if (optionalDoctor.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Doctor whit id \"" + id + "\" not found");
        }
        Doctor doctor = optionalDoctor.get();
        doctor.toDTO();
        return doctor;
    }

    @Override
    public List<Doctor> findAll() {
        List<Doctor> doctors = doctorRepository.findAll();
        for (Doctor doctor : doctors) {
            doctor.toDTO();
        }
        return doctors;
    }
}
