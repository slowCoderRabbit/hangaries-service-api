package com.hangaries.controller;

import com.hangaries.model.Recipe;
import com.hangaries.model.RecipeWithName;
import com.hangaries.model.inventory.request.RecipeStatusRequest;
import com.hangaries.service.inventory.supplier.RecipeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class RecipeController {

    private static final Logger logger = LoggerFactory.getLogger(RecipeController.class);

    @Autowired
    private RecipeServiceImpl recipeService;

    @GetMapping("getAllRecipes")
    public ResponseEntity<List<RecipeWithName>> getAllRecipes(@RequestParam("restaurantId") String restaurantId,
                                                              @RequestParam("storeId") String storeId) {
        List<RecipeWithName> recipes = new ArrayList<>();
        logger.info("Getting list of all recipes for restaurantId = [{}], storeId = [{}].", restaurantId, storeId);
        recipes = recipeService.getAllRecipes(restaurantId, storeId);
        logger.info("[{}] recipes found.", recipes.size());
        return new ResponseEntity<List<RecipeWithName>>(recipes, HttpStatus.OK);
    }

    @GetMapping("getAllActiveRecipes")
    public ResponseEntity<List<RecipeWithName>> getAllActiveRecipes(@RequestParam("restaurantId") String restaurantId,
                                                                    @RequestParam("storeId") String storeId) {
        List<RecipeWithName> recipes = new ArrayList<>();
        logger.info("Getting list of all active recipes for restaurantId = [{}], storeId = [{}].", restaurantId, storeId);
        recipes = recipeService.getAllActiveRecipes(restaurantId, storeId);
        logger.info("[{}] active recipes found.", recipes.size());
        return new ResponseEntity<List<RecipeWithName>>(recipes, HttpStatus.OK);
    }

    @PostMapping("saveRecipe")
    public Recipe saveRecipe(@RequestBody Recipe recipe) {
        logger.info("Saving recipe with details = [{}].", recipe);
        return recipeService.saveRecipe(recipe);
    }

    @PostMapping("saveRecipeStatus")
    public Recipe saveRecipeStatus(@RequestBody RecipeStatusRequest request) {
        logger.info("Saving recipe status = [{}] for supplier id = [{}].", request.getItemStatus(), request.getId());
        return recipeService.saveRecipeStatus(request);
    }
}
