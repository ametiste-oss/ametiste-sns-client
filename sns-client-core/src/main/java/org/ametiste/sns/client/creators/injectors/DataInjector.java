package org.ametiste.sns.client.creators.injectors;

import java.util.UUID;

/**
 * Created by ametiste on 8/19/15.
 */
public interface DataInjector {

    DataInjector injectId(UUID id);
    DataInjector injectType(String type);
    DataInjector injectContextEntry(String name, Object value);

}
