package org.ametiste.sns.client.drivers;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class ReportMessage {
	
	private final UUID reportId;
	private final String type;
	private final String sender;
	private final Serializable content;
	private final Date date;

	public ReportMessage(UUID reportId, Date date, String type, String sender, Serializable content) {
		this.reportId = reportId;
		this.date = date;
		this.type = type;
		this.sender = sender;
		this.content = content;
	}

	public UUID getReportId() {
		return reportId;
	}

	public String getType() {
		return type;
	}

	public String getSender() {
		return sender;
	}

	public Serializable getContent() {
		return content;
	}

	public Date getDate() {
		return date;
	}
	
}
