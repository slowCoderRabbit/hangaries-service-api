package com.hangaries.service.store.impl;

import com.hangaries.model.Store;
import com.hangaries.repository.StoreRepository;
import com.hangaries.service.store.StoreService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StoreServiceImpl implements StoreService {

    private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);

    private static final Map<String, Store> weraMerchantToStoreMapping = new HashMap<>();
    @Autowired
    StoreRepository storeRepository;

    public static Map<String, Store> getWeraMerchantToStoreMapping() {
        return Collections.unmodifiableMap(weraMerchantToStoreMapping);
    }

    public static Optional<Store> getStoreDetailsFromStoreIdCache(String storeId) {
        logger.info("Getting store details for StoreId = [{}]", storeId);
        if (StringUtils.isBlank(storeId)) {
            return null;
        }
        return weraMerchantToStoreMapping.entrySet().stream().filter(e -> storeId.equals(e.getValue().getStoreId())).map(Map.Entry::getValue).findFirst();

    }

    @Bean(initMethod = "init")
    private void populateWeraMerchantToStoreMapping() {
        logger.info("Loading WERA merchant to store mapping......");
        List<Store> stores = getAllStores();
        for (Store store : stores) {
            if (null != store.getWeraMerchantId() && !StringUtils.isBlank(store.getWeraMerchantId())) {
                weraMerchantToStoreMapping.put(store.getWeraMerchantId(), store);
            }
        }
        logger.info("WERA merchant to store mapping loaded. [{}]", weraMerchantToStoreMapping);
    }

    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public List<Store> getStoreDetailsByStoreId(String storeId) {
        return storeRepository.findByStoreId(storeId);
    }

    @Override
    public Store addStore(Store newStore) {
        return storeRepository.save(newStore);
    }


    public Store getStoreDetailsByWeraMerchantId(String weraMerchantId) {
        logger.info("Getting store details for weraMerchantId = [{}]", weraMerchantId);
        return storeRepository.findByWeraMerchantId(weraMerchantId);
    }

    public List<Store> updateStoreActiveFlag(String storeId, String storeActiveFlag) {
        int result = storeRepository.updateStoreActiveFlag(storeId, storeActiveFlag);
        logger.info("updateStoreActiveFlag result = [{}]", result);
        return storeRepository.findByStoreId(storeId);
    }
}
