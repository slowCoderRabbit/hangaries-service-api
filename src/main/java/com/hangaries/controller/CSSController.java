package com.hangaries.controller;

import com.hangaries.model.CSSMaster;
import com.hangaries.model.CSSRequest;
import com.hangaries.service.css.CSSServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class CSSController {

    private static final Logger logger = LoggerFactory.getLogger(CSSController.class);

    @Autowired
    CSSServiceImpl cssService;

    @PostMapping("saveCss")
    public ResponseEntity<CSSMaster> saveCss(@Valid @RequestBody CSSMaster request) {
        logger.info("Saving CSS request = [{}].", request);
        CSSMaster response = cssService.saveCss(request);
        logger.info("CSS Saved!! Sending response = [{}].", response);
        return new ResponseEntity<CSSMaster>(response, HttpStatus.OK);
    }

    @PostMapping("saveCSSStatus")
    public Optional<CSSMaster> saveCSSStatus(@RequestParam("id") long id, @RequestParam("status") String status) {
        logger.info("Saving CSS status = [{}] for id = [{}].", id, status);
        return cssService.saveCSSStatus(id, status);
    }

    @GetMapping("getActiveCSS")
    public List<CSSMaster> getActiveCSS(@RequestBody CSSRequest request) {
        logger.info("Getting active CSS for request = [{}].", request);
        List<CSSMaster> result = cssService.getActiveCSS(request);
        logger.info("[{}] active records found for CSS request = [{}].", result.size(), request);
        return result;
    }
}
