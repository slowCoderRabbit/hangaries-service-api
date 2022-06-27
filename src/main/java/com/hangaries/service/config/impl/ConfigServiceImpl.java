package com.hangaries.service.config.impl;

import com.hangaries.model.ConfigMaster;
import com.hangaries.repository.ConfigMasterRepository;
import com.hangaries.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    ConfigMasterRepository configMasterRepository;

    @Override
    public List<ConfigMaster> getConfigDetailsByCriteria(String restaurantId, String storeId, String criteria) {
        return configMasterRepository.getDetailsFromConfigMaster(restaurantId, storeId, criteria);
    }

    @Override
    public ConfigMaster addConfigDetailsByCriteria(ConfigMaster configMaster) {
        return configMasterRepository.save(configMaster);
    }
}
