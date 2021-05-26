package com.dentistapp.dentistappdevelop.service;


import com.dentistapp.dentistappdevelop.model.Recipe;
import com.dentistapp.dentistappdevelop.model.Review;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface RecipeService {

    public Recipe save(String visitId, Recipe recipe);
    public Recipe update(String visitId, Recipe recipe);
    public List<Recipe> findAllByDoctorId(String doctorId);
    public List<Recipe> findAllByVisitId(String visitId);
    public Recipe findByRecipeIdAndVisitId(String id, String visitId);
    public void deleteByRecipeIdAndVisitId(String id, String visitId);
    public String saveImage(String visitId, String recipeId, MultipartFile multipartFile);
    public Path findImage(String visitId, String recipeId) throws FileNotFoundException;
    public boolean deleteImage(String visitId, String recipeId);

}
