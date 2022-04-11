package com.hangaries.repository;

import com.hangaries.model.Menu;
import com.hangaries.model.MenuIngrident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface MenuIngridentRepository extends JpaRepository<MenuIngrident, Long> {
    @Query(value = "select menu.* from hangaries.MENU_INGREDIENT_MASTER menu,hangaries.DISH_TOPPING_MAPPING dish \n" +
            "where dish.sub_product_id=menu.sub_product_id and dish.product_id=:productId", nativeQuery = true)
    List<MenuIngrident> getAllIngredientsByMenuId(@Param("productId") String productId) throws Exception;
}