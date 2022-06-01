package com.hangaries.controller;

import com.hangaries.model.Report;
import com.hangaries.model.ReportResult;
import com.hangaries.service.report.impl.ReportServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class ReportController {

    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    ReportServiceImpl reportService;

    @PostMapping("getReports")
    public ResponseEntity<ReportResult> addStore(@Valid @RequestBody Report report) {
        logger.info("Generating reports for details  : " + report.toString());
        ReportResult reportResult = new ReportResult();
        try {
            reportResult = reportService.getReports(report);
            return new ResponseEntity<ReportResult>(reportResult, HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Error while getting store details::" + ex.getMessage());
            return new ResponseEntity<ReportResult>(reportResult, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
