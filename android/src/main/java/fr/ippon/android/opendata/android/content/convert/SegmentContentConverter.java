package fr.ippon.android.opendata.android.content.convert;

import android.content.ContentValues;
import fr.ippon.android.opendata.android.content.SegmentDao;
import fr.ippon.android.opendata.data.traffic.Segment;

import java.text.SimpleDateFormat;

import static fr.ippon.android.opendata.android.content.SegmentColorTableDescription.COLOR_ID;
import static fr.ippon.android.opendata.android.content.SegmentColorTableDescription.LAST_UPDATE_SEG;
import static fr.ippon.android.opendata.android.content.SegmentColorTableDescription.SEGMENT_ID;

/**
 * User: nicolasguillot
 * Date: 06/05/12
 * Time: 15:16
 */
public class SegmentContentConverter implements ContentValueConverter<Segment> {
    @Override
    public ContentValues getValuesFrom(Segment obj) {
        SimpleDateFormat sdf = new SimpleDateFormat(SegmentDao.LAST_UPDATE_FORMAT);
        ContentValues cv = new ContentValues();
        cv.put(SEGMENT_ID, obj.getName());
        cv.put(COLOR_ID, obj.getColorId());
        cv.put(LAST_UPDATE_SEG, sdf.format(obj.getLastUpdate()));

        return cv;
    }
}
