package com.dentistapp.dentistappdevelop.repository;

import com.dentistapp.dentistappdevelop.model.Review;
import com.dentistapp.dentistappdevelop.model.Visit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface VisitRepository extends MongoRepository<Visit,String> {
    boolean existsById(String id);

    @Query(value = "{ 'doctorId' : ? 0 }", fields = "{ 'review' : 1 }")
    List<Review> findReviewByDoctorId(String doctorId);
}
