package com.hangaries.service.login.impl;

import com.hangaries.model.*;
import com.hangaries.repository.StoreRepository;
import com.hangaries.repository.UserLoginDetailsRepository;
import com.hangaries.repository.UserRepository;
import com.hangaries.service.login.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.hangaries.constants.HangariesConstants.*;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
    @Autowired
    UserRepository userRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    UserLoginDetailsRepository userLoginDetailsRepository;

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
            loginResponse.setRestaurantName(storeRepository.findByStoreId(loginResponse.getUser().getStoreId()).stream().findFirst().get().getResturantName());

            try {
                UserLoginDetails userLoginDetails = logUserLoginDetails(populateUserLoginDetails(loginRequest.getLoginId(), "", ""));
                loginResponse.setUserLoginDetailId(userLoginDetails.getId());
            } catch (Exception e) {
                logger.error("Exception while logUserLoginDetails {}", e);
            }
            user.setLoginPassword("");

        } else {
            logger.info("Password did not match for userId = [{}].", loginRequest.getLoginId());
            loginResponse.setLoginResponse(INCORRECT_PASSWORD);
            user.setLoginPassword("");
        }


        return loginResponse;
    }

    private UserLoginDetails logUserLoginDetails(UserLoginDetails detail) {
        return userLoginDetailsRepository.save(detail);
    }

    private UserLoginDetails populateUserLoginDetails(String userLoginId, String restaurantId, String storeId) {
        UserLoginDetails userLoginDetails = getUserLoginDetailsCommon(userLoginId, restaurantId, storeId);
        userLoginDetails.setLoginTime(new Date());
        userLoginDetails.setLoginStatus(ACTIVE);
        return userLoginDetails;

    }

    private UserLoginDetails getUserLoginDetailsCommon(String userLoginId, String restaurantId, String storeId) {
        UserLoginDetails userLoginDetails = new UserLoginDetails();
        userLoginDetails.setUserLoginId(userLoginId);
        userLoginDetails.setRestaurantId(restaurantId);
        userLoginDetails.setStoreId(storeId);
        return userLoginDetails;
    }

    @Override
    public List<User> getUsersByRoleCategory(String roleCategory) {
        List<User> users = null;

        if (roleCategory.equalsIgnoreCase(ALL)) {
            users = userRepository.getAllUsersByRoleCategory(ACTIVE);
        } else {
            users = userRepository.getUsersByRoleCategory(roleCategory, ACTIVE);
        }


        for (User user : users) {
            user.setLoginPassword("");
        }
        return users;
    }

    @Override
    public User addEmployee(User user) {
        user.setLoginPassword("default");
        userRepository.save(user);
        return userRepository.getEmployeeByLoginId(user.getLoginId());
    }

    @Override
    public List<User> getAllEmployee(String restaurantId, String storeId, String status) {
        return userRepository.getAllEmployee(restaurantId, storeId, status);
    }

    @Override
    public User getEmployeeByLoginId(String loginId) {
        return userRepository.getEmployeeByLoginId(loginId);
    }

    @Override
    public LoginResponse updateEmployeePasswordByLoginId(String loginId, String loginPassword) {
        User user = userRepository.findByLoginId(loginId);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUser(user);

        if (null == user) {
            logger.info("No user found for userId = [{}] in DB.", loginId);
            loginResponse.setLoginResponse(INCORRECT_ID);
        } else if (StringUtils.isBlank(loginPassword)) {
            logger.info("Empty password for userId = [{}].", loginId);
            loginResponse.setLoginResponse(BLANK_OR_INCORRECT_PASSWORD);
            user.setLoginPassword("");
        } else {
            logger.info("Updating Password for userId = [{}].", loginId);
            userRepository.updateEmployeePasswordByLoginId(loginId, loginPassword);
            loginResponse.setLoginResponse(SUCCESS);
            user.setLoginPassword("");
        }

        return loginResponse;
    }

    private boolean isPasswordCorrect(String uiPassword, String dbPassword) {
        return uiPassword.equals(dbPassword);
    }


    public String employeeLogout(LogoutRequest logoutRequest) {
        UserLoginDetails result = userLoginDetailsRepository.findByUserLoginIdAndId(logoutRequest.getLoginId(), logoutRequest.getUserLoginDetailId());
        if (null == result) {
            return "No active login for user found!";
        }
        result.setLogoutTime(new Date());
        result.setLoginStatus(INACTIVE);
        userLoginDetailsRepository.save(result);
        return SUCCESS;

    }
}
