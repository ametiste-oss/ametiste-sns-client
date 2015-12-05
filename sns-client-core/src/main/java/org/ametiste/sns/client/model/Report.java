package org.ametiste.sns.client.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Report implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final UUID reportId;
	private final Date date;
	private final String reportType;
	private final String reportSender;
	private final Serializable reportContent;

	public Report(UUID reportId, Date date, String reportType, String reportSender, Serializable reportContent) {
		this.reportId = reportId;
		this.date = date;
		this.reportType = reportType;
		this.reportSender = reportSender;
		this.reportContent = reportContent;

	}

	public UUID getReportId() {
		return reportId;
	}

	public Date getDate() {
		return date;
	}

	public String getReportType() {
		return reportType;
	}

	public String getReportSender() {
		return reportSender;
	}

	public Serializable getReportContent() {
		return reportContent;
	}

}
