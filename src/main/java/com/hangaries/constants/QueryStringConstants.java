package com.hangaries.constants;

public class QueryStringConstants {

    public static final String RECIPES_WITH_NAME = "SELECT im.item_name as itemName, rm.* FROM ITEM_MASTER im, RECIPE_MASTER rm where im.item_id = rm.item_id";

    public static final String ACTIVE_RECIPES_WITH_NAME = "SELECT im.item_name as itemName, rm.* FROM ITEM_MASTER im, RECIPE_MASTER rm where rm.item_status = 'ACTIVE' and im.item_id = rm.item_id ";

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
}
