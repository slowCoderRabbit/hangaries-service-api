package com.hangaries.service.offer;

import com.hangaries.model.Offer;
import com.hangaries.model.OfferDetails;
import com.hangaries.repository.OfferDetailsRepository;
import com.hangaries.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl {

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    OfferDetailsRepository offerDetailsRepository;

    public List<Offer> getAllOffers(String restaurantId, String storeId) {
        return offerRepository.findByRestaurantIdAndStoreId(restaurantId, storeId);
    }

    public List<Offer> getOffersByStatus(String restaurantId, String storeId, String status) {
        return offerRepository.findByRestaurantIdAndStoreIdAndOfferStatus(restaurantId, storeId, status);
    }

    public Offer saveOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    public List<OfferDetails> getAllOfferDetails() {
        return offerDetailsRepository.findAll();
    }

    public OfferDetails saveOfferDetail(OfferDetails offer) {
        return offerDetailsRepository.save(offer);
    }

    public List<OfferDetails> getOfferDetailsByStatus(String status) {

        return offerDetailsRepository.findAllByRuleStatus(status);
    }
}
