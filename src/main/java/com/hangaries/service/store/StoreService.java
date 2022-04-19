package com.hangaries.service.store;

import com.hangaries.model.Store;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StoreService {

    List<Store> getAllStores();
    List<Store> getStoreDetailsByStoreId(String storeId);

    Store addStore(Store newStore);

    long deleteStoreByStoreId(String storeId);
}
