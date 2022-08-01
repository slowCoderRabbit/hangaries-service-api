package com.hangaries.service.product.impl;

import com.hangaries.model.Product;
import com.hangaries.repository.ProductRepository;
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
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        int maxId = productRepository.getMaxId();
        logger.info("Max id from PRODUCT_MASTER = [{}]", maxId);
        maxId = maxId + 1;
        product.setId(maxId);
        product.setProductId(P + maxId);
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
}
