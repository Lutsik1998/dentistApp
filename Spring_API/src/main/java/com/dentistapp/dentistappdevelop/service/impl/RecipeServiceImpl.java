package com.dentistapp.dentistappdevelop.service.impl;

import com.dentistapp.dentistappdevelop.model.Recipe;
import com.dentistapp.dentistappdevelop.model.Review;
import com.dentistapp.dentistappdevelop.model.Visit;
import com.dentistapp.dentistappdevelop.security.jwt.JwtUtils;
import com.dentistapp.dentistappdevelop.service.*;
import com.mongodb.client.result.UpdateResult;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(RecipeServiceImpl.class);
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    VisitService visitService;
    @Autowired
    DoctorService doctorService;
    @Autowired
    ImageService imageService;

    @Override
    public Recipe save(String visitId, Recipe recipe) {
        recipe.setId(visitId + new ObjectId().toString());
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(visitId));
        query.fields().include("recipes");
        Update update = new Update();
        update.push("recipes", recipe);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Visit.class, "visit");
        if (updateResult.getModifiedCount() <= 0) {
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
            if (rec != null && rec.getId().equals(id)) {
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
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        for (Recipe rec : visit.getRecipes()) {
            if (rec != null && rec.getId().equals(id)) {
                imageService.deleteImage(rec.getFileName());
                break;
            }
        }
    }

    @Override
    public String saveImage(String visitId, String recipeId, MultipartFile multipartFile) {
        String fileName = recipeId + multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().length() - 4, multipartFile.getOriginalFilename().length());
        try {
            imageService.saveImage(multipartFile, fileName);
        } catch (IOException e) {
            logger.error("File isn't save", e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(visitId)
                .and("recipes._id").is(recipeId));
        query.fields().include("recipes");
        Update update = new Update();
        update.set("recipes.$.fileName", fileName);
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Visit.class, "visit");
        if (updateResult.getModifiedCount() <= 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        }
        return fileName;
    }

    @Override
    public Path findImage(String visitId, String recipeId) throws FileNotFoundException {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(visitId)
                .and("recipes._id").is(recipeId));
        query.fields().include("recipes");
        Visit visit = mongoTemplate.findOne(query, Visit.class, "visit");
        if (visit == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        }
        List<Recipe> recipes = visit.getRecipes();
        for (Recipe recipe : recipes) {
            if (recipe != null && recipe.getId().equals(recipeId)) {
                return imageService.getImage(recipe.getFileName());
            }
        }
        return null;
    }

    @Override
    public boolean deleteImage(String visitId, String recipeId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(visitId)
                .and("recipes._id").is(recipeId));
        query.fields().include("recipes");
        Update update = new Update();
        update.set("recipes.$.fileName", "");
        Visit visit = mongoTemplate.findAndModify(query, update, Visit.class, "visit");
        if (visit == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipe not found");
        }
        List<Recipe> recipes = visit.getRecipes();
        for (Recipe recipe : recipes) {
            if (recipe != null && recipe.getId().equals(recipeId)) {
                return imageService.deleteImage(recipe.getFileName());
            }
        }
        return false;
    }

}
