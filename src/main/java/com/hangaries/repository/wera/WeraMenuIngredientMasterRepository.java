package com.hangaries.repository.wera;

import com.hangaries.model.wera.WeraMenuIngredientMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeraMenuIngredientMasterRepository extends JpaRepository<WeraMenuIngredientMaster, Long> {

    List<WeraMenuIngredientMaster> findByRestaurantIdAndStoreId(String restaurantId, String storeId);
}
