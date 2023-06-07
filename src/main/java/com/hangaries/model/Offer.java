package com.hangaries.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

import static com.hangaries.constants.HangariesConstants.SYSTEM;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "OFFERS_MASTER")
public class Offer {

    @Id
    @Column(name = "offer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offerId;

    @Column(name = "restaurant_id")
    private @NotBlank String restaurantId;

    @Column(name = "store_Id")
    private @NotBlank String storeId;

    @Column(name = "offer_function_type")
    private String offerFunctionType;

    @Column(name = "offer_code")
    private String offerCode;

    @Column(name = "offer_description")
    private String offerDescription;

//    @Column(name = "offer_type")
//    private String offerType;

    @Column(name = "day_sunday")
    private String sunday;

    @Column(name = "day_monday")
    private String monday;

    @Column(name = "day_tuesday")
    private String tuesday;

    @Column(name = "day_wednesday")
    private String wednesday;

    @Column(name = "day_thursday")
    private String thursday;

    @Column(name = "day_friday")
    private String friday;

    @Column(name = "day_saturday")
    private String saturday;

    @Column(name = "offer_applicability")
    private String offerApplicability;

//    @Column(name = "offer_rule_logic")
//    private String offerRuleLogic;

    @Column(name = "offer_price")
    private Float offerPrice;

    @Column(name = "offer_status")
    private String offerStatus;

    @Column(name = "offer_criteria")
    private String offerCriteria;

    @Column(name = "offer_section")
    private String offerSection;

    @Column(name = "offer_dish")
    private String offerDish;

    @Column(name = "offer_product_list")
    private String offerProductList;

    @Column(name = "offer_discount")
    private Float offerDiscount;

    @Column(name = "Created_by")
    private String createdBy = SYSTEM;

    @Column(name = "Created_date")
    private Date createdDate = new Date();

    @Column(name = "Updated_by")
    private String updatedBy = SYSTEM;

    @Column(name = "Updated_date")
    private Date updatedDate = new Date();


}
