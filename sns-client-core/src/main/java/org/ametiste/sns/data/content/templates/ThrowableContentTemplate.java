package org.ametiste.sns.data.content.templates;

import org.ametiste.sns.client.model.ReportContext;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ThrowableContentTemplate {

	public static final String ERROR_CONTENT_TYPE = "com.dph.reports.content.contrib.ThrowableContent";

	private final Map<String, Serializable> map;

	private ReportContext context;
	private String stackTrace;

	private boolean isBuild;

	private Class<? extends Throwable> exceptionClass;

	private ThrowableContentTemplate() {
		map = new HashMap<>();
	}

	public static ThrowableContentTemplate createContent(Throwable error) {
		ThrowableContentTemplate template = new ThrowableContentTemplate();
		template.appendError(error);
		return template;
	}

	private void appendError(Throwable error) {

		this.stackTrace = ExceptionUtils.getStackTrace(error);
		this.exceptionClass = error.getClass();

	}

	public ThrowableContentTemplate appendContext(ReportContext context) {

		this.context = context;
		return this;

	}

	public HashMap<String, Serializable> build() {

		if (isBuild) {
			throw new IllegalStateException("Report already built.");
		}

		if (context != null && !context.getContext().isEmpty()) {
			map.put("context", context.getContext());
		}

		map.put("stacktrace", stackTrace);
		map.put("errorClass", exceptionClass);
		
		try {
			return new HashMap<>(map);
		} finally {
			isBuild = true;
		}
	}

}