package com.hangaries.service.menuIngridentService.Impl;

import com.hangaries.model.MenuIngredientList;
import com.hangaries.model.MenuIngrident;
import com.hangaries.repository.MenuIngredientListRepository;
import com.hangaries.repository.MenuIngridentRepository;
import com.hangaries.service.menuIngridentService.MenuIngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuIngredientServiceImpl implements MenuIngredientService {
    private static final Logger logger = LoggerFactory.getLogger(MenuIngredientServiceImpl.class);

    @Autowired
    private MenuIngridentRepository menuIngridentRepository;

    @Autowired
    private MenuIngredientListRepository menuIngredientListRepository;

    public List<MenuIngrident> getIngredientsByMenuId(String productId, String restaurantId, String storeId) throws Exception {

        List<MenuIngrident> menuIngridentList = new ArrayList<MenuIngrident>();
        try {
            logger.info("Get ingredients by productId::");
            menuIngridentList = menuIngridentRepository.getAllIngredientsByMenuId(productId, restaurantId, storeId);

        } catch (Exception ex) {
            logger.error("Error while getting menuingredients::");
            throw new Exception(ex);
        }
        return menuIngridentList;
    }

    @Override
    public List<MenuIngrident> getAllIngredientsByRestoAndStoreId(String restaurantId, String storeId) throws Exception {
        return menuIngridentRepository.getAllIngredientsByRestoAndStoreId(restaurantId, storeId);
    }

    @Override
    public MenuIngrident saveMenuIngredient(MenuIngrident menuIngredient) {
        menuIngridentRepository.save(menuIngredient);
        return menuIngridentRepository.getById(menuIngredient.getId());
    }

    public List<MenuIngredientList> getMenuIngredientList() {
        return menuIngredientListRepository.findAll();

    }
}
