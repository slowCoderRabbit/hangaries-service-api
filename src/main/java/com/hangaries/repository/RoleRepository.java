package com.hangaries.repository;

import com.hangaries.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query(value = "select * from ROLE_MASTER where restaurant_id=:restaurantId and store_id=:storeId and role_category=:roleCategory and role_status=:roleStatus", nativeQuery = true)
    List<Role> getRoleByRoleCategory(@Param("restaurantId") String restaurantId, @Param("storeId") String storeId,
                                     @Param("roleCategory") String roleCategory, @Param("roleStatus") String roleStatus);

    @Query(value = "select * from ROLE_MASTER where restaurant_id=:restaurantId and store_id=:storeId and role_status=:roleStatus", nativeQuery = true)
    List<Role> getRoleByRoleCategory(@Param("restaurantId") String restaurantId, @Param("storeId") String storeId, @Param("roleStatus") String roleStatus);

    List<Role> findByRestaurantId(String restaurantId);
}
