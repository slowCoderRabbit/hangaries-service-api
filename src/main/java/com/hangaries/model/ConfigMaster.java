package com.hangaries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

import static com.hangaries.constants.HangariesConstants.SYSTEM;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@IdClass(ConfigMasterKey.class)
@Table(name = "CONFIG_MASTER")
public class ConfigMaster {

    @Id
    @Column(name = "restaurant_id")
    private String restaurantId;

    @Id
    @Column(name = "store_id")
    private String storeId;

    @Id
    @Column(name = "config_criteria")
    private String configCriteria;

    @Id
    @Column(name = "config_criteria_value")
    private String configCriteriaValue;

    @Column(name = "config_criteria_description")
    private String configCriteriaDesc;

    @Column(name = "config_parameter")
    private String configParameter;

    @Column(name = "config_value")
    private String configValue;

    @Column(name = "Created_by")
    private String createdBy = SYSTEM;

    @Column(name = "created_date")
    private Date createdDate = new Date();

    @Column(name = "updated_by")
    private String updatedBy = SYSTEM;

    @Column(name = "updated_date")
    private Date updatedDate = new Date();

}
