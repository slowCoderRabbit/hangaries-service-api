package com.hangaries.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.hangaries.constants.HangariesConstants.SYSTEM;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "DELIVERY_CONFIG")
public class DeliveryConfig {

    @Column(name = "delivery_rule_Id")
    @Id
    private @NotNull Integer delivery_rule_Id;

    @Column(name = "restaurant_id")
    private @NotBlank String restaurantId;

    @Column(name = "store_Id")
    private @NotBlank String storeId;

    @Column(name = "criteria")
    private @NotBlank String criteria;

    @Column(name = "min_distance_kms")
    private Integer minDistanceKms;

    @Column(name = "max_distance_kms")
    private Integer maxDistanceKms;

    @Column(name = "min_amount")
    private Integer minAmount;

    @Column(name = "max_amount")
    private Integer maxAmount;

    @Column(name = "delivery_time_mins")
    private Integer deliveryTimeMins;

    @Column(name = "delivery_fee_currency")
    private @NotBlank String deliveryFeeCurrency;

    @Column(name = "delivery_fee")
    private @NotNull Float deliveryFee;

    @Column(name = "rule_status")
    private @NotBlank String ruleStatus;

    @Column(name = "effective_start_date")
    private Date effectiveStartDate;

    @Column(name = "effective_end_date")
    private Date effectiveEndDate;

    @Column(name = "default_criteria_flag")
    private @NotBlank String defaultCriteriaFlag;

    @Column(name = "Created_by")
    private String createdBy = SYSTEM;

    @Column(name = "Created_date")
    private Date createdDate = new Date();

    @Column(name = "Updated_by")
    private String updatedBy = SYSTEM;

    @Column(name = "Updated_date")
    private Date updatedDate = new Date();


}
