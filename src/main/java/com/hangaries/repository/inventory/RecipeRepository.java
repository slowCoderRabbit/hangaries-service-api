package com.hangaries.repository.inventory;

import com.hangaries.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query(value = "select * from RECIPE_MASTER where item_status=:status order by id", nativeQuery = true)
    List<Recipe> getAllActiveRecipes(@Param("status") String status);

    @Modifying
    @Transactional
    @Query(value = "update RECIPE_MASTER set item_status=:status, updated_by=:updatedBy,updated_date=:updatedDate where id=:id", nativeQuery = true)
    int saveRecipeStatus(@Param("id") long id, @Param("status") String status, @Param("updatedBy") String updatedBy, @Param("updatedDate") Date updatedDate);

    @Query(value = "select * from RECIPE_MASTER where id=:id", nativeQuery = true)
    Recipe getRecipeById(@Param("id") long id);


}
