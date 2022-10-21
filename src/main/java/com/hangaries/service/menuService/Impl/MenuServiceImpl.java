package com.hangaries.service.menuService.Impl;

import com.hangaries.model.*;
import com.hangaries.repository.DishRepository;
import com.hangaries.repository.DishToppingRepository;
import com.hangaries.repository.MenuRepository;
import com.hangaries.repository.SectionRepository;
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

import static com.hangaries.constants.HangariesConstants.DELETED;

@Service
public class MenuServiceImpl implements MenuService {
    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private DishToppingRepository dishToppingRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private DishRepository dishRepository;

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
     * @param
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
            Product updatedProduct = productService.updatedProduct(menu.getProductId(), menu.getSection(), menu.getDish(), menu.getDishCategory(), menu.getDishType(), menu.getProductSize());
            logger.info("Product updated successfully  = [{}]", updatedProduct);
            logger.info("Updating menu master for Product Id = [{}]", menu.getProductId());
            int result = menuRepository.updatedProductColumns(menu.getProductId(), menu.getSection(), menu.getDish(), menu.getDishCategory(), menu.getDishType(), menu.getProductSize());
            logger.info("[{}] records update in menu master for product id = [{}]", result, menu.getProductId());

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


    public DishToppingMapping mapDishToTopping(DishToppingMapping dishToppingMapping) {
        return dishToppingRepository.save(dishToppingMapping);

    }

    public String deleteDishToTopping(DishToppingMapping dishToppingMapping) {
        dishToppingRepository.deleteById(dishToppingMapping.getId());
        return DELETED;
    }

    public List<DishToppingMapping> getDishToppingsByRestoAndStore(String restaurantId, String storeId) {
        return dishToppingRepository.getDishToppingsByRestoAndStore(restaurantId, storeId);
    }

    public List<DishToppingMapping> getAllDishToppingMapping() {

        return dishToppingRepository.findAll();
    }

    public List<Section> getAllSectionsFromMaster() {

        return sectionRepository.findAll();

    }

    public List<Dish> getAllDishesFromMaster() {

        return dishRepository.findAll();
    }

    public Section saveSection(Section section) {
        return sectionRepository.save(section);
    }

    public Dish saveDish(Dish dish) {
        return dishRepository.save(dish);
    }

//    public List<Menu> isProductMappedToMenuMaster(String productId,String storeId) {
//       return menuRepository.getProductMappedToMenuMaster(productId,storeId);
//    }
}