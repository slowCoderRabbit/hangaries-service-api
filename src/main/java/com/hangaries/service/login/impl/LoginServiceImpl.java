package com.hangaries.service.login.impl;

import com.hangaries.model.LoginRequest;
import com.hangaries.model.LoginResponse;
import com.hangaries.model.User;
import com.hangaries.repository.UserRepository;
import com.hangaries.service.login.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.hangaries.constants.HangariesConstants.*;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
    @Autowired
    UserRepository userRepository;

    @Override
    public LoginResponse employeeLogin(LoginRequest loginRequest) {

        User user = userRepository.findByLoginId(loginRequest.getLoginId());

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUser(user);

        if (null == user) {
            logger.info("No user found for userId = [{}] in DB.", loginRequest.getLoginId());
            loginResponse.setLoginResponse(INCORRECT_ID);
        } else if (isPasswordCorrect(loginRequest.getPassword(), user.getLoginPassword())) {
            logger.info("Password matched for userId = [{}].", loginRequest.getLoginId());
            loginResponse.setLoginResponse(SUCCESS);
            user.setLoginPassword("");
        } else {
            logger.info("Password did not match for userId = [{}].", loginRequest.getLoginId());
            loginResponse.setLoginResponse(INCORRECT_PASSWORD);
            user.setLoginPassword("");
        }

        return loginResponse;

    }

    @Override
    public List<User> getUsersByRoleCategory(String roleCategory) {
        List<User> users = userRepository.getUsersByRoleCategory(roleCategory, ACTIVE);
        for (User user : users) {
            user.setLoginPassword("");
        }
        return users;
    }

    private boolean isPasswordCorrect(String uiPassword, String dbPassword) {
        return uiPassword.equals(dbPassword);
    }


}
