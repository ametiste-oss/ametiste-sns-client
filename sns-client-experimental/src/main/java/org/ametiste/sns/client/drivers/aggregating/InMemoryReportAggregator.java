package org.ametiste.sns.client.drivers.aggregating;

import org.ametiste.sns.client.model.Report;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class InMemoryReportAggregator implements ReportAggregator, InitializingBean {

	private StoredReportFactory factory;
	private int releasePeriod;
	private long lastReleaseTimestamp;
	private int reportsCapacity;

	private final List<Report> storedReports;
	
	public InMemoryReportAggregator() {
		storedReports = new ArrayList<Report>();
		lastReleaseTimestamp = System.currentTimeMillis();
	}
	
	public void setReleasePeriod(int releasePeriod) {
		this.releasePeriod = releasePeriod;
	}

	public void setFactory(StoredReportFactory factory) {
		this.factory = factory;
	}

	public void setReportsCapacity(int reportsCapacity) {
		this.reportsCapacity = reportsCapacity;
		// TODO maybe exctract this to constructor, to be able to create list with this capacity, possible to improve
		// performance with this?
	}


	@Override
	public synchronized List<AggregatedReport> releaseReports() {
		if (!this.canRelease()) {
			throw new IllegalStateException("Cant be released now");
		}
		
		//will this couple of lines sincronized enough, or we should take whole method?
		List<Report> toRelease = new ArrayList<Report>(storedReports);
		storedReports.clear();
		
		
		List<AggregatedReport> aggregatedReports = new ArrayList<AggregatedReport> ();
		for(Report report: toRelease) {
			this.aggregateReport(report, aggregatedReports);
		}
		this.lastReleaseTimestamp = System.currentTimeMillis();
		return aggregatedReports;
	}

	@Override
	public synchronized void addReport(Report report) {
		if (!this.canAdd()) {
			throw new IllegalStateException("");
		}
		storedReports.add(report);


	}

	private void aggregateReport(Report report, List<AggregatedReport> aggregatedReports) { 
		boolean aggregated=false;
		for(AggregatedReport sr: aggregatedReports) {
			if(sr.handleStoreAttempt(report)) {
				aggregated=true;
				break;
			}
		}
		if(!aggregated) {
			AggregatedReport newUniqueReport = factory.create(report);
			newUniqueReport.addContext("aggregationPeriod", releasePeriod);
			aggregatedReports.add(newUniqueReport);
		}
	}

	@Override
	public boolean canRelease() {
		return System.currentTimeMillis() - lastReleaseTimestamp > releasePeriod;
	}



	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(factory, "Builder cant be null");
		Assert.isTrue(releasePeriod != 0, "Release period should be set  in ms, and not equal 0");

	}

	@Override
	public boolean canAdd() {
		return storedReports.size() < reportsCapacity;
	}


}
