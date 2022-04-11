package com.hangaries.repository;

import com.hangaries.model.OrderId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface OrderIdRepository extends JpaRepository<OrderId, Long> {

}
