package com.hangaries.model;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class AppDetailsKey implements Serializable {

    private String appEnvironment;
    private String restaurantId;
    private String storeId;

}
