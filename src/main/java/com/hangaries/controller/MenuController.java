package com.hangaries.controller;


import com.hangaries.model.*;
import com.hangaries.service.menuIngridentService.Impl.MenuIngredientServiceImpl;
import com.hangaries.service.menuService.Impl.MenuServiceImpl;
import com.hangaries.service.product.impl.ProductServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class MenuController {

    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
    @Autowired
    private MenuServiceImpl menuService;
    @Autowired
    private MenuIngredientServiceImpl menuIngredientService;

    @Autowired
    private ProductServiceImpl productService;

    /**
     * Get all menuItems
     *
     * @return
     * @throws Exception
     */
    @GetMapping("getAllMenuItems")
    @ResponseBody
    public ResponseEntity<List<Menu>> getAllMenuItems() throws Exception {
        logger.info("Get all menu items::");
        List<Menu> menuList = new ArrayList<Menu>();
        try {
            menuList = menuService.getAllMenuItems();
            return new ResponseEntity<List<Menu>>(menuList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting menuitems::" + ex.getMessage());
            return new ResponseEntity<List<Menu>>(menuList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get all menuItems
     *
     * @return
     * @throws Exception
     */
    @GetMapping("getMenuItemsByRestroAndStore")
    @ResponseBody
    public ResponseEntity<List<Menu>> getMenuItemsByRestroAndStore(@RequestParam("restaurantId") String restaurantId, @RequestParam("storeId") String storeId) throws Exception {
        logger.info("Get all menu items for restaurantId = {} and storeId = {}.", restaurantId, storeId);
        List<Menu> menuList = new ArrayList<Menu>();
        try {
            menuList = menuService.getMenuItemsByRestroAndStore(restaurantId, storeId);
            return new ResponseEntity<List<Menu>>(menuList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting menuitems::" + ex.getMessage());
            return new ResponseEntity<List<Menu>>(menuList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Get all ingredients by productId
     *
     * @param productId
     * @return
     * @throws Exception
     */
    @GetMapping("getMenuIngredientsByMenuId")
    @ResponseBody
    public ResponseEntity<List<MenuIngrident>> getIngredientsByMenuId(@RequestParam("productId") String productId, @RequestParam("restaurantId") String restaurantId,
                                                                      @RequestParam("storeId") String storeId) throws Exception {
        List<MenuIngrident> menuIngridentList = new ArrayList<MenuIngrident>();
        try {
            logger.info("Get ingredients::");
            menuIngridentList = menuIngredientService.getIngredientsByMenuId(productId, restaurantId, storeId);
            return new ResponseEntity<List<MenuIngrident>>(menuIngridentList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting Ingredients::" + ex.getMessage());
            return new ResponseEntity<List<MenuIngrident>>(menuIngridentList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getMenuIngredientsByRestoAndStoreId")
    @ResponseBody
    public ResponseEntity<List<MenuIngrident>> getMenuIngredientsByRestoAndStoreId(@RequestParam("restaurantId") String restaurantId,
                                                                                   @RequestParam("storeId") String storeId) throws Exception {
        List<MenuIngrident> menuIngridentList = new ArrayList<MenuIngrident>();
        try {
            logger.info("Get ingredients for restaurantId = {}, storeId = {}. ", restaurantId, storeId);
            menuIngridentList = menuIngredientService.getAllIngredientsByRestoAndStoreId(restaurantId, storeId);
            return new ResponseEntity<List<MenuIngrident>>(menuIngridentList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting Ingredients::" + ex.getMessage());
            return new ResponseEntity<List<MenuIngrident>>(menuIngridentList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getAllSections")
    @ResponseBody
    public ResponseEntity<List<String>> getSections(@RequestParam("restaurantId") String restaurantId, @RequestParam("storeId") String storeId) throws Exception {
        List<String> sections;
        try {
            logger.info("Get all sections for restaurantId = {}, storeId = {}. ", restaurantId, storeId);
            sections = menuService.getAllSections(restaurantId, storeId);
            return new ResponseEntity<List<String>>(sections, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting Sections::" + ex.getMessage());
            return new ResponseEntity<List<String>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getDishesBySection")
    @ResponseBody
    public ResponseEntity<List<String>> getDishesBySection(@RequestParam("section") String section, @RequestParam("restaurantId") String restaurantId, @RequestParam("storeId") String storeId) throws Exception {
        List<String> sections;
        try {
            logger.info("Get Dishes for section = {}, restaurantId = {}, storeId = {}.", section, restaurantId, storeId);
            sections = menuService.getDishesBySection(section, restaurantId, storeId);
            return new ResponseEntity<List<String>>(sections, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting Sections::" + ex.getMessage());
            return new ResponseEntity<List<String>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getAllSectionsWithDishes")
    @ResponseBody
    public ResponseEntity<Map<String, List<String>>> getAllSectionsWithDishes(@RequestParam("restaurantId") String restaurantId, @RequestParam("storeId") String storeId) throws Exception {
        Map<String, List<String>> sectionWithDishes = null;
        try {
            logger.info("Get all sections for restaurantId = {}, storeId = {}.", restaurantId, storeId);
            sectionWithDishes = menuService.getAllSectionsWithDishes(restaurantId, storeId);
            return new ResponseEntity<Map<String, List<String>>>(sectionWithDishes, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting Sections::" + ex.getMessage());
            return new ResponseEntity<Map<String, List<String>>>((Map<String, List<String>>) new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("saveMenuItem")
    @ResponseBody
    public ResponseEntity<Menu> saveMenuItem(@RequestBody Menu menu) {
        logger.info("Saving menu item = {}. ", menu);
        Menu newMenu = menuService.saveMenuItem(menu);
        return new ResponseEntity<Menu>(newMenu, HttpStatus.OK);

    }

    @PostMapping("saveMenuIngredient")
    @ResponseBody
    public ResponseEntity<MenuIngrident> saveUpdateMenuIngredient(@RequestBody MenuIngrident menuIngredient) {
        logger.info("Saving menu ingredient = {}. ", menuIngredient);
        MenuIngrident newMenuIngredient = menuIngredientService.saveMenuIngredient(menuIngredient);
        return new ResponseEntity<MenuIngrident>(newMenuIngredient, HttpStatus.OK);

    }

    @GetMapping("getMenuIngredientList")
    @ResponseBody
    public ResponseEntity<List<MenuIngredientList>> getMenuIngredientList() {
        logger.info("getMenuIngredientList !!!!!!!! ");
        List<MenuIngredientList> newMenuIngredient = menuIngredientService.getMenuIngredientList();
        logger.info("getMenuIngredientList size = {} ", newMenuIngredient.size());
        return new ResponseEntity<List<MenuIngredientList>>(newMenuIngredient, HttpStatus.OK);

    }

    @PostMapping("saveProduct")
    @ResponseBody
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        logger.info("Saving Product = {}. ", product);
        Product newProduct = productService.saveProduct(product);
        return new ResponseEntity<Product>(newProduct, HttpStatus.OK);

    }

    @GetMapping("getAllProduct")
    @ResponseBody
    public ResponseEntity<List<Product>> getAllProduct() {
        logger.info("Getting list of all products,menu items.");
        List<Product> products = productService.getAllProduct();
        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);

    }

    @PostMapping("saveSubProduct")
    @ResponseBody
    public ResponseEntity<SubProduct> saveSubProduct(@RequestBody SubProduct subProduct) {
        logger.info("Saving Sub Product = {}. ", subProduct);
        SubProduct newSubProduct = productService.saveSubProduct(subProduct);
        return new ResponseEntity<SubProduct>(newSubProduct, HttpStatus.OK);

    }


}
