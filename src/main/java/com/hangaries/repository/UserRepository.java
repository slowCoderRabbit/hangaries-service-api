package com.hangaries.repository;

import com.hangaries.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByLoginId(String loginId);

    @Query(value = "select * from USER_MASTER where status=:status and role_category=:roleCategory", nativeQuery = true)
    List<User> getUsersByRoleCategory(@Param("roleCategory") String roleCategory, @Param("status") String status);

    @Query(value = "select * from USER_MASTER where status=:status", nativeQuery = true)
    List<User> getAllUsersByRoleCategory(@Param("status") String status);

    @Query(value = "select * from USER_MASTER where restaurant_id=:restaurantId and store_id=:storeId and status=:status", nativeQuery = true)
    List<User> getAllEmployee(@Param("restaurantId") String restaurantId, @Param("storeId") String storeId, @Param("status") String status);

    @Query(value = "select * from USER_MASTER where user_login_id=:loginId", nativeQuery = true)
    User getEmployeeByLoginId(@Param("loginId") String loginId);
}
