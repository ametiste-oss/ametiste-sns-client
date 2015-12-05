package org.ametiste.sns.client.creators.injectors.support.environment;


import org.ametiste.sns.client.creators.injectors.model.EnvironmentData;
import org.ametiste.sns.client.creators.injectors.EnvironmentInjector;

/**
 * Created by ametiste on 8/20/15.
 */
public class SenderEnvironmentData implements EnvironmentData {

    private String sender;

    public SenderEnvironmentData(String sender) {
        if(sender==null) {
            throw new IllegalArgumentException("Sender identifier cant be null");
        }
        this.sender = sender;
    }

    @Override
    public void inject(EnvironmentInjector injector) {
        injector.injectSender(sender);
    }
}
