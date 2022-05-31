package com.hangaries.service.role;

import com.hangaries.model.Role;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    Role saveNewRole(Role role);
}
