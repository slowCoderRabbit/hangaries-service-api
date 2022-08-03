package com.hangaries.service.product.impl;

import com.hangaries.model.Product;
import com.hangaries.model.SubProduct;
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
    public List<Product> getAllProduct() {
        return productRepository.findAll(Sort.by(Sort.Direction.ASC, "productId"));

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

    private String getPaddedString(int maxId) {
        return String.format("%03d", maxId);
    }
}
