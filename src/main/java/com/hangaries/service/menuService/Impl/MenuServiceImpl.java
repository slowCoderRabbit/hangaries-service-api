package com.hangaries.service.menuService.Impl;

import com.hangaries.controller.MenuController;
import com.hangaries.model.Menu;
import com.hangaries.repository.MenuRepository;
import com.hangaries.service.menuService.MenuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class MenuServiceImpl implements MenuService {
    private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    private MenuRepository menuRepository;

    /**
     * Get all menuItems
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

    /**
     * Get all sections
     * @return
     * @throws Exception
     */
    public List<String> getAllSections() throws Exception {
        logger.debug("Get all sections::");
        List<String> sectionList = null;
        try {
            sectionList = menuRepository.getAllSections();
        } catch (Exception ex) {
            logger.error("Error while getting all sections::");
            throw new Exception(ex);
        }
        return sectionList;
    }

    /**
     * Get dishses by section
     * @param section
     * @return
     * @throws Exception
     */
    public List<String> getDishesBySection(String section) throws Exception {
        logger.debug("Get all dishes::");
        List<String> dishList = null;
        try {
            dishList = menuRepository.getDishesBySection(section);

        } catch (Exception ex) {
            logger.error("Error while getting dishes::");
            throw new Exception(ex);
        }
        return dishList;
    }
}