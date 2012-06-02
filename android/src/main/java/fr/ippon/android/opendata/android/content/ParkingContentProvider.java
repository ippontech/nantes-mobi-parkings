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

import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.IDENTIFIANT;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.PARKINGS_TABLE_NAME;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * @author Damien Raude-Morvan
 */
public class ParkingContentProvider extends AbstractContentProvider {
	public static final String AUTHORITY = "fr.ippon.android.opendata.android.content.parking";

	protected DatabaseHelper dbHelper;

	private static final UriMatcher URI_MATCHER;

	private static final String PARKING_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/vnd.ippon.parking";

	private static final int PARKINGS_CODE = 1;
	private static final int PARKINGS_SINGLE_CODE = 3;

	static {
		URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		
		// content://fr.ippon.android.opendata.android.content/parkings
		URI_MATCHER.addURI(ParkingContentProvider.AUTHORITY,
				PARKINGS_TABLE_NAME, PARKINGS_CODE);
		// content://fr.ippon.android.opendata.android.content/parkings/#
		URI_MATCHER.addURI(ParkingContentProvider.AUTHORITY,
				PARKINGS_TABLE_NAME + "/#", PARKINGS_SINGLE_CODE);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreate() {
		dbHelper = new DatabaseHelper(getContext());
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count;
		switch (URI_MATCHER.match(uri)) {
		
		case PARKINGS_CODE:
			count = db.delete(PARKINGS_TABLE_NAME, where, whereArgs);
			break;

		case PARKINGS_SINGLE_CODE:
			where = appendIdClauseToWhere(where);
			whereArgs = appendIdValueToWhere(uri, whereArgs);
			count = db.delete(PARKINGS_TABLE_NAME, where, whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Invalid URI: " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getType(Uri uri) {
		switch (URI_MATCHER.match(uri)) {

		case PARKINGS_CODE:
			return PARKING_CONTENT_TYPE;

		case PARKINGS_SINGLE_CODE:
			return PARKING_CONTENT_TYPE;

		default:
			throw new IllegalArgumentException("Invalid URI: " + uri);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String where,
			String[] whereArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

		switch (URI_MATCHER.match(uri)) {

		case PARKINGS_CODE:
			qb.setTables(PARKINGS_TABLE_NAME);
			break;

		case PARKINGS_SINGLE_CODE:
			qb.setTables(PARKINGS_TABLE_NAME);
			where = appendIdClauseToWhere(where);
			whereArgs = appendIdValueToWhere(uri, whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Invalid URI: " + uri);
		}

		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = qb.query(db, projection, where, whereArgs, null, null,
				sortOrder);

		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int update(Uri uri, ContentValues values, String where,
			String[] whereArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count;
		switch (URI_MATCHER.match(uri)) {

		case PARKINGS_CODE:
			count = db.update(PARKINGS_TABLE_NAME, values, where, whereArgs);
			break;

		case PARKINGS_SINGLE_CODE:
			where = appendIdClauseToWhere(where);
			whereArgs = appendIdValueToWhere(uri, whereArgs);
			count = db.update(PARKINGS_TABLE_NAME, values, where, whereArgs);
			break;

		default:
			throw new IllegalArgumentException("Invalid URI: " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}

		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long rowId = -1;
		switch (URI_MATCHER.match(uri)) {

		case PARKINGS_CODE:
			rowId = db.insert(PARKINGS_TABLE_NAME, IDENTIFIANT, values);
			if (rowId > 0) {
				// content://fr.ippon.android.opendata.android.content/parkings/XX
				Uri noteUri = ContentUris.withAppendedId(
						ParkingsTableDescription.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(noteUri, null);
				return noteUri;
			}
			break;
		}

		throw new IllegalArgumentException("Invalid URI: " + uri);
	}
}
