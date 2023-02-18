package com.hangaries.service.module;

import com.hangaries.model.ModuleMaster;

import java.util.List;

public interface ModuleService {
    List<ModuleMaster> getAllModule(String restaurantId, String storeId);

    List<ModuleMaster> getModuleByRestroAndStore(String restaurantId, String storeId);
}
