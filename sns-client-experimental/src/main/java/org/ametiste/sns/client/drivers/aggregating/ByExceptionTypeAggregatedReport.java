package org.ametiste.sns.client.drivers.aggregating;


import org.ametiste.sns.data.content.templates.HttpRequestContentTemplate;
import org.ametiste.sns.data.content.templates.AggregatingContentTemplate;
import org.ametiste.sns.client.model.Report;
import org.ametiste.sns.client.model.ReportContext;

import java.io.Serializable;
import java.util.*;

public class ByExceptionTypeAggregatedReport implements AggregatedReport {

	private static final String CONTENT_TYPE = HttpRequestContentTemplate.CONTENT_TYPE;
	private final Class<? extends Throwable> errorClass;
	private final List<Date> timestamps;
	private final Report report;
	private final ReportContext context;

	public ByExceptionTypeAggregatedReport(Report report) {
		context = new ReportContext();
		if (!report.getReportType().equals(CONTENT_TYPE)) {
			throw new IllegalArgumentException("This Content cant be aggregated by this type of StoredReport");
		}
		HashMap<String, Serializable> map  = this.convert(report);
		this.timestamps = new ArrayList<>();
		this.timestamps.add(report.getDate());
		this.errorClass = (Class<? extends Throwable>) map.get("errorClass");
		this.report = report;
	}
	
	private HashMap<String, Serializable> convert(Report report) {
		
		if (!report.getReportType().equals(CONTENT_TYPE)) {
			throw new IllegalArgumentException("This Content cant be aggregated by this type of StoredReport");
		}
		if(!HashMap.class.equals(report.getReportContent().getClass())) { //TODO isAccessibleBy in future
			throw new IllegalArgumentException("this should be HashMap");
		}
		return (HashMap<String, Serializable>) report.getReportContent();
	}

	@Override
	public boolean handleStoreAttempt(Report report) {
		HashMap<String, Serializable> map  = this.convert(report);
		if(map.get("errorClass").equals(this.errorClass)) {
			timestamps.add(report.getDate());
			return true;
		}

		return false;
	}

	@Override
	public Report buildReport() {
		HashMap<String, Serializable> content = 
				AggregatingContentTemplate.createContent(report)
				.appendCounter(timestamps.size()).appendContext(context).build();
		return new Report(UUID.randomUUID(), new Date(), 
				AggregatingContentTemplate.AGGREGATING_CONTENT_TYPE, 
				report.getReportSender(), content);
	}

	@Override
	public void addContext(String name, Serializable value) {
		this.context.append(name, value);

	}

}
