package com.hangaries.service.report.impl;

import com.hangaries.model.*;
import com.hangaries.model.dto.OrderMenuIngredientAddressDTO;
import com.hangaries.repository.*;
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

//            List<RSSDate> rssDate = rssDateRepository.getReport();
            List<RSSDate> rssDate = jdbcTemplate.query("select * from REPORT_SALES_SUMMARY_BY_DATE", BeanPropertyRowMapper.newInstance(RSSDate.class));
            reportResult.setSalesSummeryByDateList(rssDate);

//            List<RSSDishType> rssDishTypes = rssDishTypeRepository.getReport();
            List<RSSDishType> rssDishTypes = jdbcTemplate.query("select * from REPORT_SALES_SUMMARY_BY_DISH_TYPE", BeanPropertyRowMapper.newInstance(RSSDishType.class));
            reportResult.setSalesSummeryByDishType(rssDishTypes);

//            List<RSSOrderSource> rssOrderSources = rssOrderSourceRepository.getReport();
            List<RSSOrderSource> rssOrderSources = jdbcTemplate.query("select * from REPORT_SALES_SUMMARY_BY_ORDER_SOURCE", BeanPropertyRowMapper.newInstance(RSSOrderSource.class));
            reportResult.setSalesSummeryByOrderSource(rssOrderSources);

//            List<RSSPaymentMode> rSSPaymentMode = rssPayModeRepository.getReport();
            List<RSSPaymentMode> rSSPaymentMode = jdbcTemplate.query("select * from REPORT_SALES_SUMMARY_BY_PAYMENT_MODE", BeanPropertyRowMapper.newInstance(RSSPaymentMode.class));
            reportResult.setSalesSummeryByPaymentMode(rSSPaymentMode);

//            List<ReportDishConsumptionSummary> rDCSummary = rdcSummaryRepository.getReport();
            List<ReportDishConsumptionSummary> rDCSummary = jdbcTemplate.query("select * from REPORT_DISH_CONSUMPTION_SUMMARY", BeanPropertyRowMapper.newInstance(ReportDishConsumptionSummary.class));
            reportResult.setReportDishConsumptionSummary(rDCSummary);

            List<ReportCashierSummery> results = jdbcTemplate.query("SELECT * FROM REPORT_CASHIER_SUMMARY order by cashier_name,store_name,type_of_data,category", BeanPropertyRowMapper.newInstance(ReportCashierSummery.class));
            logger.info("ReportCashierSummery records from DB = {}", results.size());
            List<ReportCashierSummeryResponse> response = transformData(groupCollectionByCashier(results));
            logger.info("ReportCashierSummery post data transformation records = {}", response.size());
            reportResult.setReportCashierSummery(response);


        } catch (Exception e) {
            logger.error("Error during reports table refresh {}", e.getMessage());
        }


        return reportResult;


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
