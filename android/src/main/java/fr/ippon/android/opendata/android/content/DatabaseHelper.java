/*
* Copyright 2012 Damien Raude-Morvan, Alvin Berthelot,
*                Guillaume Granger and Nicolas Guillot
* 
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* 
*     http://www.apache.org/licenses/LICENSE-2.0
* 
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package fr.ippon.android.opendata.android.content;

import static android.provider.BaseColumns._ID;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.DISPONIBLES;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.FAVORI;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.IDENTIFIANT;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.ID_OBJ;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.LAST_UPDATE;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.LATITUDE;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.LONGITUDE;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.NOM;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.PARKINGS_TABLE_NAME;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.PLACES_TOTALES;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.HORAIRES;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.PLACES_PMR;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.PLACES_ELEC;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.PLACES_VELO;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.PLACES_MOTO;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.PRIORITE;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.SEUIL_COMPLET;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.STATUS;
import static fr.ippon.android.opendata.android.content.TrafficTableDescription.IS_VALID;
import static fr.ippon.android.opendata.android.content.TrafficTableDescription.PATH_ID;
import static fr.ippon.android.opendata.android.content.TrafficTableDescription.TEMPS;
import static fr.ippon.android.opendata.android.content.TrafficTableDescription.TRAFFIC_TABLE_NAME;
import static fr.ippon.android.opendata.android.content.SegmentColorTableDescription.SEGMENTS_COLOR_TABLE_NAME;
import static fr.ippon.android.opendata.android.content.SegmentColorTableDescription.SEGMENT_ID;
import static fr.ippon.android.opendata.android.content.SegmentColorTableDescription.COLOR_ID;
import static fr.ippon.android.opendata.android.content.SegmentColorTableDescription.LAST_UPDATE_SEG;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String TAG = DatabaseHelper.class.getName();

	private static final String DATABASE_NAME = "parking.db";

    private static final int DATABASE_VERSION = 6;

	DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		// -------------------
		// Creation des tables
		db.execSQL("CREATE TABLE IF NOT EXISTS " + PARKINGS_TABLE_NAME + " ("
				+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ IDENTIFIANT + " TEXT, "
				+ NOM + " TEXT, "
				+ STATUS + " INTEGER, "
				+ PRIORITE + " INTEGER, "
				+ DISPONIBLES + " INTEGER, "
				+ SEUIL_COMPLET + " INTEGER, "
				+ PLACES_TOTALES + " INTEGER, "
				+ HORAIRES + " TEXT, "
				+ PLACES_PMR + " INTEGER, "
				+ PLACES_ELEC + " INTEGER, "
				+ PLACES_VELO + " INTEGER, "
				+ PLACES_MOTO + " INTEGER, "
				+ LAST_UPDATE + " TEXT, "
				+ ID_OBJ + " INTEGER, "
				+ LATITUDE + " DOUBLE, "
				+ LONGITUDE + " DOUBLE, "
				+ FAVORI + " INTEGER"
				+ ");");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + SEGMENTS_COLOR_TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SEGMENT_ID + " INTEGER, "
                + COLOR_ID + " INTEGER,"
                + LAST_UPDATE_SEG + " TEXT);");
        
		// --------------------
		// Creation des indexes
        db.execSQL("CREATE INDEX idx_" + PARKINGS_TABLE_NAME + "_id ON " + PARKINGS_TABLE_NAME + "(" + _ID + ");");
        db.execSQL("CREATE INDEX idx_" + PARKINGS_TABLE_NAME + "_idobj ON " + PARKINGS_TABLE_NAME + "(" + ID_OBJ + ");");
        db.execSQL("CREATE INDEX idx_" + PARKINGS_TABLE_NAME + "_latlong ON " + PARKINGS_TABLE_NAME + "(" + LATITUDE + ", " + LONGITUDE + ");");
        
        db.execSQL("CREATE INDEX idx_" + SEGMENTS_COLOR_TABLE_NAME + "_id ON " + SEGMENTS_COLOR_TABLE_NAME + "(" + _ID + ");");
        db.execSQL("CREATE INDEX idx_" + SEGMENTS_COLOR_TABLE_NAME + "_segmentid ON " + SEGMENTS_COLOR_TABLE_NAME + "(" + SEGMENT_ID + ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion <= 1) {
			Log.i(TAG, "Upgrade from 1 to 2 : add traffic table.");
			db.execSQL("CREATE TABLE IF NOT EXISTS " + TRAFFIC_TABLE_NAME + " ("
					+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
					+ PATH_ID + " TEXT, "
					+ IS_VALID + " TEXT, "
					+ TEMPS + " INTEGER, "
					+ TrafficTableDescription.LAST_UPDATE + " TEXT"
					+ ");");
		}
		
		if (oldVersion <= 2) {
			Log.i(TAG, "Upgrade from 2 to 3 : add favori field.");
			db.execSQL("ALTER TABLE " + PARKINGS_TABLE_NAME + " ADD COLUMN "
					+ FAVORI + " INTEGER"
					+ ";");
		}
        if(oldVersion <= 3) {
            Log.i(TAG, "Upgrade from 3 to 4 : delete "+TRAFFIC_TABLE_NAME+" table.");
            db.execSQL("DROP TABLE IF EXISTS "+ TRAFFIC_TABLE_NAME);
        }
        if(oldVersion <= 4) {
            Log.i(TAG, "Upgrade from 4 to 5 : create traffic segment color table.");
            db.execSQL("CREATE TABLE IF NOT EXISTS " + SEGMENTS_COLOR_TABLE_NAME + " ("
                    + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + SEGMENT_ID + " INTEGER, "
                    + COLOR_ID + " INTEGER,"
                    + LAST_UPDATE_SEG + " TEXT);");
        }
        if (oldVersion <= 5) {
			Log.i(TAG, "Upgrade from 5 to 6 : add fields places and horaires.");
			db.execSQL("ALTER TABLE " + PARKINGS_TABLE_NAME + " ADD COLUMN " + HORAIRES + " TEXT;");
			db.execSQL("ALTER TABLE " + PARKINGS_TABLE_NAME + " ADD COLUMN " + PLACES_PMR + " INTEGER;");
			db.execSQL("ALTER TABLE " + PARKINGS_TABLE_NAME + " ADD COLUMN " + PLACES_ELEC + " INTEGER;");
			db.execSQL("ALTER TABLE " + PARKINGS_TABLE_NAME + " ADD COLUMN " + PLACES_VELO + " INTEGER;");
			db.execSQL("ALTER TABLE " + PARKINGS_TABLE_NAME + " ADD COLUMN " + PLACES_MOTO + " INTEGER;");
			
			Log.i(TAG, "Upgrade from 5 to 6 : add indexes");
	        db.execSQL("CREATE INDEX idx_" + PARKINGS_TABLE_NAME + "_id ON " + PARKINGS_TABLE_NAME + "(" + _ID + ");");
	        db.execSQL("CREATE INDEX idx_" + PARKINGS_TABLE_NAME + "_idobj ON " + PARKINGS_TABLE_NAME + "(" + ID_OBJ + ");");
	        db.execSQL("CREATE INDEX idx_" + PARKINGS_TABLE_NAME + "_latlong ON " + PARKINGS_TABLE_NAME + "(" + LATITUDE + ", " + LONGITUDE + ");");

	        db.execSQL("CREATE INDEX idx_" + SEGMENTS_COLOR_TABLE_NAME + "_id ON " + SEGMENTS_COLOR_TABLE_NAME + "(" + _ID + ");");
	        db.execSQL("CREATE INDEX idx_" + SEGMENTS_COLOR_TABLE_NAME + "_segmentid ON " + SEGMENTS_COLOR_TABLE_NAME + "(" + SEGMENT_ID + ");");
		}
	}
}
