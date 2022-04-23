package com.hangaries.repository;

import com.hangaries.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Query(value = "select * from MENU_MASTER", nativeQuery = true)
    List<Menu> getAllMenuItems();

    @Query(value = "select distinct section from MENU_MASTER", nativeQuery = true)
    List<String> getAllSections() throws Exception;

    @Query(value = "select distinct dish from MENU_MASTER where section=:section", nativeQuery = true)
    List<String> getDishesBySection(@Param("section") String section) throws Exception;

    @Query(value = "select * from MENU_MASTER where restaurant_id=:restaurantId and store_id=:storeId", nativeQuery = true)
    List<Menu> getMenuItemsByRestroAndStore(String restaurantId, String storeId);
}