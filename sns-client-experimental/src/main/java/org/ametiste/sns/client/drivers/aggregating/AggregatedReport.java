package org.ametiste.sns.client.drivers.aggregating;


import org.ametiste.sns.client.model.Report;

import java.io.Serializable;

public interface AggregatedReport {
	
	//exact reason why i want this as interface, cause canAggregate can be various, and maybe increment aswell

	void addContext(String name, Serializable value);

	boolean handleStoreAttempt(Report report);

	Report buildReport();
	
	//contains report itself, and data for aggregation, for now i see some counter and maybe timestamp
	
}
