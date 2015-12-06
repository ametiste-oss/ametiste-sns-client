package org.ametiste.sns.client.model;

import java.io.Serializable;
import java.util.HashMap;

public class ReportContext implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4412437008984445117L;

	private final HashMap<String, Serializable> context;

	public ReportContext() {
		context = new HashMap<>();
	}

	public ReportContext append(String name, Serializable value) {
		context.put(name, value);
		return this;
	}

	public HashMap<String, Serializable> getContext() {
		return this.context;
	}
}
