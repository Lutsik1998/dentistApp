package com.dentistapp.dentistappdevelop.service.impl;

import com.dentistapp.dentistappdevelop.model.Recipe;
import com.dentistapp.dentistappdevelop.model.Review;
import com.dentistapp.dentistappdevelop.model.Visit;
import com.dentistapp.dentistappdevelop.service.DoctorService;
import com.dentistapp.dentistappdevelop.service.RecipeService;
import com.dentistapp.dentistappdevelop.service.ReviewService;
import com.dentistapp.dentistappdevelop.service.VisitService;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    VisitService visitService;
    @Autowired
    DoctorService doctorService;

    @Override
    public Recipe save(String visitId, Recipe recipe) {
        recipe.setId(visitId + new ObjectId().toString());
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(visitId));
        query.fields().include("recipes");
        Update update = new Update();
        update.push("recipes", recipe);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Visit.class, "visit");
        if (updateResult.getModifiedCount() <=0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return recipe;
    }

    @Override
    public Recipe update(String visitId, Recipe recipe) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(visitId)
                .and("recipes._id").is(recipe.getId()));
        query.fields().include("recipes");
        Update update = new Update();
        update.set("recipes.$", recipe);
        Visit visit = mongoTemplate.findAndModify(query, update, Visit.class, "visit");
        if (visit == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return recipe;
    }

    @Override
    public List<Recipe> findAllByDoctorId(String doctorId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("doctorId").is(doctorId));
        query.fields().include("recipes");
        List<Visit> visits = mongoTemplate.find(query, Visit.class, "visit");
        return visits.stream().map(Visit::getRecipes).filter(Objects::nonNull).flatMap(List::stream).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public List<Recipe> findAllByVisitId(String visitId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(visitId));
        query.fields().include("recipes");
        Visit visit = mongoTemplate.findOne(query, Visit.class, "visit");
        if (visit == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return visit.getRecipes();
    }

    @Override
    public Recipe findByRecipeIdAndVisitId(String id, String visitId) {
        List<Recipe> recipeList = findAllByVisitId(visitId);
        for (Recipe rec : recipeList) {
            if (rec.getId().equals(id)) {
                return rec;
            }
        }
        return null;
    }

    @Override
    public void deleteByRecipeIdAndVisitId(String id, String visitId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(visitId)
                .and("recipes._id").is(id));
        query.fields().include("recipes");
        Update update = new Update();
        update.unset("recipes.$");
        Visit visit = mongoTemplate.findAndModify(query, update, Visit.class, "visit");
        if (visit == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
//        for (Recipe rec : visit.getRecipes()) {
//            if (rec.getId().equals(id)) {
//                deleteImage(rec.getFileName());
//                break;
//            }
//        }
    }

    private void saveImage(MultipartFile multipartFile, String fileName) throws IOException {
        if (multipartFile != null && !fileName.equals("")) {
            File targetFile = new File("img_db/" + fileName);
            OutputStream outputStream = new FileOutputStream(targetFile);
            outputStream.write(multipartFile.getBytes());
            outputStream.close();
        }
    }

    private void deleteImage(String fileName) {
        if (!fileName.equals("")) {
            File targetFile = new File("img_db/" + fileName);
            targetFile.delete();
        }
    }

    private Path getImage(String fileName) {
        Path path = Paths.get("img_db/" + fileName);
        return path;
    }

}
