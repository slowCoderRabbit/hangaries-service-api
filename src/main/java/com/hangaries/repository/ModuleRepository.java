package com.hangaries.repository;

import com.hangaries.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer> {

    @Query(value = "select * from MODULE_MASTER where restaurant_id=:restaurantId and store_id=:storeId and module_status=:moduleStatus", nativeQuery = true)
    List<Module> getModuleByRestroAndStore(@Param("restaurantId") String restaurantId, @Param("storeId") String storeId, @Param("moduleStatus") String moduleStatus);
}
