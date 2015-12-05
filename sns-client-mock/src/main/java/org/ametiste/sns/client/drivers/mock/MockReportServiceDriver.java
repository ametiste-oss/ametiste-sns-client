package org.ametiste.sns.client.drivers.mock;


import org.ametiste.metrics.annotations.Countable;
import org.ametiste.sns.client.drivers.ReportMessage;
import org.ametiste.sns.client.drivers.ReportServiceDriver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by ametiste on 7/15/15.
 */
public class MockReportServiceDriver implements ReportServiceDriver {


    private List<ReportMessage> reports;
    private boolean verifyStarted = false;

    public MockReportServiceDriver() {
        reports = new ArrayList<>();
    }

    @Override
    @Countable(nameSuffixExpression="target.getSnsNamespace() + '.reports.count'")
    public void createNewReport(UUID reportId, Date date, String reportType, String reportSender, Serializable reportContent) {
        if(!verifyStarted) {
            reports.add(new ReportMessage(reportId, date, reportType, reportSender, reportContent));
        }
        else {
            throw new IllegalArgumentException("Cannot register new reports after verify started");
        }
    }


    public MockReportContainer verifyReport() {
        verifyStarted = true;
        return new MockReportContainer(reports);
    }

    public void reset() {
        reports.clear();
        verifyStarted = false;
    }

    public String getSnsNamespace() {
        return "sns";
    }

}
