package org.ametiste.sns.client.creators.injectors.support.environment;


import org.ametiste.sns.client.creators.injectors.model.EnvironmentData;
import org.ametiste.sns.client.creators.injectors.EnvironmentInjector;

import java.util.Date;

/**
 * Created by ametiste on 8/20/15.
 */
public class CurrentTimeEnvironmentData implements EnvironmentData {


    @Override
    public void inject(EnvironmentInjector injector) {
        injector.injectDate(new Date());
    }
}
