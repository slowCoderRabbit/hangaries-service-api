package com.hangaries.service.offer;

import com.hangaries.model.Offer;
import com.hangaries.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl {

    @Autowired
    OfferRepository offerRepository;

    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    public List<Offer> getOffersByStatus(String status) {
        return offerRepository.getOffersByStatus(status);
    }

    public Offer saveOffer(Offer offer) {
        return offerRepository.save(offer);
    }
}
