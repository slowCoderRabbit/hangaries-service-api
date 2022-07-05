package com.hangaries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginResponse {

    private String loginResponse;
    private String restaurantName;
    private User user;


}
