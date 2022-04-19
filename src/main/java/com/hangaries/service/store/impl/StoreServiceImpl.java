package com.hangaries.service.store.impl;

import com.hangaries.model.Store;
import com.hangaries.repository.StoreRepository;
import com.hangaries.service.store.StoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    private static final Logger logger = LoggerFactory.getLogger(StoreServiceImpl.class);

    @Autowired
    StoreRepository storeRepository;

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

    @Override
    public long deleteStoreByStoreId(String storeId) {
        return storeRepository.deleteByStoreId(storeId);
    }
}
