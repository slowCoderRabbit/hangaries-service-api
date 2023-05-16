package com.hangaries.controller;

import com.hangaries.model.Role;
import com.hangaries.model.RoleModuleResponse;
import com.hangaries.model.RoleWithModules;
import com.hangaries.service.role.impl.RoleServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static com.hangaries.constants.HangariesConstants.ERROR;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    RoleServiceImpl roleService;

    @GetMapping("getAllRoles")
    public ResponseEntity<List<Role>> getAllRoles(@RequestParam("restaurantId") String restaurantId) {
        logger.info("Getting list of all User roles for restaurantId = {}.", restaurantId);

        List<Role> roleList = new ArrayList<>();
        try {
            roleList = roleService.getAllRoles(restaurantId);
            return new ResponseEntity<List<Role>>(roleList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting store details::" + ex.getMessage());
            return new ResponseEntity<List<Role>>(roleList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("saveNewRole")
    public ResponseEntity<Role> saveNewRole(@RequestBody @Valid Role role) {
        logger.info("Adding new role {}.", role);

        Role newRole = null;
        try {
            newRole = roleService.saveNewRole(role);
            return new ResponseEntity<Role>(newRole, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting store details::" + ex.getMessage());
            return new ResponseEntity<Role>(newRole, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("saveNewRoleWithModuleAccess")
    public ResponseEntity<String> saveNewRoleWithModuleAccess(@RequestBody @Valid RoleWithModules role) {
        logger.info("Adding new role {}.", role);

        String result = null;
        try {
            result = roleService.saveNewRoleWithModuleAccess(role);
            return new ResponseEntity<String>(result, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting store details::" + ex.getMessage());
            return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getRoleWithModuleAccess")
    public ResponseEntity<List<RoleModuleResponse>> getRoleWithModuleAccess(@RequestParam("restaurantId") String restaurantId,
                                                                            @RequestParam("storeId") String storeId,
                                                                            @RequestParam("roleCategory") String roleCategory) {
        logger.info("Getting modules for restaurantId = {}, storeId = {} and role = {}.", restaurantId, storeId, roleCategory);

        List<RoleModuleResponse> roleModuleMappingList = null;
        try {
            roleModuleMappingList = roleService.getRoleWithModuleAccess(restaurantId, storeId, roleCategory);
            return new ResponseEntity<List<RoleModuleResponse>>(roleModuleMappingList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting store details::" + ex.getMessage());
            return new ResponseEntity<List<RoleModuleResponse>>(roleModuleMappingList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("deleteRoleWithModuleAccess")
    public ResponseEntity<String> deleteRoleWithModuleAccess(@RequestBody @Valid RoleWithModules role) {
        logger.info("Removing modules for role {}.", role);

        String result = null;
        try {
            result = roleService.deleteRoleWithModuleAccess(role);
            return new ResponseEntity<String>(result, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting store details::" + ex.getMessage());
            return new ResponseEntity<String>(ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
