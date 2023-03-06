package com.hangaries.service.store.impl;

import com.hangaries.model.Store;
import com.hangaries.repository.OrderIdRepository;
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

    public static final String NEW_STORE = "NEW";
    private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);
    private static final Map<String, Store> weraMerchantToStoreMapping = new HashMap<>();
    @Autowired
    StoreRepository storeRepository;

    @Autowired
    OrderIdRepository idRepository;

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

        String newStoreId = null;
        if (isStoreIdPresent(newStore)) {
            logger.info("Store Id is empty!!!. Generating new store ID");
            newStoreId = "S" + getPaddedString(getNextStoreIdFromDB(newStore));
            logger.info("New store ID = [{}]", newStoreId);
            newStore.setStoreId(newStoreId);
        }

        Store store = storeRepository.save(newStore);
        logger.info("Store saved successfully with store Id = [{}].", store.getStoreId());

        if (isStoreIdPresent(newStore)) {
            logger.info("Calling SP setupNewStore for store Id = [{}].", store.getStoreId());
            String result = storeRepository.setupNewStore(store.getRestaurantId(), store.getStoreId());
            logger.info("SP setupNewStore returned result = [{}] for store Id = [{}].", result, store.getStoreId());
        }

        return store;
    }

    private String getPaddedString(int maxId) {
        return String.format("%03d", maxId);
    }

    private int getNextStoreIdFromDB(Store newStore) {
        int latestId = idRepository.getLatestOrderId(newStore.getRestaurantId(), NEW_STORE);
        logger.info("Latest Id from DB for restaurantId = [{}] and store id = [{}] is = [{}]", newStore.getRestaurantId(), NEW_STORE, latestId);
        latestId++;
        idRepository.saveNewOrderId(newStore.getRestaurantId(), NEW_STORE, latestId);
        logger.info("Next Id for restaurantId = [{}] and store id = [{}] is = [{}]", newStore.getRestaurantId(), NEW_STORE, latestId);
        return latestId;
    }

    private boolean isStoreIdPresent(Store newStore) {
        return StringUtils.isBlank(newStore.getStoreId());
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
