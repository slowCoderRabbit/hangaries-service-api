package com.hangaries.repository;

import com.hangaries.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByStoreId(String storeId);

    long deleteByStoreId(String storeId);

    Store findByWeraMerchantId(String weraMerchantId);

    @Modifying
    @Transactional
    @Query(value = "update STORE_MASTER set store_active_flag=:storeActiveFlag where store_Id=:storeId", nativeQuery = true)
    int updateStoreActiveFlag(String storeId, String storeActiveFlag);

    @Procedure(procedureName = "sp_SetupNewStore", outputParameterName = "oErrorDescription")
    String setupNewStore(@Param("restaurantId") String restaurantId, @Param("storeId") String storeId);

    List<Store> findByRestaurantId(String restaurantId);
}
