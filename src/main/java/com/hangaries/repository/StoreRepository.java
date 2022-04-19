package com.hangaries.repository;

import com.hangaries.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByStoreId(String storeId);

    long deleteByStoreId(String storeId);
}
