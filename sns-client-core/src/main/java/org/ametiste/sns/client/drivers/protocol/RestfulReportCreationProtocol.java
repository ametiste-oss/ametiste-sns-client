package org.ametiste.sns.client.drivers.protocol;


import org.ametiste.ifaces.protocol.Protocol;
import org.ametiste.ifaces.protocol.http.HttpProtocolMessage;
import org.ametiste.sns.client.drivers.ReportMessage;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestfulReportCreationProtocol implements
		Protocol<ReportMessage, HttpProtocolMessage<Map<String, Object>>> {

	private final Map<String, List<String>> headers = new HashMap<>();
	
	public RestfulReportCreationProtocol() {
		headers.put("Content-Type", Collections.singletonList("application/json"));
		headers.put("Accept", Collections.singletonList("application/json"));
	}
	
	@Override
	public HttpProtocolMessage<Map<String, Object>> buildMessage(ReportMessage message) {
		
		Map<String, Object> reportRequest = new HashMap<>();
		
		reportRequest.put("id", message.getReportId().toString());
		// TODO: It should be set as X-Dph-Report-Tag = "operation_id=report.operation_id"
		reportRequest.put("date", message.getDate().getTime());
		reportRequest.put("type", message.getType());
		reportRequest.put("sender", message.getSender());
		reportRequest.put("content", message.getContent());

		HttpProtocolMessage<Map<String, Object>> httpProtocolMessage = new HttpProtocolMessage<>(
			"PUT", 
			"/" + message.getReportId().toString(),
				//TODO hardcoded to root, should be configured, but not on revamp stage
			headers, 
			null, 
			reportRequest
		);
		
		return httpProtocolMessage;
	}

}
