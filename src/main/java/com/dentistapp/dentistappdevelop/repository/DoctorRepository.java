package com.dentistapp.dentistappdevelop.repository;

import com.dentistapp.dentistappdevelop.model.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DoctorRepository extends MongoRepository<Doctor, String> {
    Boolean existsByEmail(String email);
    boolean existsById(String id);

    public Doctor findByEmail(String email);

    public Doctor findByEmailAndPassword(String email, String password);

}
