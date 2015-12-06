package org.ametiste.sns.client.drivers.multithread;

import org.ametiste.metrics.annotations.Countable;
import org.ametiste.sns.client.drivers.ReportServiceDriver;
import org.ametiste.sns.client.model.Report;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class LimitableThreadPoolWrapperDriver implements ReportServiceDriver {

    private final ExecutorService executor;
    private final ReportServiceDriver driver;
    private String snsNamespace;

    public LimitableThreadPoolWrapperDriver(ReportServiceDriver driver,
                                            String threadName,
                                            String snsNamespace,
                                            int threadsNumber,
                                            int queueCapacity) {

        //TODO checks
        this.driver = driver;
        this.snsNamespace = snsNamespace;
        executor = new ThreadPoolExecutor(threadsNumber, threadsNumber,0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(queueCapacity),
                new NamedThreadFactory(threadName) );
    }

    @Override
    @Countable(nameSuffixExpression="target.getSnsNamespace() + '.reports.count'")
    public void createNewReport(UUID reportId, Date date, String reportType, String reportSender,
                                Serializable reportContent) {
        Report report = new Report(reportId, date, reportType, reportSender, reportContent);
        executor.execute(new ReportRunner(driver, report));

    }

    public String getSnsNamespace() {
        return snsNamespace;
    }

}
