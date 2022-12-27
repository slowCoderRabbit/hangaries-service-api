package com.hangaries.controller;


import com.hangaries.model.Offer;
import com.hangaries.service.offer.OfferServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class OfferController {

    private static final Logger logger = LoggerFactory.getLogger(OfferController.class);

    @Autowired
    OfferServiceImpl offerService;

    @GetMapping("getAllOffers")
    public ResponseEntity<List<Offer>> getAllOffers() {
        List<Offer> offers = new ArrayList<>();
        logger.info("Getting list of all the offers.");
        offers = offerService.getAllOffers();
        logger.info("[{}] offers returned.", offers.size());
        return new ResponseEntity<List<Offer>>(offers, HttpStatus.OK);

    }

    @GetMapping("getOffersByStatus")
    public ResponseEntity<List<Offer>> getOffersByStatus(@RequestParam String status) {
        List<Offer> offers = new ArrayList<>();
        logger.info("Getting list of all the offers with status = [{}].", status);
        offers = offerService.getOffersByStatus(status);
        logger.info("[{}] offers returned.", offers.size());
        return new ResponseEntity<List<Offer>>(offers, HttpStatus.OK);

    }

    @PostMapping("saveOffer")
    public ResponseEntity<Offer> getOffersByStatus(@RequestBody Offer offer) {
        logger.info("Saving offer = [{}].", offer);
        Offer newOffer = offerService.saveOffer(offer);
        logger.info("[{}] offer returned.", newOffer);
        return new ResponseEntity<Offer>(newOffer, HttpStatus.OK);

    }

}
