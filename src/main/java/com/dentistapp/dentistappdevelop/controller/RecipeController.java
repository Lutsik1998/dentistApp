package com.dentistapp.dentistappdevelop.controller;

import com.dentistapp.dentistappdevelop.model.Recipe;
import com.dentistapp.dentistappdevelop.model.Review;
import com.dentistapp.dentistappdevelop.security.jwt.JwtUtils;
import com.dentistapp.dentistappdevelop.service.RecipeService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/")
public class RecipeController {
    // TODO:
    // [X] CRUD .. tests passed
    // [X] find by: visitid, doctorid .. tests passed
    // [ ] save/load images
    @Autowired
    RecipeService recipeService;

    @PostMapping(value = "visit/{visitId}/recipe")
    public ResponseEntity saveRecipe(@PathVariable(value = "visitId") String visitId,@RequestBody @Valid Recipe recipe){
        try {
            recipe = recipeService.save(visitId, recipe);
            return new ResponseEntity(recipe, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping(value = "visit/{visitId}/recipe/{id}")
    public ResponseEntity findRecipe(@PathVariable(value = "visitId") String visitId,@PathVariable(value = "id") String id){
        try {
            Recipe recipe = recipeService.findByRecipeIdAndVisitId(id, visitId);
            return new ResponseEntity(recipe, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping(value = "visit/{visitId}/recipe")
    public ResponseEntity findRecipeByVisitId(@PathVariable(value = "visitId") String visitId){
        try {
            List<Recipe> recipes = recipeService.findAllByVisitId(visitId);
            return new ResponseEntity(recipes, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping(value = "doctor/{doctorId}/recipe/all")
    public ResponseEntity<List<Recipe>> getAllRecipesByDoctorId(@PathVariable(value = "doctorId") String doctorId) {
        try {
            List<Recipe> visitList = recipeService.findAllByDoctorId(doctorId);
            return new ResponseEntity<List<Recipe>>(visitList, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }
    }

    @PutMapping("visit/{visitId}/recipe/{id}")
    public ResponseEntity<Recipe> updateReview(@PathVariable(value = "visitId") String visitId,@PathVariable(value = "id") String id, @RequestBody @Valid Recipe recipeDetails) {
        try {
            recipeDetails.setId(id);
            Recipe recipe = recipeService.update(visitId, recipeDetails);
            return new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }
    }

    @DeleteMapping(value = "visit/{visitId}/recipe/{id}")
    public ResponseEntity deleteReviewByVisitId(@PathVariable(value = "visitId") String visitId,@PathVariable(value = "id") String id) {
        try {
            recipeService.deleteByRecipeIdAndVisitId(id, visitId);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity("Patient not found for this id: " + visitId, HttpStatus.NOT_FOUND);
        }catch (ResponseStatusException e2) {
            return new ResponseEntity(e2.getMessage(), e2.getStatus());
        }
        return new ResponseEntity("Deleted successfully", HttpStatus.OK);

    }

}
