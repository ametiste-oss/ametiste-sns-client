package org.ametiste.sns.client.drivers.mock;


import org.ametiste.sns.client.drivers.ReportMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by ametiste on 7/15/15.
 */
public class MockReportContainer {

    private List<ReportMessage> allReports;

    public MockReportContainer(List<ReportMessage> reports) {

        this.allReports = reports;
    }

    public MockReportContainer withType(String type) {

        this.allReports = allReports.stream()
                .filter(reportMessage -> reportMessage.getType().equals(type))
                .collect(Collectors.toList());
        return this;
    }

    public MockReportContainer withRequestId(UUID id) {

        this.allReports = allReports.stream()
                .filter(reportMessage -> reportMessage.getReportId().equals(id))
                .collect(Collectors.toList());

        return this;
    }

    public MockReportContainer withSender(String sender) {

        this.allReports = allReports.stream()
                .filter(reportMessage -> reportMessage.getSender().equals(sender))
                .collect(Collectors.toList());
        return this;
    }


    /**
     * This method should be used with caution, only if content of tested report is compatible with
     * {@code Map<String, Object>} type. Otherwise the key cant be found and other map types cant be predicted
     * @param key
     * @return
     */
    public MockReportContainer withContentContains(String key) {

        List<ReportMessage> selectedReports = new ArrayList<>(allReports.size());

        //do nothing
        allReports.stream()
                .filter(reportMessage -> Map.class.isAssignableFrom(reportMessage.getContent().getClass()))
                .forEach(reportMessage -> {
                    try {
                        //TODO refactor
                        Map<String, Object> map = (Map<String, Object>) reportMessage.getContent();
                        if (map.containsKey(key)) {
                            selectedReports.add(reportMessage);
                        }
                    } catch (Exception e) {
                        //do nothing
                    }
                });
        this.allReports = selectedReports;
        return this;
    }

    /**
     *
     * This method should be used with caution, only if content of tested report is compatible with
     * {@code Map<String, Object>} type. Otherwise the key cant be found and other map types cant be predicted
     *
     * @param key
     * @param value
     * @return
     */
    public MockReportContainer withContentContains(String key, Object value) {

        List<ReportMessage> selectedReports = new ArrayList<>(allReports.size());

        //do nothing
        allReports.stream()
                .filter(reportMessage -> Map.class.isAssignableFrom(reportMessage.getContent().getClass()))
                .forEach(reportMessage -> {
                    try {
                        Map<String, Object> map = (Map<String, Object>) reportMessage.getContent();
                        if (map.containsKey(key) && map.get(key).equals(value)) {
                            selectedReports.add(reportMessage);
                        }
                    } catch (Exception e) {
                        //do nothing
                    }
        });
        this.allReports = selectedReports;
        return this;
    }

    public void wasRegistered(int times) {
        if(this.allReports.size()!=times) {
            throw new AssertionError("Expected number of selected reports is " + times + " but was " + allReports.size());
        }
    }

    public void wasRegistered() {
        if(this.allReports.size()==0) {
            throw new AssertionError("Report was expected to be registered but wasnt ");
        }
    }

    public void wasntRegistered() {
        if(this.allReports.size()!=0) {
            throw new AssertionError("Report wasnt expected to be registered but was registered " + allReports.size() + " times");
        }
    }

}
