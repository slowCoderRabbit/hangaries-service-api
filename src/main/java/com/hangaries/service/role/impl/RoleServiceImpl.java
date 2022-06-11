package com.hangaries.service.role.impl;

import com.hangaries.model.*;
import com.hangaries.repository.ModuleRepository;
import com.hangaries.repository.RoleModuleRepository;
import com.hangaries.repository.RoleRepository;
import com.hangaries.service.role.RoleService;
import com.hangaries.service.store.impl.StoreServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.hangaries.constants.HangariesConstants.ACTIVE;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    RoleModuleRepository roleModuleRepository;

    @Autowired
    ModuleRepository moduleRepository;

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role saveNewRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role saveNewRoleWithModuleAccess(RoleWithModules role) {
        return null;
    }

    @Override
    public List<RoleModuleResponse> getRoleWithModuleAccess(String restaurantId, String storeId, String roleCategory) {

        List<RoleModuleResponse> roleModuleMappingList = new ArrayList<>();
        RoleModuleResponse roleModuleResponse = null;
        List<Role> roles = getRoles(restaurantId, storeId, roleCategory);
        logger.info("Role fetched {}. Fetching categories for {} roles !!", roles.size());

        List<Module> roleModules = null;
        for (Role role : roles) {
            roleModuleResponse = new RoleModuleResponse();
            roleModules = getModules(role.getRestaurantId(), role.getStoreId(), role.getRoleCategory());
            roleModuleResponse.setRole(role);
            roleModuleResponse.setModules(roleModules);
            roleModuleMappingList.add(roleModuleResponse);


        }
        return roleModuleMappingList;
    }

    private List<Role> getRoles(String restaurantId, String storeId, String roleCategory) {

        if (roleCategory.equalsIgnoreCase("ALL")) {
            return roleRepository.getRoleByRoleCategory(restaurantId, storeId, ACTIVE);
        } else {
            return roleRepository.getRoleByRoleCategory(restaurantId, storeId, roleCategory, ACTIVE);
        }

    }

    List<Module> getModules(String restaurantId, String storeId, String roleCategory) {
        logger.info("Fetching role - module mapping for restaurantId = {}, storeId = {}, roleCategory = {}.", restaurantId, storeId, roleCategory);
        List<RoleModuleMapping> mappings = roleModuleRepository.getRoleModuleMapping(restaurantId, storeId, roleCategory);
        List<Integer> roleMappingIds = mappings.stream().map(m -> m.getModuleId()).collect(Collectors.toList());
        logger.info("Getting modules for roleMappingIds = {}", roleMappingIds);
        return moduleRepository.findByModuleIdIn(roleMappingIds);
    }
}
