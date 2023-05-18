package com.hangaries.model;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ConfigMasterKey implements Serializable {

    private String restaurantId;
    private String storeId;
    private String configCriteria;
    private String configCriteriaValue;


}
