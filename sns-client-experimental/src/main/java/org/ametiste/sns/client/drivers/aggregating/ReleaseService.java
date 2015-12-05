package org.ametiste.sns.client.drivers.aggregating;


import org.ametiste.sns.client.model.Report;

import java.util.List;

public interface ReleaseService extends Runnable {
	
	//should be called whenever some implemented condition is completed, for example, 
	//every 5 minutes, or when some amount of aggregated reports achieved, or at the 
	//phase of moon
	void releaseReports(List<Report> reports);
	
}
