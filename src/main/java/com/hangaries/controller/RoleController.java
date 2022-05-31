package com.hangaries.controller;

import com.hangaries.model.CustomerDtls;
import com.hangaries.model.Role;
import com.hangaries.service.role.impl.RoleServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    RoleServiceImpl roleService;

    @GetMapping("getAllRoles")
    public ResponseEntity<List<Role>> getAllRoles() {
        logger.info("Getting list of all User roles...");

        List<Role> roleList = new ArrayList<>();
        try {
            roleList = roleService.getAllRoles();
            return new ResponseEntity<List<Role>>(roleList, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting store details::" + ex.getMessage());
            return new ResponseEntity<List<Role>>(roleList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("saveNewRole")
    public ResponseEntity<Role> saveNewRole(@RequestBody Role role) {
        logger.info("Adding new role {}.",role);

        Role newRole =null;
        try {
            newRole = roleService.saveNewRole(role);
            return new ResponseEntity<Role>(newRole, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting store details::" + ex.getMessage());
            return new ResponseEntity<Role>(newRole, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
