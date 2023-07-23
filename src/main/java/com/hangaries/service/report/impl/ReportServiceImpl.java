package com.hangaries.service.report.impl;

import com.hangaries.model.*;
import com.hangaries.model.dto.OrderMenuIngredientAddressDTO;
import com.hangaries.model.vo.OrderDetailsVO;
import com.hangaries.model.vo.OrderVO;
import com.hangaries.repository.ReportRepository;
import com.hangaries.service.order.impl.OrderServiceImpl;
import com.hangaries.service.report.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

import static com.hangaries.constants.HangariesConstants.ALL;
import static com.hangaries.constants.QueryStringConstants.CUSTOMER_REPORT_SQL;
import static com.hangaries.constants.QueryStringConstants.ORDER_MENU_INGREDIENT_ADDRESS_VIEW_SQL;

@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    private static final List<String> sqlReportList = Arrays.asList("CANCELLED_REPORT", "CUSTOMER_REPORT");

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ReportResult getReports(Report report) throws ParseException {

        logger.info("getReports.....!!!!");
        ReportResult reportResult = new ReportResult();

        try {
            if (sqlReportList.contains(report.getReportName())) {
                if (report.getReportName().equals("CANCELLED_REPORT")) {
                    reportResult.setReportCancelledOrderData(getCancelledOrderReportData());
                } else if (report.getReportName().equals("CUSTOMER_REPORT")) {
                    reportResult.setReportCustomerData(getCustomerReportData());
                }
            } else {
                logger.info("Refreshing reports table.....");
                String result = reportRepository.getReports(report.getRestaurantId(), report.getStoreId(), report.getFromDate(), report.getToDate(), report.getReportName(), report.getUserLoginId());
                logger.info("Result of reports table refresh {}", result);

                StringBuilder queryBuilder = new StringBuilder(" where restaurant_id ='");
                queryBuilder.append(report.getRestaurantId());
                queryBuilder.append("' ");
                if (!report.getStoreId().equals(ALL)) {
                    queryBuilder.append("and store_id ='");
                    queryBuilder.append(report.getStoreId());
                    queryBuilder.append("' ");
                }
                queryBuilder.append(" and user_login_id = '");
                queryBuilder.append(report.getUserLoginId());
                queryBuilder.append("' ");

                if (report.getReportName().equals("SALES_SUMMARY_BY_DATE_LIST")) {

                    StringBuilder queryDateBuilder = new StringBuilder(" and order_date between '");
                    queryDateBuilder.append(report.getFromDate());
                    queryDateBuilder.append("' and '");
                    queryDateBuilder.append(report.getToDate());
                    queryDateBuilder.append("' ");
                    String query = "select * from REPORT_SALES_SUMMARY_BY_DATE" + queryBuilder + queryDateBuilder;
                    logger.info("SALES_SUMMARY_BY_DATE_LIST SQL = [{}]", query);
                    List<RSSDate> rssDate = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(RSSDate.class));
                    if (null != rssDate && !rssDate.isEmpty()) {
                        reportResult.setSalesSummeryByDateList(rssDate);
                    }
                } else if (report.getReportName().equals("SALES_SUMMARY_BY_ORDER_SOURCE")) {
                    String query = "select * from REPORT_SALES_SUMMARY_BY_ORDER_SOURCE" + queryBuilder;
                    logger.info("REPORT_SALES_SUMMARY_BY_ORDER_SOURCE SQL = [{}]", query);
                    List<RSSOrderSource> rssOrderSources = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(RSSOrderSource.class));
                    if (null != rssOrderSources && !rssOrderSources.isEmpty()) {
                        reportResult.setSalesSummeryByOrderSource(rssOrderSources);
                    }

                } else if (report.getReportName().equals("SALES_SUMMARY_BY_PAYMENT_MODE")) {
                    String query = "select * from REPORT_SALES_SUMMARY_BY_PAYMENT_MODE" + queryBuilder;
                    logger.info("REPORT_SALES_SUMMARY_BY_PAYMENT_MODE SQL = [{}]", query);
                    List<RSSPaymentMode> rSSPaymentMode = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(RSSPaymentMode.class));
                    if (null != rSSPaymentMode && !rSSPaymentMode.isEmpty()) {
                        reportResult.setSalesSummeryByPaymentMode(rSSPaymentMode);
                    }
                } else if (report.getReportName().equals("SALES_BY_DISH_ITEM")) {
//                    String query = "select * from REPORT_DISH_CONSUMPTION_SUMMARY" + queryBuilder;
                    StringBuilder queryDateBuilder = getQueryWithRestoStoreAndUserId("REPORT_DISH_CONSUMPTION_SUMMARY", report);
                    logger.info("REPORT_DISH_CONSUMPTION_SUMMARY SQL = [{}]", queryDateBuilder);
                    List<ReportDishConsumptionSummary> rDCSummary = jdbcTemplate.query(queryDateBuilder.toString(), BeanPropertyRowMapper.newInstance(ReportDishConsumptionSummary.class));
                    if (null != rDCSummary && !rDCSummary.isEmpty()) {
                        reportResult.setReportDishConsumptionSummary(rDCSummary);
                    }

                } else if (report.getReportName().equals("CASH_SALES_REPORT")) {
                    String query = "select * from REPORT_CASHIER_SUMMARY" + queryBuilder + " order by cashier_name,store_name,type_of_data,category";
                    logger.info("REPORT_CASHIER_SUMMARY SQL = [{}]", query);
                    List<ReportCashierSummery> results = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ReportCashierSummery.class));
                    logger.info("ReportCashierSummery records from DB = {}", results.size());
                    List<ReportCashierSummeryResponse> response = transformData(groupCollectionByCashier(results));
                    logger.info("ReportCashierSummery post data transformation records = {}", response.size());
                    if (null != response && !response.isEmpty()) {
                        reportResult.setReportCashierSummery(response);
                    }
                } else if (report.getReportName().equals("ORDER_REPORT")) {
                    StringBuilder queryDateBuilder = new StringBuilder(" and order_received_date_time between '");
                    queryDateBuilder.append(report.getFromDate());
                    queryDateBuilder.append("' and '");
                    queryDateBuilder.append(report.getToDate());
                    queryDateBuilder.append("' ");
                    String query = "select * from REPORT_ORDER_DATA" + queryBuilder + queryDateBuilder + " order by order_id";
                    logger.info("ORDER_REPORT SQL = [{}]", query);
                    List<ReportOrderData> results = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ReportOrderData.class));
                    logger.info("ReportOrderData records from DB = {}", results.size());
                    Map<String, List<ReportOrderData>> orderMap = consolidateResponseByOrderId(results);
                    logger.info("ReportOrderData post data transformation records = {}", orderMap.size());
                    List<OrderVO> orderList = convertMapList(orderMap);
                    logger.info("ReportOrderData :: Final order list created of size = [{}].", orderList.size());
                    if (null != orderList && !orderList.isEmpty()) {
                        reportResult.setReportOrderData(orderList);
                    }
                } else if (report.getReportName().equals("DASHBOARD_SUMMARY")) {

                    StringBuilder queryDateBuilder = getQueryWithRestoStoreAndUserId("REPORT_DASHBOARD_SUMMARY", report);
//                    String query = "select * from REPORT_DASHBOARD_SUMMARY where user_login_id = '" + report.getUserLoginId() + "'";
//                    logger.info("REPORT_DASHBOARD_SUMMARY SQL = [{}]", query);
                    logger.info("REPORT_DASHBOARD_SUMMARY SQL = [{}]", queryDateBuilder);
                    List<RDSReport> rssOrderSources = jdbcTemplate.query(queryDateBuilder.toString(), BeanPropertyRowMapper.newInstance(RDSReport.class));
//                    query = "select * from REPORT_DASHBOARD_DETAILS where user_login_id = '";
                    queryDateBuilder = getQueryWithRestoStoreAndUserId("REPORT_DASHBOARD_DETAILS", report);
                    logger.info("REPORT_DASHBOARD_DETAILS SQL = [{}]", queryDateBuilder);
                    List<RDDReport> rddOrderSources = jdbcTemplate.query(queryDateBuilder.toString(), BeanPropertyRowMapper.newInstance(RDDReport.class));
                    rssOrderSources.stream().forEach(o -> o.setReportDashboardDetails(rddOrderSources));
                    if (null != rssOrderSources && !rssOrderSources.isEmpty()) {
                        reportResult.setReportDashboardSummery(rssOrderSources);
                    }
//                } else if (report.getReportName().equals("ITEM_CONSUMPTION_SUMMARY")) {
//                    logger.info("Getting REPORT_ITEM_CONSUMPTION_SUMMARY ");
//                    List<ReportItemConsumptionSummary> rICSummary = getReportItemConsumptionSummary(report, ITEM_CONSUMPTION_SUMMARY_SQL);
//                    reportResult.setReportItemConsumptionSummary(rICSummary);
                } else if (report.getReportName().equals("ITEM_CONSUMPTION_SUMMARY_RECIPE")) {
                    StringBuilder queryDateBuilder = getQueryWithRestoAndUserId("REPORT_RECIPE_ITEM_CONSUMPTION_SUMMARY", report);
                    logger.info("ITEM_CONSUMPTION_SUMMARY_RECIPE SQL = [{}]", queryDateBuilder);
                    List<ReportItemConsumptionSummary> rICSummary = jdbcTemplate.query(queryDateBuilder.toString(), BeanPropertyRowMapper.newInstance(ReportItemConsumptionSummary.class));
                    if (null != rICSummary && !rICSummary.isEmpty()) {
                        reportResult.setReportItemConsumptionSummary(rICSummary);
                    }
                } else if (report.getReportName().equals("ITEM_CONSUMPTION_SUMMARY_NONRECIPE")) {
//                    String query = "select * from REPORT_NRECIPE_ITEM_CONSUMPTION_SUMMARY";
                    StringBuilder queryDateBuilder = getQueryWithRestoAndUserId("REPORT_NRECIPE_ITEM_CONSUMPTION_SUMMARY", report);
                    logger.info("ITEM_CONSUMPTION_SUMMARY_NONRECIPE SQL = [{}]", queryDateBuilder);
                    List<ReportItemConsumptionSummary> rICSummary = jdbcTemplate.query(queryDateBuilder.toString(), BeanPropertyRowMapper.newInstance(ReportItemConsumptionSummary.class));
                    if (null != rICSummary && !rICSummary.isEmpty()) {
                        reportResult.setReportItemConsumptionSummary(rICSummary);
                    }
                } else if (report.getReportName().equals("CASHIER_SALES_BY_DISH")) {
//                    String query = "select * from REPORT_CASHIER_SALES_BY_DISH";
                    StringBuilder queryDateBuilder = getQueryWithRestoStoreAndUserId("REPORT_CASHIER_SALES_BY_DISH", report);
                    logger.info("CASHIER_SALES_BY_DISH SQL = [{}]", queryDateBuilder);
                    List<ReportCashierSalesByDish> rICSummary = jdbcTemplate.query(queryDateBuilder.toString(), BeanPropertyRowMapper.newInstance(ReportCashierSalesByDish.class));
                    if (null != rICSummary && !rICSummary.isEmpty()) {
                        reportResult.setReportCashierSalesByDish(rICSummary);
                    }
                } else if (report.getReportName().equals("CUSTOMER_DATA_REPORT")) {
                    String query = "select * from REPORT_CUSTOMER_DATA " + queryBuilder;
                    logger.info("CUSTOMER_DATA_REPORT SQL = [{}]", query);
                    List<ReportCustomerData> customerData = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ReportCustomerData.class));
                    if (null != customerData && !customerData.isEmpty()) {
                        reportResult.setReportCustomerData2(customerData);
                    }
                } else if (report.getReportName().equals("CANCELLED_ORDER_REPORT")) {
                    String query = "select * from REPORT_CANCELLED_ORDERS " + queryBuilder;
                    logger.info("CANCELLED_ORDER_REPORT SQL = [{}]", query);
                    List<ReportCancelledOrder> cancelledOrder = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ReportCancelledOrder.class));
                    if (null != cancelledOrder && !cancelledOrder.isEmpty()) {
                        reportResult.setReportCancelledOrder(cancelledOrder);
                    }
                } else if (report.getReportName().equals("SALES_SUMMARY_BY_OFFER_CODE")) {
                    String query = "select * from REPORT_SALES_SUMMARY_BY_OFFER_CODE " + queryBuilder;
                    logger.info("SALES_SUMMARY_BY_OFFER_CODE SQL = [{}]", query);
                    List<ReportSalesSummaryByOfferCode> offerCodeList = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ReportSalesSummaryByOfferCode.class));
                    if (null != offerCodeList && !offerCodeList.isEmpty()) {
                        reportResult.setReportSalesSummaryByOfferCode(offerCodeList);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error during reports table refresh {}", e.getMessage());
        }


        return reportResult;


    }

    private StringBuilder getQueryWithRestoStoreAndUserId(String tableName, Report report) {
        StringBuilder queryDateBuilder = new StringBuilder("select * from " + tableName + " where restaurant_id = '");
        queryDateBuilder.append(report.getRestaurantId());
        queryDateBuilder.append("' ");
        if (!report.getStoreId().equals(ALL)) {
            queryDateBuilder.append(" and store_id ='");
            queryDateBuilder.append(report.getStoreId());
            queryDateBuilder.append("' ");
        }
        queryDateBuilder.append(" and user_login_id = '");
        queryDateBuilder.append(report.getUserLoginId());
        queryDateBuilder.append("' ");
        return queryDateBuilder;
    }

    private StringBuilder getQueryWithRestoAndUserId(String tableName, Report report) {
        StringBuilder queryDateBuilder = new StringBuilder("select * from " + tableName + " where restaurant_id = '");
        queryDateBuilder.append(report.getRestaurantId());
        queryDateBuilder.append("' ");
        queryDateBuilder.append(" and user_login_id = '");
        queryDateBuilder.append(report.getUserLoginId());
        queryDateBuilder.append("' ");
        return queryDateBuilder;
    }

    List<ReportItemConsumptionSummary> getReportItemConsumptionSummary(Report report, String sql) {
        MapSqlParameterSource parameters = getMapSqlParameterSource(report);
        List<ReportItemConsumptionSummary> itemConsumptionSummary = namedParameterJdbcTemplate.query(
                sql, parameters, BeanPropertyRowMapper.newInstance(ReportItemConsumptionSummary.class));
        return itemConsumptionSummary;
    }

    private MapSqlParameterSource getMapSqlParameterSource(Report report) {
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("restaurantId", report.getRestaurantId());
        parameters.addValue("storeId", report.getStoreId());
        parameters.addValue("fromDate", report.getFromDate());
        parameters.addValue("toDate", report.getToDate());
        return parameters;
    }

    private List<CustomerReportData> getCustomerReportData() {

        logger.info("CUSTOMER_REPORT SQL = [{}]", CUSTOMER_REPORT_SQL);
        return jdbcTemplate.query(CUSTOMER_REPORT_SQL, BeanPropertyRowMapper.newInstance(CustomerReportData.class));

    }

    Map<String, List<ReportOrderData>> consolidateResponseByOrderId(List<ReportOrderData> results) {
        Map<String, List<ReportOrderData>> orderMap = new LinkedHashMap<>();
        for (ReportOrderData result : results) {
            List<ReportOrderData> exitingList = orderMap.get(result.getOrderId());
            if (exitingList == null) {
                List<ReportOrderData> newList = new ArrayList<>();
                newList.add(result);
                orderMap.put(result.getOrderId(), newList);
            } else {
                exitingList.add(result);
                orderMap.put(result.getOrderId(), exitingList);
            }
        }
        return orderMap;
    }

    private List<OrderVO> convertMapList(Map<String, List<ReportOrderData>> orderMap) {
        List<OrderDetailsVO> orderDetailsVOList;
        OrderVO orderVO;
        List<OrderVO> orderList = new ArrayList<>();
        for (List<ReportOrderData> orderDTOList : orderMap.values()) {
            orderVO = new OrderVO();
            orderDetailsVOList = new ArrayList<>();
            for (int i = 0; i < orderDTOList.size(); i++) {
                if (i == 0) {
                    orderVO = populateOrderVO(orderDTOList.get(i));
                }
                orderDetailsVOList.add(populateOrderDetailsVO(orderDTOList.get(i)));

            }
            orderVO.setOrderDetails(orderDetailsVOList);
            orderList.add(orderVO);

        }
        return orderList;
    }

    private OrderDetailsVO populateOrderDetailsVO(ReportOrderData result) {

        OrderDetailsVO orderDetailsVO = new OrderDetailsVO();

        orderDetailsVO.setOrderId(result.getOrderId());
        orderDetailsVO.setProductId(result.getProductId());
        orderDetailsVO.setProductName(result.getDishType());
        orderDetailsVO.setSubProductId(result.getSubProductId());
        orderDetailsVO.setIngredient(result.getIngredientType());
        orderDetailsVO.setQuantity(result.getQuantity());
        orderDetailsVO.setPrice(result.getPrice());
        orderDetailsVO.setRemarks(result.getRemarks());
        orderDetailsVO.setOrderDetailStatus(result.getOrderDetailStatus());
        orderDetailsVO.setKdsRoutingName(result.getKdsRoutingName());
        orderDetailsVO.setOrderDetailFoodPackagedFlag(result.getOrderDetailFoodPackagedFlag());
        return orderDetailsVO;
    }

    OrderVO populateOrderVO(ReportOrderData result) {
        OrderVO vo = new OrderVO();
        vo.setOrderId(result.getOrderId());
        vo.setRestaurantId(result.getRestaurantId());
        vo.setRestaurantName(result.getRestaurantName());
        vo.setStoreId(result.getStoreId());
        vo.setStoreName(result.getStoreName());
        vo.setOrderSource(result.getOrderSource());
        vo.setCustomerId(result.getCustomerId());
        vo.setCustomerName(result.getCustomerName());
        vo.setOrderDeliveryType(result.getOrderDeliveryType());
        vo.setStoreTableId(result.getStoreTableId());
        vo.setStoreId(result.getStoreId());
        vo.setOrderStatus(result.getOrderStatus());
        vo.setPaymentStatus(result.getPaymentStatus());
        vo.setPaymentMode(result.getPaymentMode());
        vo.setPaymentTxnReference(result.getPaymentTxnReference());
        vo.setTaxRuleId(result.getTaxRuleId());
        vo.setTotalPrice(result.getTotalPrice());
        vo.setCgstCalculatedValue(result.getCgstCalculatedValue());
        vo.setSgstCalculatedValue(result.getSgstCalculatedValue());
        vo.setDeliveryCharges(result.getDeliveryCharges());
        vo.setOverallPriceWithTax(result.getOverallPriceWithTax());
        vo.setCreatedBy(result.getCreatedBy());
        vo.setCreatedDate(result.getCreatedDate());
        vo.setUpdatedBy(result.getUpdatedBy());
        vo.setUpdatedDate(result.getUpdatedDate());
        vo.setCustomerAddressId(result.getCustomerAddressId());
        vo.setMobileNumber(result.getMobileNumber());
        vo.setAddress(result.getAddress());
        vo.setDeliveryUserId(result.getDeliveryUserId());
        vo.setFoodPackagedFlag(result.getFoodPackagedFlag());
        vo.setCouponCode(result.getCouponCode());
        vo.setDiscountPercentage(result.getDiscountPercentage());
        vo.setOrderReceivedDateTime(result.getOrderReceivedDateTime());
        return vo;


    }


    private List<ReportCashierSummeryResponse> transformData(Map<String, List<ReportCashierSummery>> groupCollectionByCashier) {

        List<ReportCashierSummeryResponse> responseList = new ArrayList<>();
        for (Map.Entry<String, List<ReportCashierSummery>> dataMap : groupCollectionByCashier.entrySet()) {
            ReportCashierSummeryResponse response = new ReportCashierSummeryResponse();
            List<ReportCashierSummery> dataList = dataMap.getValue();
            Map<String, List<CashierSummeryData>> dataListMap = new LinkedHashMap<>();

            for (ReportCashierSummery data : dataList) {
                response.setCashierName(data.getCashierName());
                response.setRestaurantId(data.getRestaurantId());
                response.setStoreId(data.getStoreId());
                response.setStoreName(data.getStoreName());

                List<CashierSummeryData> exitingList = dataListMap.get(data.getTypeOfData());

                if (null == exitingList) {
                    List<CashierSummeryData> newList = new ArrayList<>();
                    CashierSummeryData cashierSummeryData = getCashierSummeryData(data);
                    newList.add(cashierSummeryData);
                    dataListMap.put(data.getTypeOfData(), newList);
                } else {
                    CashierSummeryData cashierSummeryData = getCashierSummeryData(data);
                    exitingList.add(cashierSummeryData);
                    dataListMap.put(data.getTypeOfData(), exitingList);
                }

                response.setDetails(dataListMap);

            }
            responseList.add(response);

        }
        return responseList;
    }

    private CashierSummeryData getCashierSummeryData(ReportCashierSummery data) {
        CashierSummeryData cashierSummeryData = new CashierSummeryData();
        cashierSummeryData.setTypeOfData(data.getTypeOfData());
        cashierSummeryData.setTotalPrice(data.getTotalPrice());
        cashierSummeryData.setTotalQty(data.getTotalQty());
        cashierSummeryData.setCategory(data.getCategory());
        return cashierSummeryData;
    }

    Map<String, List<ReportCashierSummery>> groupCollectionByCashier(List<ReportCashierSummery> rcs) {
        Map<String, List<ReportCashierSummery>> map = new LinkedHashMap<>();
        for (ReportCashierSummery result : rcs) {
            String ketString = result.getCashierName() + " - " + result.getStoreId();
            List<ReportCashierSummery> exitingList = map.get(ketString);
            if (null == exitingList) {
                List<ReportCashierSummery> newList = new ArrayList<>();
                newList.add(result);
                map.put(ketString, newList);
            } else {
                exitingList.add(result);
                map.put(ketString, exitingList);
            }

        }
        return map;
    }

    public List<OrderVO> getCancelledOrderReportData() {

        String queryString = ORDER_MENU_INGREDIENT_ADDRESS_VIEW_SQL + "and order_status = 'CANCELLED' ";
        queryString = queryString + " order by created_date, order_id, id desc, product_id, sub_product_id";
        logger.info("CANCELLED_REPORT SQL = [{}]", queryString);
        List<OrderMenuIngredientAddressDTO> results = jdbcTemplate.query(queryString, BeanPropertyRowMapper.newInstance(OrderMenuIngredientAddressDTO.class));
        Map<String, List<OrderMenuIngredientAddressDTO>> orderMap = orderService.consolidateResponseToOrderedMapByOrderId(results);
        List<OrderVO> orderList = orderService.convertOrderDTOMapTOOrderVOList(orderMap);
        return orderList;
    }


}
