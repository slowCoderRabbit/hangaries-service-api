package com.hangaries.service.config.impl;

import com.hangaries.model.*;
import com.hangaries.repository.ApplicationRepository;
import com.hangaries.repository.BusinessDateRepository;
import com.hangaries.repository.ConfigMasterRepository;
import com.hangaries.service.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.hangaries.constants.HangariesConstants.SYSTEM;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    ConfigMasterRepository configMasterRepository;

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    BusinessDateRepository businessDateRepository;

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

    public List<BusinessDate> getAllBusinessDates() {
        return businessDateRepository.findAll();
    }

    public List<PaymentMode> getPaymentModes() {

        List<Object[]> results = configMasterRepository.getPaymentModes();
        List<PaymentMode> paymentModes = new ArrayList<>();
        for (Object[] result : results) {
            String value = result[0].toString();
            String description = result[1].toString();
            paymentModes.add(new PaymentMode(value, description));

        }
        return paymentModes;
    }
}
