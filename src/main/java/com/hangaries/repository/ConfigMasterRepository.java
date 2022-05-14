package com.hangaries.repository;

import com.hangaries.model.ConfigMaster;
import com.hangaries.model.CustomerDtls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigMasterRepository  extends JpaRepository<ConfigMaster, Long>{

    @Query(value = "select * from CONFIG_MASTER where restaurant_id=:restaurantId and store_id=:storeId and config_criteria=:configCriteria", nativeQuery = true)
    List<ConfigMaster> getDetailsFromConfigMaster(@Param("restaurantId") String restaurantId, @Param("storeId") String storeId, @Param("configCriteria") String configCriteria );
}
