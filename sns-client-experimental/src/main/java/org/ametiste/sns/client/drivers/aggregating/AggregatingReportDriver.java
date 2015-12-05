package org.ametiste.sns.client.drivers.aggregating;

import org.ametiste.sns.client.drivers.ReportServiceDriver;
import org.ametiste.sns.client.model.Report;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class AggregatingReportDriver implements ReportServiceDriver, InitializingBean {

	private ReleaseService releaser;
	private ReportAggregator aggregator;
	
	@Override
	public void createNewReport(UUID reportId, Date date, String reportType,
			String reportSender, Serializable reportContent) {
		if (aggregator.canAdd()) {
			aggregator.addReport(new Report(reportId, date, reportType, reportSender, reportContent));
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(releaser, "For this implementation, service is required for proper work");
		Assert.notNull(aggregator, "Aggregator cant be null");
		//this bean not required in this class, but how can we check if its created in context? 
		
	}

	public void setAggregator(ReportAggregator aggregator) {
		this.aggregator = aggregator;
	}

	public void setReleaser(ReleaseService releaser) {
		this.releaser = releaser;
	}

}
