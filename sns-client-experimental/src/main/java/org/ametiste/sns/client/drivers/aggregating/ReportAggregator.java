package org.ametiste.sns.client.drivers.aggregating;


import org.ametiste.sns.client.model.Report;

import java.util.List;

public interface ReportAggregator {
	
	List<AggregatedReport> releaseReports();
	void addReport(Report report);

	boolean canRelease();

	boolean canAdd();

}
