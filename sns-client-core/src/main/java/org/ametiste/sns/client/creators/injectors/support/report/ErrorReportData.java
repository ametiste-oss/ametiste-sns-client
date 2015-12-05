package org.ametiste.sns.client.creators.injectors.support.report;

import org.ametiste.sns.client.creators.injectors.DataInjector;
import org.ametiste.sns.client.model.ReportContext;
import org.ametiste.sns.client.creators.injectors.model.ReportData;
import org.ametiste.sns.data.content.templates.ThrowableContentTemplate;

import java.util.UUID;

/**
 * Created by ametiste on 8/21/15.
 */
public class ErrorReportData implements ReportData {

    private final UUID reportId;
    private final Throwable reason;
    private final ReportContext context;

    public ErrorReportData(UUID reportId, Throwable reason, ReportContext context) {

        this.reportId = reportId;
        this.reason = reason;
        this.context = context;
    }

    @Override
    public void inject(DataInjector injector) {

        injector.injectId(reportId).injectType(ThrowableContentTemplate.ERROR_CONTENT_TYPE);
        ThrowableContentTemplate.createContent(reason).appendContext(context).build().entrySet().forEach(entry ->
            injector.injectContextEntry(entry.getKey(),entry.getValue())
        );
    }
}
