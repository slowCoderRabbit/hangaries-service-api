package com.hangaries.repository;

import com.hangaries.model.dto.OrderMenuIngredientAddressDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderMenuIngredientAddressRepository extends JpaRepository<OrderMenuIngredientAddressDTO, Long> {

    @Query(value = "SELECT * FROM vOrderMenuIngredientAddress where restaurant_id=:restaurantId and store_id=:storeId and mobile_number=:mobileNumber", nativeQuery = true)
    List<OrderMenuIngredientAddressDTO> getOrderMenuIngredientAddressView(@Param("restaurantId") String restaurantId, @Param("storeId") String storeId, @Param("mobileNumber") String mobileNumber);

    @Query(value = "SELECT * FROM vOrderMenuIngredientAddress where order_id=:orderId order by order_id, product_id, sub_product_id", nativeQuery = true)
    List<OrderMenuIngredientAddressDTO> getOrderMenuIngredientAddressViewByOrderId(@Param("orderId") String orderId);


}
