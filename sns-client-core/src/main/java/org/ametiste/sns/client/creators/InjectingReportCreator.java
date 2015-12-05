package org.ametiste.sns.client.creators;

import org.ametiste.sns.client.creators.injectors.model.EnvironmentData;
import org.ametiste.sns.client.drivers.ReportServiceDriver;
import org.ametiste.sns.client.creators.injectors.model.ReportBuilder;
import org.ametiste.sns.client.creators.injectors.model.ReportData;
import org.ametiste.sns.client.model.Report;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by ametiste on 8/19/15.
 */
public class InjectingReportCreator {

    private final ReportServiceDriver driver;
    private List<EnvironmentData> environments;
    private final ReportErrorMode mode;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public InjectingReportCreator(ReportServiceDriver driver, List<EnvironmentData> environments, ReportErrorMode mode) {
        this.mode = mode;

        if (driver == null) {
            throw new IllegalArgumentException("ReportServiceDriver can't be null.");
        }
        if (environments == null) {
            throw new IllegalArgumentException("Environment data should be set. Can't be null.");
        }

        this.driver = driver;
        this.environments = environments;

    }

    public InjectingReportCreator(ReportServiceDriver driver, List<EnvironmentData> environments) {

        if (driver == null) {
            throw new IllegalArgumentException("ReportServiceDriver can't be null.");
        }
        if (environments == null) {
            throw new IllegalArgumentException("Environment data should be set. Can't be null.");
        }

        this.driver = driver;
        this.environments = environments;
        this.mode = ReportErrorMode.SILENT;

    }

    public void createReport(ReportData data) {

        ReportBuilder reportBuilder = new ReportBuilder();
        data.inject(reportBuilder);

        for(EnvironmentData environment: environments) {
            environment.inject(reportBuilder);
        }

        Report report = reportBuilder.buildReport();

        try {

            driver.createNewReport(report.getReportId(), report.getDate(),
                    report.getReportType(), report.getReportSender(), report.getReportContent());
        }
        catch (Exception e) {
            if(mode.equals(ReportErrorMode.STRICT)) {
                throw e;
            }
            logger.debug("Report error mode is silent. Ignoring report sending error: ", e);
        }
    }
}
