package org.ametiste.sns.client.drivers.multithread;


import org.ametiste.sns.client.drivers.ReportServiceDriver;
import org.ametiste.sns.client.model.Report;

public class ReportRunner implements Runnable {


	private final ReportServiceDriver driver;
	private final Report report;

	public ReportRunner(ReportServiceDriver driver, Report report) {
		this.driver = driver;
		this.report = report;

	}

	@Override
	public void run() {
		try {
			this.driver.createNewReport(report.getReportId(), report.getDate(), report.getReportType(),
				report.getReportSender(), report.getReportContent());
		} catch (Exception e) {

		}

	}

}
