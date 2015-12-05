package org.ametiste.sns.client.drivers.aggregating;

import org.ametiste.sns.client.model.Report;

public interface StoredReportFactory {
	
	AggregatedReport create(Report report);

}
