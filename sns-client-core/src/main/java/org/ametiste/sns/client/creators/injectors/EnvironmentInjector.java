package org.ametiste.sns.client.creators.injectors;

import java.util.Date;

/**
 * Created by ametiste on 8/19/15.
 */
public interface EnvironmentInjector {

    EnvironmentInjector injectDate(Date date);
    EnvironmentInjector injectSender(String sender);
    EnvironmentInjector injectContextEntry(String name, Object value);
}
