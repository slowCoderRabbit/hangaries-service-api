package com.hangaries.service.config;

import com.hangaries.model.AppDetails;
import com.hangaries.model.ConfigMaster;

import java.util.List;

public interface ConfigService {
    List<ConfigMaster> getConfigDetailsByCriteria(String restaurantId, String storeId, String criteria);

    ConfigMaster addConfigDetailsByCriteria(ConfigMaster configMaster);

    List<AppDetails> getAppDetails();


}
