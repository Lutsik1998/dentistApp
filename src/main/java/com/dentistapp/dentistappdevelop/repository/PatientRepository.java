package com.dentistapp.dentistappdevelop.repository;

import com.dentistapp.dentistappdevelop.model.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface PatientRepository extends MongoRepository<Patient,String> {


}
