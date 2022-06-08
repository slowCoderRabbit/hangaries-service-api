package com.hangaries.service.login.impl;

import com.hangaries.model.LoginRequest;
import com.hangaries.model.User;
import com.hangaries.repository.UserRepository;
import com.hangaries.service.login.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
    @Autowired
    UserRepository userRepository;

    @Override
    public String employeeLogin(LoginRequest loginRequest) {

        User user = userRepository.findByLoginId(loginRequest.getLoginId());

        if (null == user) {
            logger.info("No user found for userId = [{}] in DB.", loginRequest.getLoginId());
            return "INCORRECT_ID";
        }
        if (isPasswordCorrect(loginRequest.getPassword(), user.getLoginPassword())) {
            logger.info("Password matched for userId = [{}].", loginRequest.getLoginId());
            return "SUCCESS";

        } else {
            logger.info("Password did not match for userId = [{}].", loginRequest.getLoginId());
            return "INCORRECT_PASSWORD";
        }

    }

    private boolean isPasswordCorrect(String uiPassword, String dbPassword) {
        return uiPassword.equals(dbPassword);
    }


}
