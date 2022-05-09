package com.hangaries.service.report;

import com.hangaries.model.Report;
import com.hangaries.model.ReportResult;

import java.text.ParseException;

public interface ReportService {
    ReportResult getReports(Report report) throws ParseException;
}
