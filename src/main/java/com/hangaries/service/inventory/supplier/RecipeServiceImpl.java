package com.hangaries.service.inventory.supplier;

import com.hangaries.model.Recipe;
import com.hangaries.model.RecipeWithName;
import com.hangaries.model.inventory.request.RecipeStatusRequest;
import com.hangaries.repository.inventory.RecipeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.hangaries.constants.QueryStringConstants.ACTIVE_RECIPES_WITH_NAME;
import static com.hangaries.constants.QueryStringConstants.RECIPES_WITH_NAME;

@Service
public class RecipeServiceImpl {


    private static final Logger logger = LoggerFactory.getLogger(RecipeServiceImpl.class);

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public Recipe saveRecipeStatus(RecipeStatusRequest request) {
        int result = recipeRepository.saveRecipeStatus(request.getId(), request.getItemStatus(), request.getUpdatedBy(), new Date());
        logger.info("saveRecipeStatus result = [{}]", result);
        return recipeRepository.getRecipeById(request.getId());
    }

    public List<RecipeWithName> getAllRecipes() {
        return jdbcTemplate.query(RECIPES_WITH_NAME, BeanPropertyRowMapper.newInstance(RecipeWithName.class));
    }

    public List<RecipeWithName> getAllActiveRecipes() {
        return jdbcTemplate.query(ACTIVE_RECIPES_WITH_NAME, BeanPropertyRowMapper.newInstance(RecipeWithName.class));
    }
}
