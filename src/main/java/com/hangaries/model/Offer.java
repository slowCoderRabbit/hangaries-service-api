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
@Table(name = "OFFERS_MASTER")
public class Offer {

    @Id
    @Column(name = "offer_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long offerId;

    @Column(name = "offer_code")
    private String offerCode;

    @Column(name = "offer_description")
    private String offerDescription;

    @Column(name = "offer_type")
    private String offerType;

    @Column(name = "day_sunday")
    private String daySunday;

    @Column(name = "day_monday")
    private String dayMonday;

    @Column(name = "day_tuesday")
    private String dayTuesday;

    @Column(name = "day_wednesday")
    private String dayWednesday;

    @Column(name = "day_thursday")
    private String dayThursday;

    @Column(name = "day_friday")
    private String dayFriday;

    @Column(name = "day_saturday")
    private String daySaturday;

    @Column(name = "offer_applicability")
    private String offerApplicability;

    @Column(name = "offer_rule_logic")
    private String offerRuleLogic;

    @Column(name = "offer_price")
    private Float offerPrice;

    @Column(name = "offer_status")
    private String offerStatus;

    @Column(name = "Created_by")
    private String createdBy = SYSTEM;

    @Column(name = "Created_date")
    private Date createdDate = new Date();

    @Column(name = "Updated_by")
    private String updatedBy = SYSTEM;

    @Column(name = "Updated_date")
    private Date updatedDate = new Date();


}