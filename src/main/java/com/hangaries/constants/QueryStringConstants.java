package com.hangaries.constants;

public class QueryStringConstants {

    public static final String RECIPES_WITH_NAME = "SELECT im.item_name as itemName, rm.* FROM ITEM_MASTER im, RECIPE_MASTER rm where im.item_id = rm.item_id and rm.restaurant_id =:restaurantId AND rm.store_id =:storeId";

    public static final String ACTIVE_RECIPES_WITH_NAME = "SELECT im.item_name as itemName, rm.* FROM ITEM_MASTER im, RECIPE_MASTER rm where rm.item_status = 'ACTIVE' and im.item_id = rm.item_id and rm.restaurant_id =:restaurantId AND rm.store_id =:storeId";

    public static final String ORDER_MENU_INGREDIENT_ADDRESS_VIEW_SQL = "SELECT a.order_id order_id, b.id, a.restaurant_id, \n" +
            "       g.restaurant_name, h.resturant_name as store_name, a.store_Id, \n" +
            "       a.order_source order_source,\n" +
            "       a.customer_id, \n" +
            "       a.customer_name AS customer_name,\n" +
            "       a.order_received_date_time, \n" +
            "       ifnull(CONCAT(a.order_delivery_type,' (', if(a.order_source='Z','Zomato',if(a.order_source='S','Swiggy','')), ' : ', external_order_id,')'), a.order_delivery_type) order_delivery_type, \n" +
            "       a.store_table_id, a.order_status, a.payment_status, a.payment_mode, a.tax_rule_id, a.total_price, \n" +
            "       a.delivery_charges, a.delivery_user_id, a.customer_address_id, a.cgst_calculated_value, a.sgst_calculated_value,\n" +
            "       a.overall_price_with_tax, a.created_by, a.created_date, a.updated_by, a.updated_date, \n" +
            "       c.product_id, if(c.product_size <> \"Regular\",concat(c.dish_type,\" (\",c.product_size,\")\"), c.dish_type) as dish_type, \n" +
            "       c.kds_routing_name, b.sub_product_Id, d.ingredient_type, b.quantity, b.price, b.order_detail_status, b.remarks, a.customer_mobile_number AS mobile_number,\n" +
            "       a.customer_address AS address,\n" +
            "       a.food_packaged_flag, a.payment_txn_reference, a.coupon_code, a.discount_percentage, b.food_packaged_flag order_detail_food_packaged_flag,\n" +
            "       a.discount_amount, a.packaging_charges\n" +
            "  FROM RESTAURANT_MASTER g, STORE_MASTER h, MENU_MASTER c, MENU_INGREDIENT_MASTER d, \n" +
            "       ORDER_MASTER a, ORDER_DETAILS b\n" +
            " WHERE a.order_id = b.order_id\n" +
            "   AND c.product_id = b.product_id\n" +
            "   AND a.restaurant_id = g.restaurant_id\n" +
            "   AND a.restaurant_id = h.restaurant_id\n" +
            "   AND a.store_Id = h.store_id\n" +
            "   AND a.restaurant_id = c.restaurant_id\n" +
            "   AND a.store_Id = c.store_id\n" +
            "   AND a.restaurant_id = d.restaurant_id\n" +
            "   AND a.store_Id = d.store_Id\n" +
            "   AND b.sub_product_id = d.sub_product_id";

    public static final String PURCHASE_ORDER_WITH_NAME_STATUS_EXCLUDING = "select rm.restaurant_name,stm.resturant_name as store_name ,supm.supplier_name, po.* from PURCHASE_ORDER po, RESTAURANT_MASTER rm, STORE_MASTER stm, \n" +
            "SUPPLIER_MASTER supm where po.restaurant_id=:restaurantId and po.store_Id=:storeId and purchase_order_status <>:status and po.restaurant_id=rm.restaurant_id\n" +
            "and po.restaurant_id=stm.restaurant_id and po.store_id=stm.store_Id and po.supplier_id=supm.supplier_id order by purchase_order_id";

    public static final String PURCHASE_ORDER_WITH_NAME_STATUS = "select rm.restaurant_name,stm.resturant_name as store_name ,supm.supplier_name, po.* from PURCHASE_ORDER po, RESTAURANT_MASTER rm, STORE_MASTER stm, \n" +
            "SUPPLIER_MASTER supm where po.restaurant_id=:restaurantId and po.store_Id=:storeId and purchase_order_status =:status and po.restaurant_id=rm.restaurant_id\n" +
            " and po.restaurant_id=stm.restaurant_id and po.store_id=stm.store_Id and po.supplier_id=supm.supplier_id order by purchase_order_id";

    public static final String PURCHASE_ORDER_ALL = "select rm.restaurant_name,stm.resturant_name as store_name ,supm.supplier_name, po.* from PURCHASE_ORDER po, RESTAURANT_MASTER rm, STORE_MASTER stm, \n" +
            "SUPPLIER_MASTER supm where po.restaurant_id=:restaurantId and po.store_Id=:storeId and po.restaurant_id=rm.restaurant_id and po.restaurant_id=stm.restaurant_id \n" +
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

    public static final String ITEM_CONSUMPTION_SUMMARY_RECIPE_SQL = "SELECT item_id, restaurant_id, store_id, item_name, item_category, business_date, store_name, sum(opng_qty) as opng_qty, sum(item_ordered) as item_ordered, sum(item_wasted) as item_wasted," +
            " sum(item_consumed) as item_consumed, sum(item_variance) as item_variance, sum(item_amount) as item_amount, sum(eod_qty) as eod_qty" +
            " FROM REPORT_RECIPE_ITEM_CONSUMPTION_SUMMARY WHERE restaurant_id =:restaurantId AND store_id =:storeId AND business_date between :fromDate and :toDate" +
            " GROUP BY item_id, restaurant_id, store_id, item_name, item_category, store_name, business_date";

    public static final String ITEM_CONSUMPTION_SUMMARY_NON_RECIPE_SQL = "SELECT item_id, restaurant_id, store_id, item_name, item_category, business_date, store_name, sum(opng_qty) as opng_qty, sum(item_ordered) as item_ordered, sum(item_wasted) as item_wasted," +
            " sum(item_consumed) as item_consumed, sum(item_variance) as item_variance, sum(item_amount) as item_amount, sum(eod_qty) as eod_qty" +
            " FROM REPORT_NRECIPE_ITEM_CONSUMPTION_SUMMARY WHERE restaurant_id =:restaurantId AND store_id =:storeId AND business_date between :fromDate and :toDate" +
            " GROUP BY item_id, restaurant_id, store_id, item_name, item_category, store_name, business_date";

    public static final String GET_ITEM_CONSUMPTION_SUMMARY_SQL = "SELECT d.id, d.item_id, d.item_name, d.item_category, d.item_sub_category, d.item_uom, d.restaurant_id, d.store_id, d.business_date,\n" +
            " round(ifnull(sum(po_opng_qty),0),2) poOpngQty, round(ifnull(sum(today_qty),0),2) poTodayQty,  \n" +
            "\t   round(ifnull(sum(wastage_qty),0),2) poWastageQty, round(ifnull(sum(wastage_qty),0),2) poWastageQty, (round(ifnull(sum(po_opng_qty),0),2) + round(ifnull(sum(today_qty),0),2) - round(ifnull(sum(wastage_qty),0),2)) poNetQty, \n" +
            "          ifnull(round(sum(item_consumed),2),0) itemCurrConsumptionQty, \n" +
            "          round(ifnull(sum(po_opng_qty),0),2) + round(ifnull(sum(today_qty),0),2) - round(ifnull(sum(wastage_qty),0),2) - round(ifnull(sum(item_consumed),0),2) + round(ifnull(sum(variance_qty),0),2) itemEodConsumptionQty,\n" +
            "\t   ifnull(round(sum(variance_qty),2),0) itemConsumptionVarianceQty, \n" +
            "\t   ifnull(round(sum(consumption_amt),2),0) itemConsumptionAmount, d.remarks, d.recon_status\n" +
            "FROM (SELECT q.id, p.item_id, p.item_name, p.item_uom, p.item_category, p.item_sub_category, q.restaurant_id, q.store_id,\n" +
            "                business_date, remarks, recon_status, \n" +
            "                sum(q.po_opng_qty) po_opng_qty, sum(q.item_consumption_variance_qty) variance_qty, sum(q.item_consumption_amount) consumption_amt\n" +
            "\t\tFROM ITEM_MASTER p, ITEM_CONSUMPTION_SUMMARY q\n" +
            "\t   WHERE q.restaurant_id = p.restaurant_id\n" +
            "\t\t AND p.store_id = 'ALL'\n" +
            "            AND p.item_id = q.item_id\n" +
            "            AND item_status = 'ACTIVE'\n" +
            "            group by q.id, p.item_id, p.item_name, p.item_uom, p.item_category, p.item_sub_category, q.restaurant_id, q.store_id,\n" +
            "                business_date, remarks, recon_status) d LEFT JOIN\n" +
            "\t(SELECT b.item_id, e.restaurant_id, e.store_id, round(sum(quantity*item_qty),2) item_consumed\n" +
            "\t   FROM RECIPE_MASTER b, ORDER_MASTER e, ORDER_DETAILS a\n" +
            "\t  WHERE b.restaurant_id = e.restaurant_id \n" +
            "\t\tAND e.order_id = a.order_id\n" +
            "\t\tAND ((b.product_id = a.product_id and a.sub_product_id = 'NAA') or (b.product_id = a.sub_product_id))\n" +
            "  group by b.item_id, e.restaurant_id, e.store_id) c ON d.restaurant_id = c.restaurant_id and d.store_id = c.store_id and d.item_id = c.item_id LEFT JOIN  \n" +
            "\t(SELECT p.item_id, p.restaurant_id, p.store_id, sum(p.purchase_qty) today_qty, sum(p.wastage_qty) wastage_qty, sum(p.net_qty) net_qty \n" +
            "          FROM PURCHASE_ORDER p, BUSINESS_DATE_MASTER q\n" +
            "         WHERE p.restaurant_id = q.restaurant_id \n" +
            "\t\tAND p.store_id = q.store_id \n" +
            "           AND p.purchase_order_status = 'RECEIVED' \n" +
            "           AND DATE(p.updated_date) = q.business_date\n" +
            "         GROUP BY p.item_id, p.restaurant_id, p.store_id) g ON d.restaurant_id = g.restaurant_id and d.store_id = g.store_id and d.item_id = g.item_id \n" +
            "  GROUP BY d.id, d.item_id, d.item_name, d.item_category, d.item_sub_category, d.item_uom, d.restaurant_id, d.store_id, d.business_date, remarks, recon_status; ";

}
