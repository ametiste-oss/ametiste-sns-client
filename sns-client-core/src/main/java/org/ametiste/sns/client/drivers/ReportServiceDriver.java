package org.ametiste.sns.client.drivers;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * <p>
 * A low-level {@code Report Service} driver interface. Provides access to the raw and a minimal RS API.
 * </p>
 * 
 * <p>
 * Can be used for building high-level clients upon the. 
 * </p>
 * 
 * <p>
 * Don't use it directly, it will be hard to maintenance.
 * Should be treat as an <i>abstraction</i> part of the GoF Bridge Pattern.
 * </p>
 * 
 * <p>
 * Out the box there one implementation for the DRS - {@link SnsReportRESTfulServiceDriver}.
 * </p>
 * 
 * @author masted
 * @since 0.1.0
 */
public interface ReportServiceDriver {

	/**
	 * <p>
	 * Creates new report on the service usin given parameters. 
	 * </p>
	 * 
	 * @param reportId identifier of the new report
	 * @param date date where report were dated
	 * @param reportType custom report type, concrete drivers may restrict a values.
	 * @param reportSender subject who sent the report
	 * @param reportContent the report content
	 */
	void createNewReport(UUID reportId, Date date,
						 String reportType, String reportSender, Serializable reportContent);
	
}
