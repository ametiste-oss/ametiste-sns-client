package org.ametiste.sns.data.content.templates;

import org.springframework.util.LinkedMultiValueMap;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.HashMap;

public class HttpRequestContentTemplate {

	public static final String CONTENT_TYPE = "com.dph.reports.content.contrib.HttpRequestContent";
		
	private final LinkedMultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
	
	private String method;

	private String path;

	private String query;

	private boolean isBuild;

	private final HashMap<String, Serializable> content;
	
	public HttpRequestContentTemplate() {
		 content = new HashMap<>();
	}
	
	public HttpRequestContentTemplate appendHeader(String name, String value) {
		headers.add(name, value);
		return this;
	}
	
	public HttpRequestContentTemplate appendMethod(String method) {
		this.method = method;
		return this;
	}
	
	public HttpRequestContentTemplate appendPath(String path) {
		this.path = path;
		return this;
	}
	
	public HttpRequestContentTemplate appendQuery(String query) {
		this.query = query;
		return this;
	}
	
	public HttpRequestContentTemplate appendCustomField(String name, Serializable value) {
		content.put(name, value);
		return this;
	}
	
	public HashMap<String, Serializable> build() {
		
		if (isBuild) {
			throw new IllegalStateException("Report already built.");
		}
		
		if (method == null) {
			throw new IllegalStateException("Request should have method.");
		}
		
		if (path == null) {
			throw new IllegalStateException("Request should have path");
		}
		
		content.put("method", method);
		content.put("path", path);
		
		if (query != null && !query.isEmpty()) {
			content.put("query", query);
		}
		
		if (headers.size() > 0) {
			content.put("headers", headers);
		}
		
		try {
			return new HashMap<>(content);
		} finally {
			isBuild = true;
		}
	}

	public static HttpRequestContentTemplate createTemplate() {
		return new HttpRequestContentTemplate();
	}

	public static HttpRequestContentTemplate createTemplateFromRequest(HttpServletRequest request) {
		
		HttpRequestContentTemplate contentTemplate = HttpRequestContentTemplate.createTemplate()
				.appendMethod(request.getMethod())
				.appendPath(request.getRequestURI())
				.appendQuery(request.getQueryString());
	    	
		@SuppressWarnings("unchecked")
		Enumeration<String> headerNames = request.getHeaderNames();
		
        while (headerNames.hasMoreElements()) {
            final String headerName = headerNames.nextElement();
            contentTemplate.appendHeader(headerName, request.getHeader(headerName));
        }
        
        return contentTemplate;
        
	}
	
	public static HashMap<String, Serializable> createContentFromRequest(HttpServletRequest request) {
		return createTemplateFromRequest(request).build();
	}
	
}
