package com.hangaries.service.product.impl;

import com.hangaries.model.*;
import com.hangaries.repository.MenuRepository;
import com.hangaries.repository.ProductRepository;
import com.hangaries.repository.SubProductRepository;
import com.hangaries.service.product.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    public static final String P = "P";
    public static final String S = "S";
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    ProductRepository productRepository;

    @Autowired
    SubProductRepository subProductRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public Product saveProduct(Product product) {
        int maxId = productRepository.getMaxId();
        logger.info("Max id from PRODUCT_MASTER = [{}]", maxId);
        maxId = maxId + 1;
        product.setId(maxId);
        product.setProductId(P + getPaddedString(maxId));
        logger.info("New Max id = {} and new product Id = [{}]", product.getId(), product.getProductId());
        return productRepository.save(product);
    }

    @Override
    public Product updatedProduct(String productId, String section, String dish, String dishCategory, String dishType, String productSize) {
        int result = productRepository.updatedProduct(productId, section, dish, dishCategory, dishType, productSize);
        logger.info("Result of update product = {}. ", result);
        return productRepository.findByProductId(productId);
    }

    @Override
    public List<Product> getAllProduct(String restaurantId) {
        return productRepository.findByRestaurantId(restaurantId, Sort.by(Sort.Direction.ASC, "productId"));

    }

    @Override
    public SubProduct saveSubProduct(SubProduct subProduct) {
        int maxId = subProductRepository.getMaxId();
        logger.info("Max id from SUB_PRODUCT_MASTER = [{}]", maxId);
        maxId = maxId + 1;
        subProduct.setId(maxId);
        subProduct.setSubProductId(S + getPaddedString(maxId));
        logger.info("New Max id = {} and new sub product Id = [{}]", subProduct.getId(), subProduct.getSubProductId());
        return subProductRepository.save(subProduct);
    }

    @Override
    public SubProduct updatedSubProduct(String subProductId, String ingredientType, String category, String size) {
        int result = subProductRepository.updatedSubProduct(subProductId, ingredientType, category, size);
        logger.info("Result of update sub product = {}. ", result);
        return subProductRepository.findBySubProductId(subProductId);
    }


    public List<SubProduct> getAllSubProduct(String restaurantId) {
        return subProductRepository.findByRestaurantId(restaurantId, Sort.by(Sort.Direction.ASC, "subProductId"));
    }

    private String getPaddedString(int maxId) {
        return String.format("%03d", maxId);
    }

    public List<Menu> saveProductMenuMapping(ProductMenuMappingRequestList requestList) {

        if (!requestList.getMappings().isEmpty()) {
            for (ProductMenuMappingRequest mapping : requestList.getMappings()) {
                List<Menu> menuList = getProductMappedToMenuMaster(mapping);
                if (menuList.isEmpty()) {
                    logger.info("Menu mapping NOT found for [{}]. Creating!!!", mapping);
                    Product product = productRepository.findByProductIdAndRestaurantId(mapping.getProductId(), mapping.getRestaurantId());
                    if (null != product) {
                        Menu menu = getMenuFromProduct(product, mapping);
                        menu.setMenuAvailableFlag(mapping.getMenuAvailable());
                        menuRepository.save(menu);
                    }
                } else {
                    logger.info("Menu mapping found for [{}]. Updating!!!", mapping);
                    for (Menu menu : menuList) {
                        menu.setMenuAvailableFlag(mapping.getMenuAvailable());
                        menuRepository.save(menu);
                    }
                }
            }
        }
        return menuRepository.getMenuItemsByRestroAndStore(requestList.getMappings().stream().findFirst().get().getRestaurantId(), requestList.getMappings().stream().findFirst().get().getStoreId());

    }

    private Menu getMenuFromProduct(Product product, ProductMenuMappingRequest mapping) {
        Menu newMenu = new Menu();
        newMenu.setRestaruantId(mapping.getRestaurantId());
        newMenu.setStoreId(mapping.getStoreId());
        newMenu.setProductId(product.getProductId());
        newMenu.setSection(product.getSection());
        newMenu.setDish(product.getDish());
        newMenu.setDishSpiceIndicatory(product.getDishSpiceIndicator());
        newMenu.setDishCategory(product.getDishCategory());
        newMenu.setDishType(product.getDishType());
        newMenu.setDishDescriptionId(product.getDishDescriptionId());
        newMenu.setProductSize(product.getProductSize());
        newMenu.setImagePath(product.getImagePath());
        newMenu.setCommonImage(product.getCommonImage());
        newMenu.setPrice((double) product.getPrice());
        newMenu.setMenuAvailableFlag(product.getMenuAvailableFlag());
        newMenu.setIngredientExistsFalg(product.getIngredientExistFlag());
        newMenu.setKdsRoutingName(product.getKdsRoutingName());
        return newMenu;
    }

    private List<Menu> getProductMappedToMenuMaster(ProductMenuMappingRequest mapping) {
        return menuRepository.getProductMappedToMenuMaster(mapping.getProductId(), mapping.getRestaurantId(), mapping.getStoreId());
    }

    public List<Product> getProductById(String restaurantId, String storeId, String productId) {
        return productRepository.findByRestaurantIdAndStoreIdAndProductId(restaurantId, storeId, productId);
    }
}
