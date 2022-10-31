package com.hangaries.service.inventory.supplier;

import com.hangaries.model.Recipe;
import com.hangaries.model.inventory.request.RecipeStatusRequest;
import com.hangaries.repository.inventory.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.hangaries.constants.HangariesConstants.ACTIVE;

@Service
public class RecipeServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(RecipeServiceImpl.class);

    @Autowired
    RecipeRepository recipeRepository;

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Recipe saveRecipeStatus(RecipeStatusRequest request) {
        int result = recipeRepository.saveRecipeStatus(request.getId(), request.getItemStatus(), request.getUpdatedBy(), new Date());
        logger.info("saveRecipeStatus result = [{}]", result);
        return recipeRepository.getRecipeById(request.getId());
    }

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public List<Recipe> getAllActiveRecipes() {
        return recipeRepository.getAllActiveRecipes(ACTIVE);
    }
}
