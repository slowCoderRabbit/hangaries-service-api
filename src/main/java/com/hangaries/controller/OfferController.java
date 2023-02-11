package com.hangaries.controller;


import com.hangaries.model.Offer;
import com.hangaries.model.OfferDetails;
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
    public ResponseEntity<List<Offer>> getAllOffers(@RequestParam("restaurantId") String restaurantId,
                                                    @RequestParam("storeId") String storeId) {
        List<Offer> offers = new ArrayList<>();
        logger.info("Getting list of all the offers for restaurantId = [{}], storeId = [{}].", restaurantId, storeId);
        offers = offerService.getAllOffers(restaurantId, storeId);
        logger.info("[{}] offers returned.", offers.size());
        return new ResponseEntity<List<Offer>>(offers, HttpStatus.OK);

    }

    @GetMapping("getOffersByStatus")
    public ResponseEntity<List<Offer>> getOffersByStatus(@RequestParam("restaurantId") String restaurantId,
                                                         @RequestParam("storeId") String storeId, @RequestParam String status) {
        List<Offer> offers = new ArrayList<>();
        logger.info("Getting list of all the offers for restaurantId = [{}], storeId = [{}] with status = [{}].", restaurantId, storeId, status);
        offers = offerService.getOffersByStatus(restaurantId, storeId, status);
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

    @GetMapping("getAllOfferDetails")
    public ResponseEntity<List<OfferDetails>> getAllOfferDetails() {
        List<OfferDetails> offers = new ArrayList<>();
        logger.info("Getting list of all the offer details");
        offers = offerService.getAllOfferDetails();
        logger.info("[{}] offers returned.", offers.size());
        return new ResponseEntity<List<OfferDetails>>(offers, HttpStatus.OK);

    }

    @GetMapping("getOfferDetailsByStatus")
    public ResponseEntity<List<OfferDetails>> getOfferDetailsByStatus(@RequestParam String status) {
        List<OfferDetails> offers = new ArrayList<>();
        logger.info("Getting list of all the offer details with status = [{}].", status);
        offers = offerService.getOfferDetailsByStatus(status);
        logger.info("[{}] offers returned.", offers.size());
        return new ResponseEntity<List<OfferDetails>>(offers, HttpStatus.OK);

    }

    @PostMapping("saveOfferDetails")
    public ResponseEntity<OfferDetails> saveOfferDetail(@RequestBody OfferDetails offer) {
        logger.info("Saving offer details = [{}].", offer);
        OfferDetails newOffer = offerService.saveOfferDetail(offer);
        logger.info("[{}] offer returned.", newOffer);
        return new ResponseEntity<OfferDetails>(newOffer, HttpStatus.OK);

    }

}
