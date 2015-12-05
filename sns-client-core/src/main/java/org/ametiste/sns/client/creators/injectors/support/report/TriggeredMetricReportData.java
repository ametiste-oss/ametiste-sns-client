package org.ametiste.sns.client.creators.injectors.support.report;


import org.ametiste.sns.data.content.templates.HttpRequestContentTemplate;
import org.ametiste.sns.client.creators.injectors.DataInjector;
import org.ametiste.sns.client.creators.injectors.model.ReportData;
import org.ametiste.sns.data.content.templates.TriggeredMetricsContentTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * Created by ametiste on 8/21/15.
 */
public class TriggeredMetricReportData implements ReportData {

    private final UUID reportId;
    private final String methodName;
    private final long delta;
    private final HttpServletRequest request;

    public TriggeredMetricReportData(UUID reportId, String methodName, long delta, HttpServletRequest request) {

        this.reportId = reportId;
        this.methodName = methodName;
        this.delta = delta;
        this.request = request;
    }
    @Override
    public void inject(DataInjector injector) {
        injector.injectId(reportId).injectType(TriggeredMetricsContentTemplate.CONTENT_TYPE);
        HttpRequestContentTemplate.createContentFromRequest(request).entrySet()
                .forEach(entry -> injector.injectContextEntry(entry.getKey(), entry.getValue()));
        injector.injectContextEntry("time_of_request_in_ms", Long.toString(delta))
                .injectContextEntry("metric_name", methodName);
    }
}
