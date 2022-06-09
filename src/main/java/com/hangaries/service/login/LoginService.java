package com.hangaries.service.login;

import com.hangaries.model.LoginRequest;
import com.hangaries.model.LoginResponse;
import com.hangaries.model.User;

import java.util.List;

public interface LoginService {
    LoginResponse employeeLogin(LoginRequest loginRequest);

    List<User> getUsersByRoleCategory(String roleCategory);
}
