package com.dentistapp.dentistappdevelop.repository;

import com.dentistapp.dentistappdevelop.model.Office;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OfficeRepository extends MongoRepository<Office,String> {
    boolean existsOfficeById(String id);
    boolean existsByNIP(int nip);
}
