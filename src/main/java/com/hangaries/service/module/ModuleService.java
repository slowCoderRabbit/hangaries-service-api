package com.hangaries.service.module;

import com.hangaries.model.ModuleMaster;

import java.util.List;

public interface ModuleService {
    List<ModuleMaster> getAllModule();

    List<ModuleMaster> getModuleByRestroAndStore(String restaurantId, String storeId);
}
