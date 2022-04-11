package com.hangaries.service.menuService;

import com.hangaries.model.Menu;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MenuService {
    List<Menu>getAllMenuItems() throws Exception;
    List<String>getAllSections()throws  Exception;
    List<String>getDishesBySection(String section) throws Exception;
}