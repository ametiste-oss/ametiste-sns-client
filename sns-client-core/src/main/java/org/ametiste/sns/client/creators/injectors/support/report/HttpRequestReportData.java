package org.ametiste.sns.client.creators.injectors.support.report;

import org.ametiste.sns.client.creators.injectors.DataInjector;
import org.ametiste.sns.client.creators.injectors.model.ReportData;
import org.ametiste.sns.data.content.templates.HttpRequestContentTemplate;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Created by ametiste on 8/21/15.
 */
public class HttpRequestReportData implements ReportData {

    private final UUID reportId;
    private final HttpServletRequest request;
    private Throwable reason;

    public HttpRequestReportData(UUID reportId, HttpServletRequest request) {

        this.reportId = reportId;
        this.request = request;
        this.reason = null;
    }

    public HttpRequestReportData(UUID reportId, HttpServletRequest request, Throwable reason) {

        this.reportId = reportId;
        this.request = request;
        this.reason = reason;
    }

    @Override
    public void inject(DataInjector injector) {
        injector.injectType(HttpRequestContentTemplate.CONTENT_TYPE).injectId(reportId);
        HttpRequestContentTemplate.createContentFromRequest(request).entrySet().forEach(entry ->
                        injector.injectContextEntry(entry.getKey(), entry.getValue())
        );
        if(reason!=null) {
            injector.injectContextEntry("stacktrace", ExceptionUtils.getStackTrace(reason))
                    .injectContextEntry("errorClass", reason.getClass());

        }

    }
}
