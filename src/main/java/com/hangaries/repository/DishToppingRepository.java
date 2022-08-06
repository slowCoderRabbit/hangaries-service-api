package com.hangaries.repository;

import com.hangaries.model.DishToppingMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DishToppingRepository extends JpaRepository<DishToppingMapping, Integer> {
    @Query(value = "select * from DISH_TOPPING_MAPPING where restaurant_id =:restaurantId and store_id=:storeId order by product_id", nativeQuery = true)
    List<DishToppingMapping> getDishToppingsByRestoAndStore(@Param("restaurantId") String restaurantId, @Param("storeId") String storeId);
}
