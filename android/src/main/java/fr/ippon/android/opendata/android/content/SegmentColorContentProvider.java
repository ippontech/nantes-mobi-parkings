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

import static fr.ippon.android.opendata.android.content.SegmentColorTableDescription.SEGMENTS_COLOR_TABLE_NAME;
import static fr.ippon.android.opendata.android.content.SegmentColorTableDescription.SEGMENT_ID;

import java.util.ArrayList;

import android.net.Uri;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.content.*;

/**
 * User: nicolasguillot Date: 05/05/12 Time: 19:22
 */
public class SegmentColorContentProvider extends AbstractContentProvider {
	private static final String TAG = SegmentColorContentProvider.class
			.getSimpleName();

	public static final String AUTHORITY = "fr.ippon.android.opendata.android.content.segments_color";

	protected DatabaseHelper dbHelper;

	private static final UriMatcher URI_MATCHER;

	private static final String TRAFFIC_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/vnd.ippon.segmentcolor";

	private static final int SEGMENT_COLOR_CODE = 2;
	private static final int SEGMENT_COLOR_SINGLE_CODE = 4;

	static {
		URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		// content://fr.ippon.android.opendata.android.content/segment_color
		URI_MATCHER.addURI(AUTHORITY, SEGMENTS_COLOR_TABLE_NAME,
				SEGMENT_COLOR_CODE);
		// content://fr.ippon.android.opendata.android.content/segment_color/#
		URI_MATCHER.addURI(SegmentColorContentProvider.AUTHORITY,
				SEGMENTS_COLOR_TABLE_NAME + "/#", SEGMENT_COLOR_SINGLE_CODE);
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
		case SEGMENT_COLOR_CODE:
			count = db.delete(SEGMENTS_COLOR_TABLE_NAME, where, whereArgs);
			break;

		case SEGMENT_COLOR_SINGLE_CODE:
			where = appendIdClauseToWhere(where);
			whereArgs = appendIdValueToWhere(uri, whereArgs);
			count = db.delete(SEGMENTS_COLOR_TABLE_NAME, where, whereArgs);
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
		case SEGMENT_COLOR_CODE:
			return TRAFFIC_CONTENT_TYPE;

		case SEGMENT_COLOR_SINGLE_CODE:
			return TRAFFIC_CONTENT_TYPE;

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
		case SEGMENT_COLOR_CODE:
			qb.setTables(SEGMENTS_COLOR_TABLE_NAME);
			break;

		case SEGMENT_COLOR_SINGLE_CODE:
			qb.setTables(SEGMENTS_COLOR_TABLE_NAME);
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
		case SEGMENT_COLOR_CODE:
			count = db.update(SEGMENTS_COLOR_TABLE_NAME, values, where,
					whereArgs);
			break;

		case SEGMENT_COLOR_SINGLE_CODE:
			where = appendIdClauseToWhere(where);
			whereArgs = appendIdValueToWhere(uri, whereArgs);
			count = db.update(SEGMENTS_COLOR_TABLE_NAME, values, where,
					whereArgs);
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
		case SEGMENT_COLOR_CODE:
			rowId = db.insert(SEGMENTS_COLOR_TABLE_NAME, SEGMENT_ID, values);
			if (rowId > 0) {
				// content://fr.ippon.android.opendata.android.content/segments_color/XX
				Uri noteUri = ContentUris.withAppendedId(
						SegmentColorTableDescription.CONTENT_URI, rowId);
				getContext().getContentResolver().notifyChange(noteUri, null);
				return noteUri;
			}
			break;
		}

		throw new IllegalArgumentException("Invalid URI: " + uri);
	}

	@Override
	public ContentProviderResult[] applyBatch(
			ArrayList<ContentProviderOperation> operations) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		db.beginTransaction();
		try {
			final int nbOp = operations.size();
			final ContentProviderResult[] results = new ContentProviderResult[nbOp];
			for (int i = 0; i < nbOp; i++) {
				results[i] = operations.get(i).apply(this, results, i);
			}
			db.setTransactionSuccessful();
			return results;
		} catch (OperationApplicationException e) {
			e.printStackTrace();
			return null;
		} finally {
			db.endTransaction();
		}
	}
}
