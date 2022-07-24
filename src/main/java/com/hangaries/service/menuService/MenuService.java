package com.hangaries.service.menuService;

import com.hangaries.model.Menu;

import java.util.List;
import java.util.Map;

public interface MenuService {
    List<Menu> getAllMenuItems() throws Exception;

    List<String> getAllSections(String restaurantId, String storeId) throws Exception;

    List<String> getDishesBySection(String s, String restaurantId, String section) throws Exception;

    Map<String, List<String>> getAllSectionsWithDishes(String restaurantId, String storeId);

    Menu saveMenuItem(Menu menu);
}