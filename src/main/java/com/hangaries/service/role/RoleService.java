package com.hangaries.service.role;

import com.hangaries.model.Role;
import com.hangaries.model.RoleModuleResponse;
import com.hangaries.model.RoleWithModules;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    Role saveNewRole(Role role);

    String saveNewRoleWithModuleAccess(RoleWithModules role);

    List<RoleModuleResponse> getRoleWithModuleAccess(String restaurantId, String storeId, String roleCategory);

    String deleteRoleWithModuleAccess(RoleWithModules role);
}
