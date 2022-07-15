package com.hangaries.model.dto;

import com.hangaries.model.keys.OrderMenuIngredientAddressId;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

import static com.hangaries.constants.HangariesConstants.SYSTEM;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@IdClass(OrderMenuIngredientAddressId.class)
@Table(name = "vOrderMenuIngredientAddress")
public class OrderMenuIngredientAddressDTO implements Serializable {

    @Id
    @Column(name = "order_id")
    private @NotBlank String orderId;

    @Column(name = "restaurant_id")
    private @NotBlank String restaurantId;

    @Column(name = "restaurant_name")
    private String restaurantName;

    @Column(name = "store_Id")
    private @NotBlank String storeId;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "order_source")
    private @NotBlank String orderSource;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "order_received_date_time")
    private Date orderReceivedDateTime;

    @Column(name = "order_delivery_type")
    private @NotBlank String orderDeliveryType;

    @Column(name = "store_table_id")
    private String storeTableId;

    @Column(name = "order_status")
    private @NotBlank String orderStatus;

    @Column(name = "payment_status")
    private @NotBlank String paymentStatus;

    @Column(name = "payment_mode")
    private @NotBlank String paymentMode;

    @Column(name = "tax_rule_id")
    private @NotNull Integer taxRuleId;

    @Column(name = "total_price")
    private @NotNull Float totalPrice;

    @Column(name = "cgst_calculated_value")
    private Float cgstCalculatedValue;

    @Column(name = "sgst_calculated_value")
    private Float sgstCalculatedValue;

    @Column(name = "delivery_charges")
    private Float deliveryCharges;

    @Column(name = "overall_price_with_tax")
    private Float overallPriceWithTax;

    @Column(name = "customer_address_id")
    private Long customerAddressId;

    @Column(name = "food_packaged_flag")
    private String foodPackagedFlag;

    @Column(name = "Created_by")
    private String createdBy = SYSTEM;

    @Column(name = "Created_date")
    private Date createdDate = new Date();

    @Column(name = "Updated_by")
    private String updatedBy = SYSTEM;

    @Column(name = "Updated_date")
    private Date updatedDate = new Date();

    @Id
    @Column(name = "product_id")
    private String productId;

    @Column(name = "dish_type")
    private String dishType;

    @Column(name = "ingredient_type")
    private String ingredientType;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Id
    @Column(name = "sub_product_Id")
    private String subProductId;

    @Column(name = "quantity")
    private @NotNull Integer quantity;

    @Column(name = "price")
    private Float price;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "address")
    private String address;

    @Column(name = "order_detail_status")
    private String orderDetailStatus;

    @Column(name = "delivery_user_id")
    private String deliveryUserId;

    @Column(name = "kds_routing_name")
    private String kdsRoutingName;


}
