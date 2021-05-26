package com.dentistapp.dentistappdevelop.service;

import com.dentistapp.dentistappdevelop.model.Doctor;
import com.dentistapp.dentistappdevelop.repository.DoctorRepository;

import java.util.List;

public interface DoctorService{

    public DoctorRepository doctorRepository();

    public List<Doctor> findAll();
    public Doctor update(Doctor doctorDetails);
    public Doctor save(Doctor doctor);
    public boolean existsById(String id);
    public Doctor findById(String id);
    public boolean updateRatingById(String id, float rating);
    public void updatePassword(String doctorId, String newPassword);
}
