package com.hangaries.repository;

import com.hangaries.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select max(id) FROM PRODUCT_MASTER", nativeQuery = true)
    int getMaxId();
}
