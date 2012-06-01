package fr.ippon.android.opendata.android.service;

import fr.ippon.android.opendata.data.traffic.Segment;
import java.util.concurrent.Callable;

/**
 * User: nicolasguillot
 * Date: 15/05/12
 * Time: 21:41
 */
public class BackgroundCityCenterSegmentColorUpdaterTask extends
        AbstractServiceWithStatus<TaskData<Segment>> {

    public BackgroundCityCenterSegmentColorUpdaterTask() {
        super("BackgroundFreewaySegmentColorUpdaterService");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Callable<TaskData<Segment>> createTask() {
        return new CityCenterSegmentColorUpdaterTask(getApplicationContext());
    }
}
