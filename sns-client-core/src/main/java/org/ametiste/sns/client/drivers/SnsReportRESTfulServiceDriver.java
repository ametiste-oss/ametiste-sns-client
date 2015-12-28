package org.ametiste.sns.client.drivers;


import org.ametiste.ifaces.protocol.Protocol;
import org.ametiste.ifaces.protocol.http.HttpProtocolMessage;
import org.ametiste.sns.client.drivers.protocol.RestfulReportCreationProtocol;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * Implementation of the {@link ReportServiceDriver} that use {@link RestTemplate}
 * to communicate with the {@code SNS Report Service} through REST API.
 * 
 * @author masted
 * @since 0.1.0
 */
public class SnsReportRESTfulServiceDriver implements ReportServiceDriver {

	public static final String DEFAULT_SERVICE_HOST = "localhost"; //TODO consider default
	
	public static final RestfulReportCreationProtocol DEFAULT_PROTOCOL = new RestfulReportCreationProtocol("");
	
	private final RestTemplate restTemplate;
	
	private final String serviceHost;

	private final Protocol<ReportMessage, HttpProtocolMessage<Map<String, Object>>> protocol;

	public SnsReportRESTfulServiceDriver(String serviceHost, RestTemplate restTemplate,
										 Protocol<ReportMessage, HttpProtocolMessage<Map<String, Object>>> protocol) {
		
		if (serviceHost == null) {
			throw new IllegalArgumentException("Service host can't be null.");
		}
		
		if (restTemplate == null) {
			throw new IllegalArgumentException("RestTemplate can't be null");
		}
		
		if (protocol == null) {
			throw new IllegalArgumentException("Protocol can't be null.");
		}
		
		this.serviceHost = serviceHost;
		this.restTemplate = restTemplate;
		this.protocol = protocol;
	}
	
	public SnsReportRESTfulServiceDriver(RestTemplate restTemplate) {
		this(DEFAULT_SERVICE_HOST, restTemplate, DEFAULT_PROTOCOL);
	}
	
	@Override
	public void createNewReport(UUID reportId, Date date, String reportType,
			String reportSender, Serializable reportContent) {
		
		ReportMessage reportMessage = 
			new ReportMessage(reportId, date, reportType, reportSender, reportContent);
		
		HttpProtocolMessage<Map<String, Object>> protocolMessage = protocol.buildMessage(reportMessage);
		if(!protocolMessage.getMethod().equals("PUT")) {
			throw new UnsupportedMethodException("Illegal method exception.");
		}
		
		HttpHeaders httpHeaders = new HttpHeaders();
		
		for (Entry<String, List<String>> header : protocolMessage.getHeaders().entrySet()) {
			for (String value : header.getValue()) {
				httpHeaders.add(header.getKey(), value);
			}
		}
		
		HttpEntity<Map<String, Object>> requestEntity =
				new HttpEntity<>(protocolMessage.getBody(), httpHeaders);
		restTemplate.put("http://" + serviceHost + protocolMessage.getPath(), requestEntity);
	}

}
