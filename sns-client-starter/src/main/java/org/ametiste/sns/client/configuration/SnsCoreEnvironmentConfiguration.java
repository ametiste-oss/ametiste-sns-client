package org.ametiste.sns.client.configuration;

import org.ametiste.sns.client.creators.injectors.model.EnvironmentData;
import org.ametiste.sns.client.creators.injectors.support.environment.CurrentTimeEnvironmentData;
import org.ametiste.sns.client.creators.injectors.support.environment.SenderEnvironmentData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by atlantis on 12/28/15.
 */
@Configuration
@EnableConfigurationProperties(SnsProperties.class)
public class SnsCoreEnvironmentConfiguration {

    @Autowired
    private SnsProperties props;

    @Bean
    public EnvironmentData senderData() {
        return new SenderEnvironmentData(props.getSender());
    }

    @Bean
    public EnvironmentData currentTimeData () {
        return new CurrentTimeEnvironmentData();
    }
}
