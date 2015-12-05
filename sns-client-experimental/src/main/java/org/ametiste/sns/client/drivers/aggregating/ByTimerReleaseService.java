package org.ametiste.sns.client.drivers.aggregating;

import org.ametiste.sns.client.drivers.ReportServiceDriver;
import org.ametiste.sns.client.model.Report;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class ByTimerReleaseService implements ReleaseService, InitializingBean {

	private ReportAggregator aggregator;
	private int releasePeriod;	
	private ReportServiceDriver driver;
	
	@Override
	public void run() {
		while(true) {
			if (aggregator.canRelease()) {
				List<Report> reports = aggregator.releaseReports().stream()
						.map(AggregatedReport::buildReport)
						.collect(Collectors.toList());
				this.releaseReports(reports);
			}
			try {
				Thread.sleep(releasePeriod);
			} catch (InterruptedException e) {
				// nothing to do
			}
		}

	}

	@Override
	public void releaseReports(List<Report> reports) {
		for(Report report: reports) {
			driver.createNewReport(report.getReportId(), report.getDate(), report.getReportType(),
					report.getReportSender(), report.getReportContent());
		}

	}

	public void setAggregator(ReportAggregator aggregator) {
		this.aggregator = aggregator;
	}

	public void setReleasePeriod(int releasePeriod) {
		this.releasePeriod = releasePeriod;
	}

	public void setDriver(ReportServiceDriver driver) {
		this.driver = driver;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(driver, "Driver cant be null");
		Assert.isTrue(releasePeriod != 0, "ReleasePeriod should be set and cant be equal 0");
		Assert.notNull(aggregator, "Aggregator cant be null");

	}

}
