package com.hangaries.service.module;

import com.hangaries.model.Module;

import java.util.List;

public interface ModuleService {
    List<Module> getAllModule();

    List<Module> getModuleByRestroAndStore(String restaurantId, String storeId);
}
