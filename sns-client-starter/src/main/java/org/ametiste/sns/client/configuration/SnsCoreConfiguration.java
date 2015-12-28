package org.ametiste.sns.client.configuration;

import org.ametiste.sns.client.creators.InjectingReportCreator;
import org.ametiste.sns.client.creators.injectors.model.EnvironmentData;
import org.ametiste.sns.client.drivers.ReportServiceDriver;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;


import java.util.List;

/**
 * Created by atlantis on 12/28/15.
 */
@Configuration
@Import({SnsDriverConfiguration.class, SnsCoreEnvironmentConfiguration.class})
public class SnsCoreConfiguration {

    @Autowired
    private ReportServiceDriver driver;

    @Autowired
    private List<EnvironmentData> environments;

    @Bean
    public InjectingReportCreator injectingReportCreator() {
        return new InjectingReportCreator(driver, environments);
    }
}
