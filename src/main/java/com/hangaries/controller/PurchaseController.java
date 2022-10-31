package com.hangaries.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class PurchaseController {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);

//    @Autowired
//    private RecipeServiceImpl recipeService;
//
//    @GetMapping("getAllRecipes")
//    public ResponseEntity<List<Recipe>> getAllRecipes() {
//        List<Recipe> recipes = new ArrayList<>();
//        logger.info("Getting list of all recipes.");
//        recipes = recipeService.getAllRecipes();
//        logger.info("[{}] recipes found.", recipes.size());
//        return new ResponseEntity<List<Recipe>>(recipes, HttpStatus.OK);
//    }
//
//    @GetMapping("getAllActiveRecipes")
//    public ResponseEntity<List<Recipe>> getAllActiveRecipes() {
//        List<Recipe> recipes = new ArrayList<>();
//        logger.info("Getting list of all active recipes.");
//        recipes = recipeService.getAllActiveRecipes();
//        logger.info("[{}] active recipes found.", recipes.size());
//        return new ResponseEntity<List<Recipe>>(recipes, HttpStatus.OK);
//    }
//
//    @PostMapping("savePurchaseOrder")
//    public Recipe savePurchaseOrder(@RequestBody Recipe recipe) {
//        logger.info("Saving recipe with details = [{}].", recipe);
//        return recipeService.saveRecipe(recipe);
//    }
//
//    @PostMapping("saveRecipeStatus")
//    public Recipe saveRecipeStatus(@RequestBody RecipeStatusRequest request) {
//        logger.info("Saving recipe status = [{}] for supplier id = [{}].", request.getItemStatus(), request.getId());
//        return recipeService.saveRecipeStatus(request);
//    }
}
