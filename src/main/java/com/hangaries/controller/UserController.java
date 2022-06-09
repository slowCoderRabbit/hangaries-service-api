package com.hangaries.controller;

import com.hangaries.model.LoginRequest;
import com.hangaries.model.LoginResponse;
import com.hangaries.model.User;
import com.hangaries.service.login.impl.LoginServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        logger.info("Login request received for userId  = " + loginRequest.getLoginId());
        return loginService.employeeLogin(loginRequest);
    }

    @GetMapping("getUsersByRoleCategory")
    List<User> getUsersByRoleCategory(@RequestParam("roleCategory") String roleCategory) {
        logger.info("Getting list of users for role category = {}.", roleCategory);
        return loginService.getUsersByRoleCategory(roleCategory);
    }

}
