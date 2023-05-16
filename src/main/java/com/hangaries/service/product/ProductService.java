package com.hangaries.service.product;

import com.hangaries.model.Product;
import com.hangaries.model.SubProduct;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);

    Product updatedProduct(String productId, String section, String dish, String dishCategory, String dishType, String productSize);

    List<Product> getAllProduct(String restaurantId);

    SubProduct saveSubProduct(SubProduct subProduct);

    SubProduct updatedSubProduct(String subProductId, String ingredientType, String category, String size);
}
