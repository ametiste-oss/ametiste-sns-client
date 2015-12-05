package org.ametiste.sns.client.drivers.aggregating;


import org.ametiste.sns.client.drivers.ReportServiceDriver;
import org.ametiste.sns.client.drivers.aggregating.*;
import org.ametiste.sns.client.model.Report;
import org.ametiste.sns.data.content.templates.HttpRequestContentTemplate;
import org.ametiste.sns.data.content.templates.ThrowableContentTemplate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AggregatingReportDriverTest {

	private static final String CONTENT_TYPE = HttpRequestContentTemplate.CONTENT_TYPE;

	private static final String SENDER_TEST = "sender.test";

	private InMemoryReportAggregator aggregator;

	private ByTimerReleaseService releaser;

	private AggregatingReportDriver driver;

	private StoredReportFactory factory;

	@Mock
	private ReportServiceDriver releaseDriver;

	private Thread releaserThread;

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);

		factory = new ByExceptionTypeReportFactory();
		aggregator = new InMemoryReportAggregator();
		aggregator.setFactory(factory);
		aggregator.setReleasePeriod(1000);
		aggregator.setReportsCapacity(10);

		releaser = new ByTimerReleaseService();
		releaser.setAggregator(aggregator);
		releaser.setReleasePeriod(50);
		releaser.setDriver(releaseDriver);

		releaserThread = new Thread(releaser);
		releaserThread.start();
		driver = new AggregatingReportDriver();
		driver.setAggregator(aggregator);
		driver.setReleaser(releaser);
	}

	@After
	public void shutdown() {
		releaserThread.interrupt();
	}

	@Test(timeout = 900)
	public void testCreateNewReportNoDelay() throws Exception {
		Report report = new Report(UUID.randomUUID(), new Date(), CONTENT_TYPE,
				SENDER_TEST, ThrowableContentTemplate.createContent(new IllegalArgumentException("some error"))
				.build());
		
		for (int i = 0; i < 10; i++) {
			driver.createNewReport(report.getReportId(), new Date(), report.getReportType(), report.getReportSender(),
					report.getReportContent());
		}

		verify(releaseDriver, times(0)).createNewReport(any(UUID.class), any(Date.class), anyString(), anyString(),
				any(Serializable.class));

	}

	@Test
	public void testCreateNewReportWithDelay() throws Exception {
		Report report = new Report(UUID.randomUUID(), new Date(), CONTENT_TYPE,
				SENDER_TEST, ThrowableContentTemplate.createContent(new IllegalArgumentException("some error"))
						.build());

		for (int i = 0; i < 10; i++) {
			driver.createNewReport(report.getReportId(), new Date(), report.getReportType(), report.getReportSender(),
					report.getReportContent());
		}

		long start = System.currentTimeMillis();
		Thread.sleep(2010);
		long end = System.currentTimeMillis();
		if (end - start > 2010) {
			verify(releaseDriver, times(1)).createNewReport(any(UUID.class), any(Date.class), anyString(), anyString(),
				any(Serializable.class));
		}

	}

	@Test
	public void testCreateNewReportWithExceedReportNumber() throws Exception {
		Report report = new Report(UUID.randomUUID(), new Date(), CONTENT_TYPE, SENDER_TEST, ThrowableContentTemplate
				.createContent(new IllegalArgumentException("some error"))
						.build());

		for (int i = 0; i < 20; i++) {
			driver.createNewReport(report.getReportId(), new Date(), report.getReportType(), report.getReportSender(),
					report.getReportContent());
		}

		Thread.sleep(1310);

		ArgumentCaptor<Serializable> argumentCaptor = ArgumentCaptor.forClass(Serializable.class);
		// ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);

		verify(releaseDriver, times(1)).createNewReport(any(UUID.class), any(Date.class), anyString(), anyString(),
				argumentCaptor.capture());

		HashMap<String, Serializable> map =

		(HashMap<String, Serializable>) argumentCaptor.getValue();
		assertEquals(10, map.get("reportsAmount"));


	}

	@Test
	public void testCreateNewReportWithDifferentReports() throws Exception {
		Report report = new Report(UUID.randomUUID(), new Date(), CONTENT_TYPE,
				SENDER_TEST, ThrowableContentTemplate.createContent(new NullPointerException("some error"))
						.build());
		Report report2 = new Report(UUID.randomUUID(), new Date(), CONTENT_TYPE,
				SENDER_TEST, ThrowableContentTemplate.createContent(new IllegalArgumentException("some error"))
						.build());

		for (int i = 0; i < 7; i++) {
			driver.createNewReport(report.getReportId(), new Date(), report.getReportType(), report.getReportSender(),
					report.getReportContent());
			driver.createNewReport(report2.getReportId(), new Date(), report2.getReportType(),
					report2.getReportSender(), report2.getReportContent());
		}

		Thread.sleep(1310);

		ArgumentCaptor<Serializable> argumentCaptor = ArgumentCaptor.forClass(Serializable.class);

		verify(releaseDriver, times(2)).createNewReport(any(UUID.class), any(Date.class), anyString(), anyString(),
				argumentCaptor.capture());

		HashMap<String, Serializable> map =

		(HashMap<String, Serializable>) argumentCaptor.getAllValues().get(0);
		assertEquals(5, map.get("reportsAmount"));

	}

}
