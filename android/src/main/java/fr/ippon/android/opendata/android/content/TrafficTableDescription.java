/**
 *
 */
package fr.ippon.android.opendata.android.content;

import android.provider.BaseColumns;

/**
 * @author nicolasguillot
 *
 */
public class TrafficTableDescription implements BaseColumns {
    public static final String TRAFFIC_TABLE_NAME = "traffics";


    private TrafficTableDescription() {
    }

    // Identifiants des colonnes
    public static final String PATH_ID = "path_id";
    public static final String IS_VALID = "is_valid";
    public static final String TEMPS = "temps";
    public static final String LAST_UPDATE = "last_update";

    public static final String[] PROJECTION_ALL = new String[] {
            TrafficTableDescription._ID, TrafficTableDescription.PATH_ID,
            TrafficTableDescription.IS_VALID, TrafficTableDescription.TEMPS,
            TrafficTableDescription.LAST_UPDATE };
}
