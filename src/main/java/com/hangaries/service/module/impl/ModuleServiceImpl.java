package com.hangaries.service.module.impl;

import com.hangaries.model.Module;
import com.hangaries.repository.ModuleRepository;
import com.hangaries.service.module.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    ModuleRepository moduleRepository;

    @Override
    public List<Module> getAllModule() {
        return moduleRepository.findAll();
    }
}
