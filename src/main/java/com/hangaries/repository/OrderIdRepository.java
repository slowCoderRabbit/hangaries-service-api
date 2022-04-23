package com.hangaries.repository;

import com.hangaries.model.Menu;
import com.hangaries.model.OrderId;
import com.hangaries.model.SequenceMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface OrderIdRepository extends JpaRepository<SequenceMaster, Long> {

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(value = "select order_sequence_number from SEQUENCE_MASTER where restaurant_id=:restaurantId and store_id=:storeId FOR UPDATE", nativeQuery = true)
    int getLatestOrderId(String restaurantId, String storeId);

    @Modifying
    @Query(value = "UPDATE SEQUENCE_MASTER SET order_sequence_number =:newOrderId where restaurant_id=:restaurantId and store_id=:storeId", nativeQuery = true)
    int saveNewOrderId(String restaurantId, String storeId,int newOrderId);



}
