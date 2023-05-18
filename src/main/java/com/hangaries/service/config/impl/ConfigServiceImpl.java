package com.hangaries.service.config.impl;

import com.hangaries.model.*;
import com.hangaries.repository.ApplicationRepository;
import com.hangaries.repository.BusinessDateRepository;
import com.hangaries.repository.ConfigMasterRepository;
import com.hangaries.service.config.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.hangaries.constants.HangariesConstants.*;

@Service
public class ConfigServiceImpl implements ConfigService {

    private static final Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);

    private static final Map<String, String> orderSourceMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);


    @Autowired
    ConfigMasterRepository configMasterRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    BusinessDateRepository businessDateRepository;

    public static Map<String, String> getOrderSourceMap() {
        return Collections.unmodifiableMap(orderSourceMap);
    }

    @Bean(initMethod = "init")
    private void populateOrderSourceMappingFromConfig() {
        logger.info("Loading order source mapping from config table......");
        List<ConfigMaster> configMasterList = configMasterRepository.getDetailsFromConfigMaster(RESTAURANT_ID, ALL, ORDER_SOURCE);

        for (ConfigMaster configMaster : configMasterList) {
            orderSourceMap.put(configMaster.getConfigCriteriaDesc(), configMaster.getConfigCriteriaValue());
        }
//
//        orderSourceMap = configMasterList.stream().collect(
//                groupingBy(ConfigMaster::getRestaurantId,
//                        Collectors.toMap(ConfigMaster::getConfigCriteriaDesc,ConfigMaster::getConfigCriteriaValue)));

        logger.info("Loaded order source mapping!!! Map size = [{}]", orderSourceMap.size());
    }

    @Override
    public List<ConfigMaster> getConfigDetailsByCriteria(String restaurantId, String storeId, String criteria) {
        return configMasterRepository.getDetailsFromConfigMaster(restaurantId, storeId, criteria);
    }

    @Override
    public ConfigMaster addConfigDetailsByCriteria(ConfigMaster configMaster) {
        return configMasterRepository.save(configMaster);
    }

    @Override
    public List<AppDetails> getAppDetails() {
        return applicationRepository.findAll();
    }

    public List<AppDetails> getAppDetails(String restaurantId, String storeId) {
        return applicationRepository.findByRestaurantIdAndStoreId(restaurantId, storeId);
    }

    public BusinessDate updateBusinessDate(BusinessDate businessDate) {
        return businessDateRepository.save(businessDate);
    }

    public BusinessDate getBusinessDate(String restaurantId, String storeId) {
        return businessDateRepository.getBusinessDate(restaurantId, storeId);
    }

    public BusinessDate performEndOfDay(String restaurantId, String storeId) {
        String result = businessDateRepository.performEndOfDay(restaurantId, storeId);
        BusinessDate businessDate = businessDateRepository.getBusinessDate(restaurantId, storeId);
        businessDate.setResult(result);
        return businessDate;
    }

    public BusinessDate updateBusinessDate(BusinessDateRequest request) throws ParseException {
        BusinessDate businessDate = businessDateRepository.getBusinessDate(request.getRestaurantId(), request.getStoreId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
        Date date = simpleDateFormat.parse(request.getBusinessDate());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        businessDate.setBusinessDate(calendar.getTime());
        calendar.add(Calendar.DATE, -1);
        businessDate.setPreviousBusinessDate(calendar.getTime());
        calendar.add(Calendar.DATE, 2);
        businessDate.setNextBusinessDate(calendar.getTime());
        businessDate.setUpdatedDate(new Date());
        businessDate.setUpdatedBy(SYSTEM);

        return businessDateRepository.save(businessDate);


    }

    public List<BusinessDate> getAllBusinessDates(String restaurantId) {
        return businessDateRepository.findByRestaurantId(restaurantId);
    }

    public List<PaymentMode> getPaymentModes(String restaurantId) {

        List<Object[]> results = configMasterRepository.getPaymentModes(restaurantId);
        List<PaymentMode> paymentModes = new ArrayList<>();
        for (Object[] result : results) {
            String value = result[0].toString();
            String description = result[1].toString();
            paymentModes.add(new PaymentMode(value, description));

        }
        return paymentModes;
    }
}
