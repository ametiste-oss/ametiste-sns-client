package org.ametiste.sns.client.creators.injectors.model;


import org.ametiste.sns.client.creators.injectors.DataInjector;
import org.ametiste.sns.client.creators.injectors.EnvironmentInjector;
import org.ametiste.sns.client.model.Report;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by ametiste on 8/19/15.
 */
public class ReportBuilder implements DataInjector, EnvironmentInjector {

    private UUID id;
    private String type;
    private Date date;
    private String sender;
    private HashMap<String, Object> context;

    public ReportBuilder() {
        context = new HashMap<>();
    }

    @Override
    public ReportBuilder injectId(UUID id) {
        this.id = id;
        return this;
    }

    @Override
    public ReportBuilder injectType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public ReportBuilder injectContextEntry(String name, Object value) {
        if(context.containsKey(name)) {
            throw new IllegalArgumentException("Context already has entry with name: " + name + ". Check configuration and build flow");
        }
        context.put(name,value);
        return this;
    }

    @Override
    public ReportBuilder injectDate(Date date) {
        this.date = date;
        return this;
    }

    @Override
    public ReportBuilder injectSender(String sender) {
        this.sender = sender;
        return this;
    }


    public Report buildReport() {

        if(id ==null ) {
            throw new IllegalArgumentException("Id isnt injected. Report cant be built, since build isnt finished. Check configuration and build flow");
        }

        if(date ==null ) {
            throw new IllegalArgumentException("Date isnt injected. Report cant be built, since build isnt finished. Check configuration and build flow");
        }

        if(type ==null ) {
            throw new IllegalArgumentException("Type isnt injected. Report cant be built, since build isnt finished. Check configuration and build flow");
        }

        if(sender ==null ) {
            throw new IllegalArgumentException("Sender isnt injected. Report cant be built, since build isnt finished. Check configuration and build flow");
        }

        return new Report(id, date, type, sender, context);
    }

}
