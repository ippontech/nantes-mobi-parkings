package fr.ippon.android.opendata.android.content.convert;

import android.content.ContentValues;
import fr.ippon.android.opendata.android.content.SegmentDao;
import fr.ippon.android.opendata.data.trafficfluency.modele.FreewaySegmentFluency;

import java.text.SimpleDateFormat;

import static fr.ippon.android.opendata.android.content.SegmentColorTableDescription.LAST_UPDATE_SEG;
import static fr.ippon.android.opendata.android.content.SegmentColorTableDescription.SEGMENT_ID;
import static fr.ippon.android.opendata.android.content.SegmentColorTableDescription.COLOR_ID;

/**
 * User: nicolasguillot
 * Date: 06/05/12
 * Time: 00:37
 */
public class FreewaySegmentFluencyConverter implements ContentValueConverter<FreewaySegmentFluency> {
    @Override
    public ContentValues getValuesFrom(FreewaySegmentFluency obj) {
        SimpleDateFormat sdf = new SimpleDateFormat(SegmentDao.LAST_UPDATE_FORMAT);
        ContentValues cv = new ContentValues();
        cv.put(SEGMENT_ID, obj.getLocationId());
        cv.put(COLOR_ID, ColorId.getColorIdFromTrafficStatus(obj.getTrafficStatus()));
        cv.put(LAST_UPDATE_SEG, sdf.format(obj.getTime()));

        return cv;
    }
}
