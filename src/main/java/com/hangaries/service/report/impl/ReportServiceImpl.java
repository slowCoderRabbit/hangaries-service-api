package com.hangaries.service.report.impl;

import com.hangaries.model.*;
import com.hangaries.model.dto.OrderMenuIngredientAddressDTO;
import com.hangaries.model.vo.OrderDetailsVO;
import com.hangaries.model.vo.OrderVO;
import com.hangaries.repository.ReportRepository;
import com.hangaries.service.report.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.hangaries.constants.HangariesConstants.ALL;

@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public ReportResult getReports(Report report) throws ParseException {

        logger.info("Refreshing reports table.....");
        ReportResult reportResult = new ReportResult();

        try {
            String result = reportRepository.getReports(report.getRestaurantId(), report.getStoreId(), report.getFromDate(), report.getToDate(), report.getReportName());
            logger.info("Result of reports table refresh {}", result);

            StringBuilder queryBuilder = new StringBuilder(" where restaurant_id ='");
            queryBuilder.append(report.getRestaurantId());
            queryBuilder.append("' ");
            if (!report.getStoreId().equals(ALL)) {
                queryBuilder.append("and store_id ='");
                queryBuilder.append(report.getStoreId());
                queryBuilder.append("' ");
            }


            if (report.getReportName().equals("SALES_SUMMARY_BY_DATE_LIST")) {

                StringBuilder queryDateBuilder = new StringBuilder(" and order_date between '");
                queryDateBuilder.append(report.getFromDate());
                queryDateBuilder.append("' and '");
                queryDateBuilder.append(report.getToDate());
                queryDateBuilder.append("' ");
                String query = "select * from REPORT_SALES_SUMMARY_BY_DATE" + queryBuilder + queryDateBuilder;
                logger.info("SALES_SUMMARY_BY_DATE_LIST SQL = [{}]", query);
                List<RSSDate> rssDate = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(RSSDate.class));
                reportResult.setSalesSummeryByDateList(rssDate);
            } else if (report.getReportName().equals("SALES_SUMMARY_BY_ORDER_SOURCE")) {
                String query = "select * from REPORT_SALES_SUMMARY_BY_ORDER_SOURCE" + queryBuilder;
                logger.info("REPORT_SALES_SUMMARY_BY_ORDER_SOURCE SQL = [{}]", query);
                List<RSSOrderSource> rssOrderSources = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(RSSOrderSource.class));
                reportResult.setSalesSummeryByOrderSource(rssOrderSources);
            } else if (report.getReportName().equals("SALES_SUMMARY_BY_PAYMENT_MODE")) {
                String query = "select * from REPORT_SALES_SUMMARY_BY_PAYMENT_MODE" + queryBuilder;
                logger.info("REPORT_SALES_SUMMARY_BY_PAYMENT_MODE SQL = [{}]", query);
                List<RSSPaymentMode> rSSPaymentMode = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(RSSPaymentMode.class));
                reportResult.setSalesSummeryByPaymentMode(rSSPaymentMode);
            } else if (report.getReportName().equals("SALES_BY_DISH_ITEM")) {
                String query = "select * from REPORT_DISH_CONSUMPTION_SUMMARY" + queryBuilder;
                logger.info("REPORT_DISH_CONSUMPTION_SUMMARY SQL = [{}]", query);
                List<ReportDishConsumptionSummary> rDCSummary = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ReportDishConsumptionSummary.class));
                reportResult.setReportDishConsumptionSummary(rDCSummary);
            } else if (report.getReportName().equals("CASH_SALES_REPORT")) {
                String query = "select * from REPORT_CASHIER_SUMMARY" + queryBuilder + " order by cashier_name,store_name,type_of_data,category";
                logger.info("REPORT_CASHIER_SUMMARY SQL = [{}]", query);
                List<ReportCashierSummery> results = jdbcTemplate.query(query, BeanPropertyRowMapper.newInstance(ReportCashierSummery.class));
                logger.info("ReportCashierSummery records from DB = {}", results.size());
                List<ReportCashierSummeryResponse> response = transformData(groupCollectionByCashier(results));
                logger.info("ReportCashierSummery post data transformation records = {}", response.size());
                reportResult.setReportCashierSummery(response);
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
                reportResult.setReportOrderData(orderList);
            }


        } catch (Exception e) {
            logger.error("Error during reports table refresh {}", e.getMessage());
        }


        return reportResult;


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

//    Map<String, List<ReportCashierSummeryResponse>> groupCollectionByCashier(List<ReportCashierSummery> rcs) {
//        Map<String, ReportCashierSummeryResponse> map =  new LinkedHashMap<>();
//        ReportCashierSummeryResponse response;
//        String cashier = null;
//        for (ReportCashierSummery result : rcs) {
//            response = map.get(result.getCashierName());
//            if (null == response) {
//                response= new ReportCashierSummeryResponse();
//                response.setCashierName(result.getCashierName());
//                response.setRestaurantId(result.getRestaurantId());
//                response.setStoreId(result.getStoreId());
//                response.setStoreName(result.getStoreName());
//                populateCashierSummeryData();
//                map.put(result.getCashierName(),response );
//            }
//
//        }
//        return null;
//    }

//    private void populateCashierSummeryData(ReportCashierSummery result) {
//        CashierSummeryData data = new CashierSummeryData();
//
//        data.setTypeOfData(result.getTypeOfData());
//
//        return null;
//    }

    Map<String, List<OrderMenuIngredientAddressDTO>> consolidateResponseToOrderedMapByOrderId(List<OrderMenuIngredientAddressDTO> results) {
        Map<String, List<OrderMenuIngredientAddressDTO>> orderMap = new LinkedHashMap<>();
        for (OrderMenuIngredientAddressDTO result : results) {
            List<OrderMenuIngredientAddressDTO> exitingList = orderMap.get(result.getOrderId());
            if (exitingList == null) {
                List<OrderMenuIngredientAddressDTO> newList = new ArrayList<>();
                newList.add(result);
                orderMap.put(result.getOrderId(), newList);
            } else {
                exitingList.add(result);
                orderMap.put(result.getOrderId(), exitingList);
            }
        }
        return orderMap;
    }
}
