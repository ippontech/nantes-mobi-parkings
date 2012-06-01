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

import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import fr.ippon.android.opendata.data.traffic.Segment;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static android.provider.BaseColumns._ID;
import static fr.ippon.android.opendata.android.content.SegmentColorTableDescription.CONTENT_URI;
import static fr.ippon.android.opendata.android.content.SegmentColorTableDescription.SEGMENT_ID;

/**
 * User: nicolasguillot Date: 06/05/12 Time: 14:35
 */
public class SegmentDao implements Dao<Segment, String> {
	/**
	 * Format de stockage de la date de dernière mise à jour.
	 */
	public static final String LAST_UPDATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

	private static final String TAG = SegmentDao.class.getName();

	@Override
	public Uri computeUniqueUri(ContentResolver resolver, String obj) {
		long id = -1;
		Cursor cursor = resolver.query(CONTENT_URI, new String[] { _ID },
				SEGMENT_ID + " = ?", new String[] { obj }, null);
		if (cursor.moveToFirst()) {
			id = cursor.getLong(0);
		}
		cursor.close();
		if (id == -1) {
			// Impossible de trouver un segment à partir de son nom/id
			// on retourne null
			return null;
		} else {
			return ContentUris.withAppendedId(CONTENT_URI, id);
		}
	}

	@Override
	public Segment loadFromCursor(Cursor cursor) {
		if (cursor.isBeforeFirst() || cursor.isAfterLast() || cursor.isClosed()) {
			throw new IllegalArgumentException();
		}

		SimpleDateFormat sdf = new SimpleDateFormat(LAST_UPDATE_FORMAT);
		Segment segment = new Segment();
		int index = 1; // 0 == _ID

		segment.setName(cursor.getString(index++));
		segment.setColorId(cursor.getInt(index++));
		try {
			segment.setLastUpdate(sdf.parse(cursor.getString(index++)));
		} catch (ParseException e) {
			segment.setLastUpdate(null);
		}

		return segment;
	}
}
