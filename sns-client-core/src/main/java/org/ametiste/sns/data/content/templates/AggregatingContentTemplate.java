package org.ametiste.sns.data.content.templates;



import org.ametiste.sns.client.model.ReportContext;
import org.ametiste.sns.client.model.Report;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class AggregatingContentTemplate {

	public static final String AGGREGATING_CONTENT_TYPE = "com.dph.reports.content.contrib.AggregatingContent";

	private final Map<String, Serializable> map;

	private ReportContext context;

	private boolean isBuild;


	private Report report;

	private int counter;

	private AggregatingContentTemplate() {
		map = new HashMap<>();
	}

	public static AggregatingContentTemplate createContent(Report originalReport) {
		AggregatingContentTemplate template = new AggregatingContentTemplate();
		template.appendReport(originalReport);
		return template;
	}

	private void appendReport(Report report) {

		this.report = report;

	}

	public AggregatingContentTemplate appendContext(ReportContext context) {

		this.context = context;
		return this;

	}

	public AggregatingContentTemplate appendCounter(int counter) {

		this.counter = counter;
		return this;

	}
	
	public HashMap<String, Serializable> build() {

		if (isBuild) {
			throw new IllegalStateException("Report already built.");
		}

		if (context != null && !context.getContext().isEmpty()) {
			map.put("context", context.getContext());
		}
		map.put("agregatedReport", report);
		map.put("reportsAmount", counter);
		
		try {
			return new HashMap<>(map);
		} finally {
			isBuild = true;
		}
	}

}