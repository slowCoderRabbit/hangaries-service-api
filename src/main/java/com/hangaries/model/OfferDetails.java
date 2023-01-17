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
@Table(name = "OFFER_DETAILS")
public class OfferDetails {

    @Id
    @Column(name = "offer_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offerDetailId;

    @Column(name = "offer_id")
    private Long offerId;

    @Column(name = "offer_rule_id")
    private Long offerRuleId;

    @Column(name = "rule_operator")
    private String ruleOperator;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "dish_type")
    private String dishType;

    @Column(name = "product_size")
    private String productSize;

    @Column(name = "product_operator")
    private String productOperator;

    @Column(name = "rule_status")
    private String ruleStatus;

    @Column(name = "Created_by")
    private String createdBy = SYSTEM;

    @Column(name = "Created_date")
    private Date createdDate = new Date();

    @Column(name = "Updated_by")
    private String updatedBy = SYSTEM;

    @Column(name = "Updated_date")
    private Date updatedDate = new Date();


}
