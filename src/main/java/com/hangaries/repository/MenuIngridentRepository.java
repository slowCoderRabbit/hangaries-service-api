package com.hangaries.repository;

import com.hangaries.model.MenuIngrident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MenuIngridentRepository extends JpaRepository<MenuIngrident, Long> {
    @Query(value = "select menu.* from hangaries.MENU_INGREDIENT_MASTER menu,hangaries.DISH_TOPPING_MAPPING dish \n " +
            "where menu.restaurant_id = dish.restaurant_id and menu.store_id = dish.store_id and dish.sub_product_id=menu.sub_product_id and dish.product_id=:productId and menu.restaurant_id =:restaurantId and menu.store_id =:storeId", nativeQuery = true)
    List<MenuIngrident> getAllIngredientsByMenuId(@Param("productId") String productId, @Param("restaurantId") String restaurantId, @Param("storeId") String storeId);

    @Query(value = "select menu.* from hangaries.MENU_INGREDIENT_MASTER menu where menu.restaurant_id =:restaurantId and menu.store_id =:storeId", nativeQuery = true)
    List<MenuIngrident> getAllIngredientsByRestoAndStoreId(@Param("restaurantId") String restaurantId, @Param("storeId") String storeId);
}