package com.hangaries.repository.wera;

import com.hangaries.model.wera.WeraMenuMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeraMenuMasterRepository extends JpaRepository<WeraMenuMaster, Long> {

    List<WeraMenuMaster> findByRestaurantIdAndStoreId(String restaurantId, String storeId);
}
