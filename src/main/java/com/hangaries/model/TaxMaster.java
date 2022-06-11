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
import java.util.Date;

import static com.hangaries.constants.HangariesConstants.SYSTEM;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "TAX_MASTER")
public class TaxMaster {

    @Id
    @Column(name = "tax_rule_id")
    private @NotBlank String taxRuleId;

    @Column(name = "restaurant_id")
    private String restaurantId;

    @Column(name = "store_Id")
    private String storeId;

    @Column(name = "tax_category")
    private String taxCategory;

    @Column(name = "tax_percentage")
    private float taxPercentage;

    @Column(name = "tax_rule_status")
    private String taxRuleStatus;

    @Column(name = "effective_start_date")
    private Date effectiveStartDate;

    @Column(name = "effective_end_date")
    private Date effectiveEndDate;

    @Column(name = "Created_by")
    private String createdBy = SYSTEM;

    @Column(name = "created_date")
    private Date createdDate = new Date();

    @Column(name = "updated_by")
    private String updatedBy = SYSTEM;

    @Column(name = "updated_date")
    private Date updatedDate = new Date();
}
