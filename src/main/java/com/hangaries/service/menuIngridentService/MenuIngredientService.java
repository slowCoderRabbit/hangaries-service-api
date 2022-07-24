package com.hangaries.service.menuIngridentService;

import com.hangaries.model.MenuIngrident;

import java.util.List;

public interface MenuIngredientService {

    List<MenuIngrident> getIngredientsByMenuId(String productId, String restaurantId, String storeId) throws Exception;

    List<MenuIngrident> getAllIngredientsByRestoAndStoreId(String restaurantId, String storeId) throws Exception;


    MenuIngrident saveMenuIngredient(MenuIngrident menuIngredient);
}
