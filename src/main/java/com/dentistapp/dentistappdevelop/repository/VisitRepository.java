package com.dentistapp.dentistappdevelop.repository;

import com.dentistapp.dentistappdevelop.model.Visit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VisitRepository extends MongoRepository<Visit,String> {
    boolean existsById(String id);
}
