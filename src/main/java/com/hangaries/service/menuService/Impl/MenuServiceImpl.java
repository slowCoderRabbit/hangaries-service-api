package com.hangaries.service.menuService.Impl;

import com.hangaries.model.Menu;
import com.hangaries.model.Product;
import com.hangaries.repository.MenuRepository;
import com.hangaries.service.menuService.MenuService;
import com.hangaries.service.product.impl.ProductServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {
    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ProductServiceImpl productService;

    /**
     * Get all menuItems
     *
     * @return
     * @throws Exception
     */
    public List<Menu> getAllMenuItems() throws Exception {
        logger.debug("Inside menu service::");
        List<Menu> menuList = null;
        try {
            menuList = menuRepository.getAllMenuItems();
        } catch (Exception ex) {
            logger.error("Error while getting menuitems::" + ex.getMessage());
            throw new Exception(ex);
        }
        return menuList;
    }

    public List<Menu> getMenuItemsByRestroAndStore(String restaurantId, String storeId) throws Exception {
        logger.debug("Inside menu service::");
        List<Menu> menuList = null;
        try {
            menuList = menuRepository.getMenuItemsByRestroAndStore(restaurantId, storeId);
        } catch (Exception ex) {
            logger.error("Error while getting menuitems::" + ex.getMessage());
            throw new Exception(ex);
        }
        return menuList;
    }

    /**
     * Get all sections
     *
     * @param restaurantId
     * @param storeId
     * @return
     * @throws Exception
     */
    public List<String> getAllSections(String restaurantId, String storeId) throws Exception {
        logger.debug("Get sections for {},{}", restaurantId, storeId);
        List<String> sectionList = null;
        try {
            sectionList = menuRepository.getAllSections(restaurantId, storeId);
        } catch (Exception ex) {
            logger.error("Error while getting all sections::");
            throw new Exception(ex);
        }
        return sectionList;
    }

    /**
     * Get dishses by section
     *
     * @param s
     * @param restaurantId
     * @param section
     * @return
     * @throws Exception
     */
    public List<String> getDishesBySection(String section, String restaurantId, String storeId) throws Exception {
        logger.debug("Get all dishes for {}, {}, {}.", section, restaurantId, storeId);
        List<String> dishList = null;
        try {
            dishList = menuRepository.getDishesBySection(section, restaurantId, storeId);

        } catch (Exception ex) {
            logger.error("Error while getting dishes::");
            throw new Exception(ex);
        }
        return dishList;
    }

    @Override
    public Map<String, List<String>> getAllSectionsWithDishes(String restaurantId, String storeId) {
        Map<String, List<String>> sectionWithDishes = null;
        List<Object[]> result = menuRepository.getAllSectionsWithDishes(restaurantId, storeId);

        if (null != result) {
            logger.info("getAllSectionsWithDishes result from DB = {}", result.size());
            sectionWithDishes = consolidateResponseToSectionMap(result);
            logger.info("getAllSectionsWithDishes post transformation to response = {}", sectionWithDishes.size());
        }
        return sectionWithDishes;
    }

    @Override
    public Menu saveMenuItem(Menu menu) {

        if (menu.getId() == 0 && StringUtils.isBlank(menu.getProductId())) {
            logger.info("Menu ID and Product ID are missing hence treating this request as new Product request!");
            Product newProduct = new Product();
            newProduct.setSection(menu.getSection());
            newProduct.setDish(menu.getDish());
            newProduct.setDishCategory(menu.getDishCategory());
            newProduct.setDishType(menu.getDishType());
            newProduct.setProductSize(menu.getProductSize());
            logger.info("Calling save new product service for [{}]", newProduct);
            Product savedProduct = productService.saveProduct(newProduct);
            logger.info("Saved product = [{}]", savedProduct);
            menu.setProductId(savedProduct.getProductId());

        } else {
            logger.info("Updating existing Menu and Product! ");
            Product product = new Product();
            product.setProductId(menu.getProductId());
            product.setSection(menu.getSection());
            product.setDish(menu.getDish());
            product.setDishCategory(menu.getDishCategory());
            product.setDishType(menu.getDishType());
            product.setProductSize(menu.getProductSize());
            Product updatedProduct = productService.updatedProduct(product);
            logger.info("Product updated successfully  = [{}]", updatedProduct);

        }

        return menuRepository.save(menu);
    }

    Map<String, List<String>> consolidateResponseToSectionMap(List<Object[]> results) {
        Map<String, List<String>> sectionMap = new LinkedHashMap<>();
        for (Object[] result : results) {
            String section = result[0].toString();
            String dish = result[1].toString();
            List<String> exitingList = sectionMap.get(section);
            if (exitingList == null) {
                List<String> newList = new ArrayList<>();
                newList.add(dish);
                sectionMap.put(section, newList);
            } else {
                exitingList.add(dish);
                sectionMap.put(section, exitingList);
            }
        }
        return sectionMap;
    }


}