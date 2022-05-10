package com.hangaries.service.menuService;

import com.hangaries.model.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getAllMenuItems() throws Exception;

    List<String> getAllSections(String restaurantId, String storeId) throws Exception;

    List<String> getDishesBySection(String s, String restaurantId, String section) throws Exception;
}