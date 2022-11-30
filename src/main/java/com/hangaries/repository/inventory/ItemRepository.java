package com.hangaries.repository.inventory;

import com.hangaries.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query(value = "select * from ITEM_MASTER where item_status=:status order by item_id", nativeQuery = true)
    List<Item> getItemsByStatus(@Param("status") String status);

    @Modifying
    @Transactional
    @Query(value = "update ITEM_MASTER set item_status=:status, updated_by=:updatedBy,updated_date=:updatedDate where item_id=:id", nativeQuery = true)
    int saveItemOrderStatus(@Param("id") long id, @Param("status") String status, @Param("updatedBy") String updatedBy, @Param("updatedDate") Date updatedDate);

    @Query(value = "select * from ITEM_MASTER where item_id=:id", nativeQuery = true)
    Item getItemById(@Param("id") long id);

}
