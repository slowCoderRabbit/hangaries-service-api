package com.hangaries.repository;

import com.hangaries.model.SubProduct;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SubProductRepository extends JpaRepository<SubProduct, Long> {

    @Query(value = "select max(id) FROM SUB_PRODUCT_MASTER", nativeQuery = true)
    int getMaxId();

    @Modifying
    @Transactional
    @Query(value = "update SUB_PRODUCT_MASTER set ingredient_type=:ingredientType, category=:category, size=:size where sub_product_id=:subProductId", nativeQuery = true)
    int updatedSubProduct(String subProductId, String ingredientType, String category, String size);

    SubProduct findBySubProductId(String subProductId);

    List<SubProduct> findByRestaurantId(String restaurantId, Sort subProductId);
}
