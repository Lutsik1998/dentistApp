package com.dentistapp.dentistappdevelop.service.impl;

import com.dentistapp.dentistappdevelop.model.Review;
import com.dentistapp.dentistappdevelop.model.Visit;
import com.dentistapp.dentistappdevelop.service.ReviewService;
import com.dentistapp.dentistappdevelop.service.VisitService;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    // TODO:
    // [ ] Update rating in doctor (save method)
    // [ ] Update rating in doctor (update method)
    // [ ] Update rating in doctor (delete method)
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    VisitService visitService;

    @Override
    public Review save(String visitId, Review review) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(visitId));
        query.fields().include("review");
        Update update = new Update();
        update.set("review", review);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Visit.class, "visit");
        if (!updateResult.wasAcknowledged()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return review;
    }

    @Override
    public Review update(String visitId, Review review) {
        return save(visitId, review);
    }

    @Override
    public List<Review> findAllByDoctorId(String doctorId) {
//        Aggregation aggregation = Aggregation.newAggregation(
//                Aggregation.match(Criteria.where("doctorId").is(doctorId)),
//                Aggregation.
//        );
//        AggregationResults<Review> aggregationResults = mongoTemplate.aggregate(aggregation, "visit", Review.class);
//        if (aggregationResults != null && aggregationResults.getMappedResults().size() > 0){
//            reviewList.addAll(aggregationResults.getMappedResults());
//        }
        Query query = new Query();
        query.addCriteria(Criteria.where("doctorId").is(doctorId));
        query.fields().include("review");
        List<Visit> visits = mongoTemplate.find(query, Visit.class, "visit");
        return visits.stream().map(Visit::getReview).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public Review findByVisitId(String visitId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(visitId));
        query.fields().include("review");
        Visit visit = mongoTemplate.findOne(query, Visit.class, "visit");
        return visit.getReview();
    }

    @Override
    public void deleteByVisitId(String visitId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(visitId));
        query.fields().include("review");
        Update update = new Update();
        update.unset("review");
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Visit.class, "visit");
        if (!updateResult.wasAcknowledged()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
