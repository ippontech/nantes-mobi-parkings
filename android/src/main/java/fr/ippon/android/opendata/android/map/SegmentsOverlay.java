package fr.ippon.android.opendata.android.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import fr.ippon.android.opendata.android.MainApplication;
import fr.ippon.android.opendata.data.traffic.IE6GeoPoint;
import fr.ippon.android.opendata.data.traffic.Segment;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.PathOverlay;

import java.util.List;

/**
 * User: nicolasguillot
 * Date: 06/05/12
 * Time: 19:16
 */
public class SegmentsOverlay extends PathOverlay {
    private String segmentId;

    public SegmentsOverlay(Context ctx, Segment seg) {
        super(Color.GRAY, ctx);
        getPaint().setAntiAlias(true);

        segmentId = seg.getName();
        for (IE6GeoPoint c : seg.getCoords()) {
            this.addPoint(c.getLat(), c.getLgt());
        }
    }

    public String getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }
}
