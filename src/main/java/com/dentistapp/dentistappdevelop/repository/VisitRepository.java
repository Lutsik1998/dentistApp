package com.dentistapp.dentistappdevelop.repository;

import com.dentistapp.dentistappdevelop.model.Review;
import com.dentistapp.dentistappdevelop.model.Visit;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

public interface VisitRepository extends MongoRepository<Visit, String> {
    boolean existsById(String id);

    // визиты доктора в промежуток времени
    @Query(value = " {$and : [ {'dateTimeStart': {$gte: ?0 }}, {'dateTimeEnd': {$lte: ?1 }}, {'doctorId': ?2 } ] }", fields = "{dateTimeStart: 1, dateTimeEnd: 2}", sort = "{dateTimeStart: 1}")
    List<Visit> findByDateTimeStartAndDateTimeEndAndDoctorIdSortedByDateTimeStart(LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, String doctorId);

    // проверка на наличие запланированного визита на это время
    @Query(value = " {$and : [  {$or : [ {$and : [ {'dateTimeStart': {$lte: ?0 }}, {'dateTimeEnd': {$gte: ?0 }} ] }, {$and : [ {'dateTimeStart': {$lte: ?1 }}, {'dateTimeEnd': {$gte: ?1 }} ] } ] }, { 'doctorId': ?2 } ] }", exists = true)
    boolean existsByDateTimeStartAndDateTimeEndAndDoctorId(LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, String doctorId);

    @Query(value = "{'doctorId': ?0 }", fields = "{'review': 1 }")
    List<Review> findReviewByDoctorId(String doctorId);
}
