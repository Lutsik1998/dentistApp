package com.dentistapp.dentistappdevelop.service.impl;

import com.dentistapp.dentistappdevelop.model.*;
import com.dentistapp.dentistappdevelop.service.JawService;
import com.dentistapp.dentistappdevelop.service.PatientService;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class JawServiceImpl implements JawService {

    @Autowired
    PatientService patientService;

    @Autowired
    MongoTemplate mongoTemplate;


    @Override
    public Jaw update(String patientId, Jaw updatedJaw) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(patientId));
        query.fields().include("jaw");
        Update update = new Update();
        update.set("jaw", updatedJaw);
        Patient patient = mongoTemplate.findAndModify(query, update, Patient.class, "patient");
        if (patient == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return updatedJaw;

    }



    @Override
    public Tooth updateTooth(String patientId, String toothId, Tooth tooth) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(patientId));
        query.fields().include("jaw");
        Update update = new Update();
        update.set("jaw."+ toothId, tooth);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Patient.class, "patient");
        if (updateResult.getModifiedCount() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return tooth;
    }



    @Override
    public Jaw findJawByPatientId(String patientId) {
        return patientService.patientRepository().findById(patientId).get().getJaw();
    }
}
