package com.hangaries.service.module.impl;

import com.hangaries.model.ModuleMaster;
import com.hangaries.repository.ModuleRepository;
import com.hangaries.service.module.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.hangaries.constants.HangariesConstants.ACTIVE;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    ModuleRepository moduleRepository;

    @Override
    public List<ModuleMaster> getAllModule(String restaurantId, String storeId) {
        return moduleRepository.findByRestaurantIdAndStoreId(restaurantId, storeId);
    }

    @Override
    public List<ModuleMaster> getModuleByRestroAndStore(String restaurantId, String storeId) {
        return moduleRepository.getModuleByRestroAndStore(restaurantId, storeId, ACTIVE);
    }
}
