package com.hangaries.service.report.impl;

import com.hangaries.model.*;
import com.hangaries.repository.*;
import com.hangaries.service.report.ReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Autowired
    ReportRepository reportRepository;

    @Autowired
    RSSDateRepository rssDateRepository;

    @Autowired
    RSSPayModeRepository rssPayModeRepository;

    @Autowired
    RSSDishTypeRepository rssDishTypeRepository;

    @Autowired
    RSSOrderSourceRepository rssOrderSourceRepository;

    @Override
    public ReportResult getReports(Report report) throws ParseException {

        logger.info("Refreshing reports table.....");
        ReportResult reportResult = new ReportResult();

        try {
            String result = reportRepository.getReports(report.getRestaurantId(), report.getStoreId(), report.getFromDate(), report.getToDate());
            logger.info("Result of reports table refresh {}", result);

            List<RSSDate> rssDate = rssDateRepository.getReport();
            reportResult.setSalesSummeryByDateList(rssDate);

            List<RSSDishType> rssDishTypes = rssDishTypeRepository.getReport();
            reportResult.setSalesSummeryByDishType(rssDishTypes);

            List<RSSOrderSource> rssOrderSources = rssOrderSourceRepository.getReport();
            reportResult.setSalesSummeryByOrderSource(rssOrderSources);

            List<RSSPaymentMode> rSSPaymentMode = rssPayModeRepository.getReport();
            reportResult.setSalesSummeryByPaymentMode(rSSPaymentMode);
        } catch (Exception e) {
            logger.error("Error during reports table refresh {}", e.getMessage());
        }


        return reportResult;


    }
}
