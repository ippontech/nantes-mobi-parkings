package fr.ippon.android.opendata.android.content;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * User: nicolasguillot
 * Date: 05/05/12
 * Time: 19:15
 */
public class SegmentColorTableDescription implements BaseColumns {
    public static final String SEGMENTS_COLOR_TABLE_NAME = "segments_color";

    public static final Uri CONTENT_URI = Uri.parse("content://"
            + SegmentColorContentProvider.AUTHORITY + "/"+SEGMENTS_COLOR_TABLE_NAME);

    private SegmentColorTableDescription() {
    }

    // Identifiants des colonnes
    public static final String SEGMENT_ID = "id";
    public static final String COLOR_ID = "color_id";
    public static final String LAST_UPDATE_SEG = "last_update";

    public static final String[] PROJECTION_ALL = new String[] {
            _ID,
            SEGMENT_ID,
            COLOR_ID,
            LAST_UPDATE_SEG
    };
}
