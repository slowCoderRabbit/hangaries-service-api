package com.hangaries.controller;

import com.hangaries.model.LoginRequest;
import com.hangaries.service.login.impl.LoginServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    LoginServiceImpl loginService;

    @PostMapping("employeeLogin")
    HashMap<String, String> employeeLogin(@Valid @RequestBody LoginRequest loginRequest) {
        logger.info("Login request received for userId  = " + loginRequest.getLoginId());
        String loginResponse = loginService.employeeLogin(loginRequest);
        HashMap<String, String> map = new HashMap<>();
        map.put("loginResponse", loginResponse);
        return map;
    }

}
