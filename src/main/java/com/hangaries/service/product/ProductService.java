package com.hangaries.service.product;

import com.hangaries.model.Product;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);

    Product updatedProduct(Product product);

    List<Product> getAllProduct();
}
