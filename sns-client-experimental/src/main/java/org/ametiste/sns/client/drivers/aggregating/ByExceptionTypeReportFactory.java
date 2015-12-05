package org.ametiste.sns.client.drivers.aggregating;


import org.ametiste.sns.client.model.Report;

public class ByExceptionTypeReportFactory implements StoredReportFactory {

	@Override
	public AggregatedReport create(Report report) {
		return new ByExceptionTypeAggregatedReport(report);
	}

}
