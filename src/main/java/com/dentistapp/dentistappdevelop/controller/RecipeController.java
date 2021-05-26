package com.dentistapp.dentistappdevelop.controller;

import com.dentistapp.dentistappdevelop.model.Recipe;
import com.dentistapp.dentistappdevelop.model.Review;
import com.dentistapp.dentistappdevelop.security.jwt.JwtUtils;
import com.dentistapp.dentistappdevelop.service.ImageService;
import com.dentistapp.dentistappdevelop.service.RecipeService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
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
    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);
    @Autowired
    RecipeService recipeService;
    @Autowired
    ImageService imageService;
    @Autowired
    JwtUtils jwtUtils;

    @PostMapping(value = "visit/{visitId}/recipe")
    public ResponseEntity saveRecipe(@PathVariable(value = "visitId") String visitId,@RequestBody @Valid Recipe recipe){
        try {
            recipe = recipeService.save(visitId, recipe);
            if (recipe == null){
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(recipe, HttpStatus.CREATED);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping(value = "visit/{visitId}/recipe/{id}")
    public ResponseEntity findRecipe(@PathVariable(value = "visitId") String visitId,@PathVariable(value = "id") String id){
        try {
            Recipe recipe = recipeService.findByRecipeIdAndVisitId(id, visitId);
            if (recipe == null){
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(recipe, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping(value = "visit/{visitId}/recipe")
    public ResponseEntity findRecipeByVisitId(@PathVariable(value = "visitId") String visitId){
        try {
            List<Recipe> recipes = recipeService.findAllByVisitId(visitId);
            if (recipes == null){
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity(recipes, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping(value = "doctor/{doctorId}/recipe/all")
    public ResponseEntity<List<Recipe>> getAllRecipesByDoctorId(@PathVariable(value = "doctorId") String doctorId) {
        try {
            List<Recipe> recipes = recipeService.findAllByDoctorId(doctorId);
            if (recipes == null){
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Recipe>>(recipes, HttpStatus.OK);
        } catch (ResponseStatusException e) {
            return new ResponseEntity(e.getMessage(), e.getStatus());
        }
    }

    @PutMapping("visit/{visitId}/recipe/{id}")
    public ResponseEntity<Recipe> updateReview(@PathVariable(value = "visitId") String visitId,@PathVariable(value = "id") String id, @RequestBody @Valid Recipe recipeDetails) {
        try {
            recipeDetails.setId(id);
            Recipe recipe = recipeService.update(visitId, recipeDetails);
            if (recipe == null){
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
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

    @PostMapping(value = "visit/{visitId}/recipe/{recipeId}/image")
    public ResponseEntity<String> saveImage(@PathVariable(value = "visitId") String visitId,@PathVariable(value = "recipeId") String recipeId, @RequestParam(value = "file") MultipartFile multipartFile){
//    public ResponseEntity<String> saveImage(@RequestParam(value = "file") MultipartFile multipartFile, @RequestParam(value = "token") String token) throws IOException {
//        if (!jwtUtils.validateJwtToken(token)){
//            return new ResponseEntity("401 - Unauthorized".getBytes(), HttpStatus.UNAUTHORIZED);
//        }
        String fileName = recipeService.saveImage(visitId,recipeId,multipartFile);
        if (fileName == null || fileName.equals("")){
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/image/download/")
                .path(fileName)
                .toUriString();
        return new ResponseEntity<>(fileDownloadUri, HttpStatus.OK);
    }

    @GetMapping(value = "visit/{visitId}/recipe/{recipeId}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity findImage(@PathVariable(value = "visitId") String visitId,@PathVariable(value = "recipeId") String recipeId){
        Resource resource = null;
        try {
            Path path = recipeService.findImage(visitId,recipeId);
            resource = new UrlResource(path.toUri());
        }catch (FileNotFoundException e){
            logger.warn(e.getMessage());
            return new ResponseEntity("File not found", HttpStatus.NOT_FOUND);
        }catch (MalformedURLException e) {
            logger.error(e.getMessage());
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setCacheControl(CacheControl.noCache().getHeaderValue());
        return new ResponseEntity(resource, headers, HttpStatus.OK);
    }

    @DeleteMapping(value = "visit/{visitId}/recipe/{recipeId}/image")
    public ResponseEntity deleteImage(@PathVariable(value = "visitId") String visitId,@PathVariable(value = "recipeId") String recipeId){
        if (recipeService.deleteImage(visitId,recipeId)) {
            return new ResponseEntity("deleted successfully", HttpStatus.OK);
        }
            return new ResponseEntity("Image not found",HttpStatus.NOT_FOUND);
    }

}
