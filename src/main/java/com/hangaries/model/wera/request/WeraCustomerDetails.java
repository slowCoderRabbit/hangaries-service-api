package com.hangaries.model.wera.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class WeraCustomerDetails {

    private String name;
    private String phone_number;
    private String email;
    private String address;
    private String delivery_area;
    private String address_instructions;

}
