package com.hangaries.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.hangaries.config.HangariesConstants.SYSTEM;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ORDER_MASTER")
public class Order {

    @Id
    @GeneratedValue
    private long id;

    @Column(name = "order_id")
    private @NotBlank String orderId;

    @Column(name = "restaurant_id")
    private @NotBlank String restaurantId;

    @Column(name = "store_Id")
    private @NotBlank String storeId;

    @Column(name = "order_source")
    private @NotBlank String orderSource;

    @Column(name = "customer_id")
    private @NotNull Integer customer_id;

    @Column(name = "order_received_date_time")
    private @NotNull Date orderReceivedDateTime;

    @Column(name = "order_delivery_type")
    private @NotBlank String orderDeliveryType;

    @Column(name = "store_table_id")
    private @NotBlank String storeTableId;

    @Column(name = "order_status")
    private @NotBlank String orderStatus;

    @Column(name = "tax_rule_id")
    private @NotNull Integer taxRuleId;

    @Column(name = "total_price")
    private @NotNull Float totalPrice;

    @Column(name = "cgst_caluclated_value")
    private Float cgstCaluclatedValue;

    @Column(name = "sgst_calculated_value")
    private Float sgstCalculatedValue;

    @Column(name = "overall_price_with_tax")
    private Float overallPriceWithTax;

    @Column(name = "Created_by")
    private String createdBy = SYSTEM;

    @Column(name = "Created_date")
    private Date createdDate = new Date();

    @Column(name = "Updated_by")
    private String updatedBy = SYSTEM;

    @Column(name = "Updated_date")
    private Date updatedDate = new Date();


}
