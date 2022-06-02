package com.hangaries.service.role.impl;

import com.hangaries.model.Role;
import com.hangaries.model.RoleWithModules;
import com.hangaries.repository.RoleRepository;
import com.hangaries.service.role.RoleService;
import com.hangaries.service.store.impl.StoreServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);
    @Autowired
    RoleRepository roleRepository;

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
}
