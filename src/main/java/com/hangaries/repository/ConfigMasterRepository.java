package com.hangaries.repository;

import com.hangaries.model.ConfigMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigMasterRepository extends JpaRepository<ConfigMaster, Long> {

    @Query(value = "select * from CONFIG_MASTER where restaurant_id=:restaurantId and store_id=:storeId and config_criteria=:configCriteria ORDER BY config_value", nativeQuery = true)
    List<ConfigMaster> getDetailsFromConfigMaster(@Param("restaurantId") String restaurantId, @Param("storeId") String storeId, @Param("configCriteria") String configCriteria);

    @Query(value = "select * from CONFIG_MASTER where store_id=:storeId and config_criteria=:configCriteria ORDER BY config_value", nativeQuery = true)
    List<ConfigMaster> getDetailsFromConfigMasterForAll(@Param("storeId") String storeId, @Param("configCriteria") String configCriteria);


    @Query(value = "SELECT config_criteria_value, config_criteria_description\n" +
            "  FROM CONFIG_MASTER \n" +
            " WHERE config_criteria = 'PAYMENT_MODE'\n" +
            "   AND restaurant_id =:restaurantId\n" +
            "   AND store_id = 'ALL'\n" +
            " ORDER BY config_value", nativeQuery = true)
    List<Object[]> getPaymentModes(@Param("restaurantId") String restaurantId);
}
