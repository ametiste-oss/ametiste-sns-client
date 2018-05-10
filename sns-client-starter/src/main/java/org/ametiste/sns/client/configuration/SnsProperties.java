package org.ametiste.sns.client.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by atlantis on 12/28/15.
 */
@Component
@ConfigurationProperties(prefix = SnsProperties.PROPS_PREFIX)
public class SnsProperties {

    public static final String PROPS_PREFIX = "org.ametiste.sns";

    private String host;
    private String namespace = "sns";
    private Thread thread = new Thread();
    private int capacity = 1000;
    private String sender;
    private String relativePath = "";


    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }


    public static class Thread {

        private String name = "snsReports-";
        private int number = 1;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }
}
