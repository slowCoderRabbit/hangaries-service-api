package com.hangaries.repository;

import com.hangaries.model.RoleModuleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface RoleModuleRepository extends JpaRepository<RoleModuleMapping, Integer> {

    @Query(value = "select * from ROLE_MODULE_MAPPING_MASTER where restaurant_id=:restaurantId and store_id=:storeId and role_category=:roleCategory", nativeQuery = true)
    List<RoleModuleMapping> getRoleModuleMapping(@Param("restaurantId") String restaurantId, @Param("storeId") String storeId, @Param("roleCategory") String roleCategory);


    @Modifying
    @Transactional
    @Query(value = "delete from ROLE_MODULE_MAPPING_MASTER where restaurant_id=:restaurantId and store_id=:storeId and role_category=:roleCategory and module_id IN (:moduleIds)", nativeQuery = true)
    int deleteRoleWithModuleAccess(@Param("restaurantId") String restaurantId, @Param("storeId") String storeId,
                                   @Param("roleCategory") String roleCategory, @Param("moduleIds") List<Integer> moduleIds);


}
