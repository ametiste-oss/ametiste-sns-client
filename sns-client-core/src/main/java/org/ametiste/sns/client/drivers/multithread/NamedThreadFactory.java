package org.ametiste.sns.client.drivers.multithread;

import java.util.concurrent.ThreadFactory;

public class NamedThreadFactory implements ThreadFactory {

	private final String prefix;

	private int counter = 0;

	public NamedThreadFactory(String threadName) {
		this.prefix = threadName;
	}

	@Override
	public Thread newThread(Runnable r) {
		return new Thread(r, prefix + "-" + counter++);
	}


}
