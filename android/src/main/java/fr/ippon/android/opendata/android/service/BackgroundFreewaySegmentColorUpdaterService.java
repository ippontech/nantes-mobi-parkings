package fr.ippon.android.opendata.android.service;

import java.util.concurrent.Callable;

import fr.ippon.android.opendata.data.traffic.Segment;

/**
 * User: nicolasguillot Date: 06/05/12 Time: 15:37
 */
public class BackgroundFreewaySegmentColorUpdaterService extends
		AbstractServiceWithStatus<TaskData<Segment>> {

	public BackgroundFreewaySegmentColorUpdaterService() {
		super("BackgroundFreewaySegmentColorUpdaterService");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Callable<TaskData<Segment>> createTask() {
		return new FreewaySegmentColorUpdaterTask(getApplicationContext());
	}
}
