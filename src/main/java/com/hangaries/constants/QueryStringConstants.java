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

    public static final String PURCHASE_ORDER_WITH_NAME_STATUS_EXCLUDING = "select rm.restaurant_name,stm.resturant_name as store_name ,supm.supplier_name, po.* from PURCHASE_ORDER po, RESTAURANT_MASTER rm, STORE_MASTER stm, \n" +
            "SUPPLIER_MASTER supm where purchase_order_status <>:status and po.restaurant_id=rm.restaurant_id\n" +
            "and po.store_id=stm.store_Id and po.supplier_id=supm.supplier_id order by purchase_order_id";

    public static final String PURCHASE_ORDER_WITH_NAME_STATUS = "select rm.restaurant_name,stm.resturant_name as store_name ,supm.supplier_name, po.* from PURCHASE_ORDER po, RESTAURANT_MASTER rm, STORE_MASTER stm, \n" +
            "SUPPLIER_MASTER supm where purchase_order_status =:status and po.restaurant_id=rm.restaurant_id\n" +
            "and po.store_id=stm.store_Id and po.supplier_id=supm.supplier_id order by purchase_order_id";

    public static final String PURCHASE_ORDER_ALL = "select rm.restaurant_name,stm.resturant_name as store_name ,supm.supplier_name, po.* from PURCHASE_ORDER po, RESTAURANT_MASTER rm, STORE_MASTER stm, \n" +
            "SUPPLIER_MASTER supm where po.restaurant_id=rm.restaurant_id\n" +
            "and po.store_id=stm.store_Id and po.supplier_id=supm.supplier_id order by purchase_order_id";

    public static final String CUSTOMER_REPORT_SQL = "SELECT a.mobile_number, \n" +
            "\t   concat(ifnull(a.customer_first_name,' '), \" \", ifnull(a.customer_middle_name,' '), \" \", ifnull(a.customer_last_name,' ')) `name`,\n" +
            "       b.customer_address_type, concat(b.address_1, \",\", address_2, \",\", landmark, \",\", state, \",\", city, \" - \", zip_code) `address`,\n" +
            "       a.email_id, if(b.active = 'N', 'Address Type Not Active', 'Address Type is Active') `address_type_status`, date(b.created_date) `data_created`\n" +
            "  FROM CUSTOMER_MASTER a, CUSTOMER_ADDRESS_DETAILS b\n" +
            " WHERE a.mobile_number = b.mobile_number\n" +
            "   AND length(a.mobile_number) between 10 and 13\n" +
            " order by date(b.created_date) desc, a.mobile_number";

    public static final String ITEM_CONSUMPTION_SUMMARY_SQL = "SELECT item_id, restaurant_id, store_id, item_name, item_category, store_name, sum(opng_qty) as opng_qty, sum(item_ordered) as item_ordered, sum(item_wasted) as item_wasted," +
            " sum(item_consumed) as item_consumed, sum(item_variance) as item_variance, sum(item_amount) as item_amount, sum(eod_qty) as eod_qty" +
            " FROM REPORT_ITEM_CONSUMPTION_SUMMARY WHERE restaurant_id =:restaurantId AND store_id =:storeId AND business_date between :fromDate and :toDate" +
            " GROUP BY item_id, restaurant_id, store_id, item_name, item_category, store_name";
}
