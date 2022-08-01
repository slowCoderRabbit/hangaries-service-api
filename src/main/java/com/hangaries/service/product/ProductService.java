package com.hangaries.service.product;

import com.hangaries.model.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);

    Product updatedProduct(String productId, String section, String dish, String dishCategory, String dishType, String productSize);

    List<Product> getAllProduct();
}
