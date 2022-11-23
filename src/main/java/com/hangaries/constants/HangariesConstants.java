package com.hangaries.constants;

public class HangariesConstants {

    public static final String STATUS_N = "N";
    public static final String STATUS_Y = "Y";
    public static final String FOOD_READY = "FOOD READY";
    public static final String WERA = "WERA";
    public static final String ONLINE = "ONLINE";
    public static final String NA = "NA";
    public static final String SUBMITTED = "SUBMITTED";
    public static final String RESTAURANT_ID = "R001";
    public static final String WERA_ACCEPT_ORDER_FLAG = "WERA_ACCEPT_ORDER_FLAG";
    public static final String WD = "WD";
    public static final String INACTIVE = "INACTIVE";
    public static final String ORDER_MENU_INGREDIENT_ADDRESS_VIEW_SQL = "SELECT a.order_id order_id, b.id, a.restaurant_id, \n" +
            "       g.restaurant_name, h.resturant_name as store_name, a.store_Id, \n" +
            "       a.order_source order_source,\n" +
            "       a.customer_id, \n" +
            "       CONCAT(IFNULL(e.customer_first_name,''),' ',\n" +
            "              IFNULL(e.customer_middle_name,''),' ',\n" +
            "              IFNULL(e.customer_last_name,'')) AS customer_name,\n" +
            "        a.order_received_date_time, \n" +
            "       ifnull(CONCAT(a.order_delivery_type,' (', if(a.order_source='Z','Zomato',if(a.order_source='S','Swiggy','')), ' : ', external_order_id,')'), a.order_delivery_type) order_delivery_type, \n" +
            "       a.store_table_id, a.order_status, a.payment_status, a.payment_mode, a.tax_rule_id, a.total_price, \n" +
            "       a.delivery_charges, a.delivery_user_id, a.customer_address_id, a.cgst_calculated_value, a.sgst_calculated_value,\n" +
            "       a.overall_price_with_tax, a.created_by, a.created_date, a.updated_by, a.updated_date, \n" +
            "       c.product_id, if(c.product_size <> \"Regular\",concat(c.dish_type,\" (\",c.product_size,\")\"), c.dish_type) as dish_type, \n" +
            "       c.kds_routing_name, b.sub_product_Id, d.ingredient_type, b.quantity, b.price, b.order_detail_status, b.remarks, e.mobile_number,\n" +
            "       concat(f.address_1,\", \",f.address_2, \", \", f.landmark, \", \", f.city, \", \", f.state, \" - \", f.zip_code) address,\n" +
            "       a.food_packaged_flag, a.payment_txn_reference, a.coupon_code, a.discount_percentage, b.food_packaged_flag order_detail_food_packaged_flag,\n" +
            "       a.discount_amount, a.packaging_charges\n" +
            "  FROM RESTAURANT_MASTER g, STORE_MASTER h, MENU_MASTER c, MENU_INGREDIENT_MASTER d, \n" +
            "       CUSTOMER_MASTER e, CUSTOMER_ADDRESS_DETAILS f, ORDER_MASTER a, ORDER_DETAILS b\n" +
            " WHERE a.order_id = b.order_id\n" +
            "   AND c.product_id = b.product_id\n" +
            "   AND a.restaurant_id = g.restaurant_id\n" +
            "   AND a.restaurant_id = h.restaurant_id\n" +
            "   AND a.store_Id = h.store_id\n" +
            "   AND a.restaurant_id = c.restaurant_id\n" +
            "   AND a.store_Id = c.store_id\n" +
            "   AND a.restaurant_id = d.restaurant_id\n" +
            "   AND a.store_Id = d.store_Id\n" +
            "   AND b.sub_product_id = d.sub_product_id\n" +
            "   AND e.id = a.customer_id\n" +
            "   AND f.id = a.customer_address_id\n";
    public static String SYSTEM = "SYSTEM";
    public static String ACCEPTED = "ACCEPTED";
    public static String ACTIVE = "ACTIVE";
    public static String Y = "Y";
    public static String INCORRECT_ID = "INCORRECT_ID";
    public static String SUCCESS = "SUCCESS";
    public static String INCORRECT_PASSWORD = "INCORRECT_PASSWORD";
    public static String ERROR = "ERROR";
    public static String ALL = "ALL";
    public static String NO_RECORD_FOUND = "NO_RECORD_FOUND";
    public static String DELETED = "DELETED";
    public static String DELIVERED = "DELIVERED";
    public static String PAID = "PAID";
    public static String BLANK_OR_INCORRECT_PASSWORD = "BLANK_OR_INCORRECT_PASSWORD";
    public static String BLANK = "";
    public static String NAA = "NAA";
    public static String ORDER_SOURCE = "ORDER_SOURCE";
    public static String ADMIN = "ADMIN";
    public static String PROCESSING = "PROCESSING";


}
