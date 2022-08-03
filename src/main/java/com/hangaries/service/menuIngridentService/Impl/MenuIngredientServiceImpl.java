package com.hangaries.service.menuIngridentService.Impl;

import com.hangaries.model.MenuIngredientList;
import com.hangaries.model.MenuIngrident;
import com.hangaries.model.SubProduct;
import com.hangaries.repository.MenuIngredientListRepository;
import com.hangaries.repository.MenuIngridentRepository;
import com.hangaries.service.menuIngridentService.MenuIngredientService;
import com.hangaries.service.product.impl.ProductServiceImpl;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private ProductServiceImpl productService;


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

        if (menuIngredient.getId() == 0 && StringUtils.isBlank(menuIngredient.getSubProductId())) {
            logger.info("Menu Ingredient ID and Sub Product ID are missing hence treating this request as new Sub Product request!");
            SubProduct newSubProduct = new SubProduct();
            newSubProduct.setIngredientType(menuIngredient.getIngredientType());
            newSubProduct.setCategory(menuIngredient.getCategory());
            newSubProduct.setSize(menuIngredient.getSize());
            logger.info("Calling save new sub product service for [{}]", newSubProduct);
            SubProduct savedSubProduct = productService.saveSubProduct(newSubProduct);
            logger.info("Saved sub product = [{}]", savedSubProduct);
            menuIngredient.setSubProductId(newSubProduct.getSubProductId());

        } else {
            logger.info("Updating existing Menu Ingredient and Sub Product! ");
            SubProduct updatedSubProduct = productService.updatedSubProduct(menuIngredient.getSubProductId(), menuIngredient.getIngredientType(), menuIngredient.getCategory(), menuIngredient.getSize());
            logger.info("Sub Product updated successfully  = [{}]", updatedSubProduct);
            logger.info("Updating menu master for Sub Product Id = [{}]", menuIngredient.getSubProductId());
            int result = menuIngridentRepository.updatedSubProductColumns(menuIngredient.getSubProductId(), menuIngredient.getIngredientType(), menuIngredient.getCategory(), menuIngredient.getSize());
            logger.info("[{}] records update in menu ingredient master for product id = [{}]", result, menuIngredient.getSubProductId());

        }

        return menuIngridentRepository.save(menuIngredient);
    }

    public List<MenuIngredientList> getMenuIngredientList() {
        return menuIngredientListRepository.findAll();

    }
}
