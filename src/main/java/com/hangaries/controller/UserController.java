package com.hangaries.controller;

import com.hangaries.model.*;
import com.hangaries.service.login.impl.LoginServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    LoginServiceImpl loginService;

    @PostMapping("employeeLogin")
    LoginResponse employeeLogin(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("Login request received for userId  = {} ", loginRequest.getLoginId());
        return loginService.employeeLogin(loginRequest);
    }

    @PostMapping("employeeLogout")
    String employeeLogout(@Valid @RequestBody LogoutRequest logoutRequest) {
        logger.info("Logout request received for userId  = {} ", logoutRequest.getLoginId());
        return loginService.employeeLogout(logoutRequest);
    }

    @GetMapping("getUsersByRoleCategory")
    List<User> getUsersByRoleCategory(@RequestParam("roleCategory") String roleCategory) {
        logger.info("Getting list of users for role category = {}.", roleCategory);
        return loginService.getUsersByRoleCategory(roleCategory);
    }

    @PostMapping("addUpdateEmployee")
    ResponseEntity<User> addEmployee(@Valid @RequestBody User user) {
        logger.info("Adding new user = {}." + user);
        User newUser = null;

        newUser = loginService.addEmployee(user);
        return new ResponseEntity<User>(newUser, HttpStatus.OK);

    }

    @GetMapping("getAllEmployee")
    List<User> getAllEmployee(@RequestParam("restaurantId") String restaurantId,
                              @RequestParam("storeId") String storeId,
                              @RequestParam("status") String status) {
        logger.info("Getting list of users for role restaurantId = {}, storeId = {} and status = {}.", restaurantId, storeId, status);
        return loginService.getAllEmployee(restaurantId, storeId, status);
    }

    @GetMapping("getEmployeeByLoginId")
    ResponseEntity<User> getEmployeeByLoginId(@RequestParam("loginId") String loginId) {
        logger.info("Getting list of users for loginId = {}.", loginId);

        User newUser = null;
        try {
            newUser = loginService.getEmployeeByLoginId(loginId);
            return new ResponseEntity<User>(newUser, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error adding or updating user  = {} :: {}", newUser, ex.getMessage());
            return new ResponseEntity<User>(newUser, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("updateEmployeePasswordByLoginId")
    LoginResponse updateEmployeePasswordByLoginId(@Valid @RequestBody UpdatePasswordRequest updatePasswordRequest) {
        logger.info("Updating password for loginID = {}.", updatePasswordRequest.getLoginId());
        return loginService.updateEmployeePasswordByLoginId(updatePasswordRequest.getLoginId(), updatePasswordRequest.getLoginPassword());

    }


}
