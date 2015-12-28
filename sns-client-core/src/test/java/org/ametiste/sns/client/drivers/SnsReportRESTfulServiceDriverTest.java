package org.ametiste.sns.client.drivers;


import org.ametiste.sns.client.drivers.protocol.RestfulReportCreationProtocol;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class SnsReportRESTfulServiceDriverTest {
	
	public static final String VALID_HOST = "valid.host.test";
	
	public static final String VALID_SERVICE_URI = "http://" + VALID_HOST;
	
	private static final class MockReport implements Serializable {

		private static final long serialVersionUID = 6982225110175456976L;

		public String header = "test";
		
		public String value = "case";
		
	}
	
	@Test
	public void testCreateNewReport() {
		
		UUID reportId = UUID.randomUUID();
		String reportType = "mockReport";
		String reportSender = "mockSender";
		Date date = new Date();
		
		RestTemplate restTemplate = new RestTemplate();
		MockRestServiceServer mockService = MockRestServiceServer.createServer(restTemplate);
		
		mockService.expect(requestTo(VALID_SERVICE_URI + "/" + reportId.toString()))
			.andExpect(method(HttpMethod.PUT))
			.andExpect(content()
				.contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(reportId.toString()))
			.andExpect(jsonPath("$.type").value(reportType))
			.andExpect(jsonPath("$.date").value(date.getTime()))
			.andExpect(jsonPath("$.sender").value(reportSender))
			.andExpect(jsonPath("$.content.header").value("test"))
			.andExpect(jsonPath("$.content.value").value("case"))
			.andRespond(withSuccess());
		
		SnsReportRESTfulServiceDriver client = new SnsReportRESTfulServiceDriver(
				VALID_HOST, restTemplate, new RestfulReportCreationProtocol(""));
		
		client.createNewReport(reportId, date,
				reportType, reportSender, new MockReport());
		
		mockService.verify();
		
	}
	
}
