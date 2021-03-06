package com.hangaries.service.config.impl;

import com.hangaries.model.AppDetails;
import com.hangaries.model.ConfigMaster;
import com.hangaries.repository.ApplicationRepository;
import com.hangaries.repository.ConfigMasterRepository;
import com.hangaries.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    ConfigMasterRepository configMasterRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Override
    public List<ConfigMaster> getConfigDetailsByCriteria(String restaurantId, String storeId, String criteria) {
        return configMasterRepository.getDetailsFromConfigMaster(restaurantId, storeId, criteria);
    }

    @Override
    public ConfigMaster addConfigDetailsByCriteria(ConfigMaster configMaster) {
        return configMasterRepository.save(configMaster);
    }

    @Override
    public List<AppDetails> getAppDetails() {
        return applicationRepository.findAll();
    }
}
