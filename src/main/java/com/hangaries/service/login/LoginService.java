package com.hangaries.service.login;

import com.hangaries.model.LoginRequest;
import com.hangaries.model.LoginResponse;
import com.hangaries.model.User;

import java.util.List;

public interface LoginService {
    LoginResponse employeeLogin(LoginRequest loginRequest);

    List<User> getUsersByRoleCategory(String roleCategory);

    User addEmployee(User user);

    List<User> getAllEmployee(String restaurantId, String storeId, String status);

    User getEmployeeByLoginId(String loginId);

    LoginResponse updateEmployeePasswordByLoginId(String loginId, String loginPassword);
}
