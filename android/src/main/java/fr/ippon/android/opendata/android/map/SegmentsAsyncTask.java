package fr.ippon.android.opendata.android.map;

import android.content.Context;
import fr.ippon.android.opendata.android.R;
import fr.ippon.android.opendata.data.traffic.Segment;
import roboguice.util.RoboAsyncTask;

import java.io.InputStream;
import java.util.List;

/**
 * User: nicolasguillot
 * Date: 16/05/12
 * Time: 23:22
 */
public class SegmentsAsyncTask extends RoboAsyncTask<List<Segment>> {

    private CustomMapView mapView;

    public SegmentsAsyncTask(Context context, CustomMapView mapView) {
        super(context);
        this.mapView = mapView;
    }

    protected List<Segment> getSegementsWithLineString() {
        InputStream raw = getContext().getResources()
                .openRawResource(R.raw.segments);
        return Segment.parseSegments(raw);
    }

    @Override
    public List<Segment> call() throws Exception {
        return getSegementsWithLineString();
    }

    @Override
    protected void onSuccess(List<Segment> result) {
        mapView.buildSegmentsOverlays(result);
    }
}
