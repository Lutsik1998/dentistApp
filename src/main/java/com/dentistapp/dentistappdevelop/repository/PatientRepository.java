package com.dentistapp.dentistappdevelop.repository;

import com.dentistapp.dentistappdevelop.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface PatientRepository extends MongoRepository<Patient,String> {
    boolean existsByEmail(String email);
    boolean existsById(String id);
    public Patient findByEmail(String email);
    public Patient findByEmailAndPassword(String email,String password);


}
