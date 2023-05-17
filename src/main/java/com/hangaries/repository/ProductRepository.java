package com.hangaries.repository;

import com.hangaries.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select max(id) FROM PRODUCT_MASTER", nativeQuery = true)
    int getMaxId();

    @Modifying
    @Transactional
    @Query(value = "update PRODUCT_MASTER set section=:section, dish=:dish, dish_category=:dishCategory, dish_type=:dishType, product_size=:productSize  where product_id=:productId", nativeQuery = true)
    int updatedProduct(String productId, String section, String dish, String dishCategory, String dishType, String productSize);

    Product findByProductId(String productId);

    List<Product> findByRestaurantId(String restaurantId, Sort productId);
}
