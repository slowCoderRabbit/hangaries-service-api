package com.hangaries.repository;

import com.hangaries.model.OfferDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferDetailsRepository extends JpaRepository<OfferDetails, Long> {


    List<OfferDetails> findAllByRuleStatus(String status);
}
