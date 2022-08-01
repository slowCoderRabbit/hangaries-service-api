package com.hangaries.repository;

import com.hangaries.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Query(value = "select * from MENU_MASTER", nativeQuery = true)
    List<Menu> getAllMenuItems();

    @Query(value = "select distinct section from MENU_MASTER where restaurant_id=:restaurantId and store_id=:storeId", nativeQuery = true)
    List<String> getAllSections(@Param("restaurantId") String restaurantId, @Param("storeId") String storeId) throws Exception;

    @Query(value = "select distinct dish from MENU_MASTER where section=:section and restaurant_id=:restaurantId and store_id=:storeId", nativeQuery = true)
    List<String> getDishesBySection(@Param("section") String section, @Param("restaurantId") String restaurantId, @Param("storeId") String storeId) throws Exception;

    @Query(value = "select * from MENU_MASTER where restaurant_id=:restaurantId and store_id=:storeId", nativeQuery = true)
    List<Menu> getMenuItemsByRestroAndStore(String restaurantId, String storeId);

    @Query(value = "select distinct section, dish from MENU_MASTER where restaurant_id =:restaurantId and store_id=:storeId order by section,dish", nativeQuery = true)
    List<Object[]> getAllSectionsWithDishes(@Param("restaurantId") String restaurantId, @Param("storeId") String storeId);

    @Query(value = "select * from MENU_MASTER where id=:id", nativeQuery = true)
    Menu getMenuItemById(@Param("id") long id);

    @Modifying
    @Transactional
    @Query(value = "update MENU_MASTER set section=:section, dish=:dish, dish_category=:dishCategory, dish_type=:dishType, product_size=:productSize  where product_id=:productId", nativeQuery = true)
    int updatedProductColumns(String productId, String section, String dish, String dishCategory, String dishType, String productSize);

}