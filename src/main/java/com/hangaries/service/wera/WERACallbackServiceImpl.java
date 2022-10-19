package com.hangaries.service.wera;

import com.hangaries.model.wera.request.WERAOrderAcceptRequest;
import com.hangaries.model.wera.response.WERAOrderAcceptResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WERACallbackServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(WERACallbackServiceImpl.class);

    @Value("${wera.order.accept.url}")
    private String orderAcceptURL;
    @Value("${wera.api.key}")
    private String werAPIKey;
    @Value("${wera.api.value}")
    private String werAPIValue;

    public ResponseEntity<WERAOrderAcceptResponse> callWERAOrderAcceptAPI(WERAOrderAcceptRequest request) {
        logger.info("################## WERA ACCEPT ORDER - INITIATING!!! ##################");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(werAPIKey, werAPIValue);

        HttpEntity<WERAOrderAcceptRequest> httpRequest = new HttpEntity<>(request, headers);
        ResponseEntity<WERAOrderAcceptResponse> response = null;
        try {
            response = restTemplate.postForEntity(orderAcceptURL, httpRequest, WERAOrderAcceptResponse.class);
            logger.info("WERA ACCEPT ORDER RESPONSE = [{}]", response.getBody());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            logger.error("Error while accepting wera order [{}], [{}]", request.getOrder_id(), ex.getMessage());
        }

        return response;
    }

    public ResponseEntity<WERAOrderAcceptResponse> callWERAOrderAcceptAPI(String orderId) {
        WERAOrderAcceptRequest request = null;
        return callWERAOrderAcceptAPI(request);
    }

}
