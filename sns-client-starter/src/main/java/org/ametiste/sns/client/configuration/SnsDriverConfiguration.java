package org.ametiste.sns.client.configuration;

import org.ametiste.sns.client.drivers.ReportServiceDriver;
import org.ametiste.sns.client.drivers.SnsReportRESTfulServiceDriver;
import org.ametiste.sns.client.drivers.multithread.LimitableThreadPoolWrapperDriver;
import org.ametiste.sns.client.drivers.protocol.RestfulReportCreationProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by atlantis on 12/28/15.
 */
@Configuration
@EnableConfigurationProperties(SnsProperties.class)
public class SnsDriverConfiguration {


    @Autowired
    private SnsProperties props;


    @Bean
    public ReportServiceDriver singleThreadDriver() {
        return new SnsReportRESTfulServiceDriver(props.getHost(),
                new RestTemplate(), new RestfulReportCreationProtocol(props.getRelativePath()));
    }


    @Bean(name="driver")
    @ConditionalOnMissingBean(name="driver")
    public ReportServiceDriver driver() {
        return new LimitableThreadPoolWrapperDriver(singleThreadDriver(), props.getThread().getName(),
                props.getNamespace(), props.getThread().getNumber(), props.getCapacity());
    }

}
