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
package fr.ippon.android.opendata.android;

import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.CONTENT_URI;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.FAVORI;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.LATITUDE;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.LONGITUDE;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.NOM;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.PROJECTION_ALL;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;

/**
 * Activité qui affiche la liste des mis en favoris
 * 
 * @author Damien Raude-Morvan
 */
public class FavouritesListFragment extends AbstractParkingListFragment {

	public FavouritesListFragment() {
		super(R.string.list_parking_fav_empty);
	}

	/**
	 * {@inheritDoc}
	 */
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(FAVORI + " = ?");
		String[] selectionArgs = { "1" };
		if (queryText != null) {
			buffer.append(" and UPPER(");
			buffer.append(NOM);
			buffer.append(") GLOB ?");
			selectionArgs = new String[] { "1",
					"*" + queryText.toString().toUpperCase() + "*" };
		}

		String orderBy = null;
		String sort = MainApplication.getDefaultSort();
		if ("NAME".equals(sort)) {
			orderBy = NOM + " ASC";
		} else {
			final Location currentLocation = locationDispatcher.getLastKnownLocation();
			// La location peut être null (pas de GPS par exemple)
			if (currentLocation != null) {
				orderBy = String.format(" ( abs(" + LATITUDE + " - (%s)) + abs("
					+ LONGITUDE + " - (%s) )) ", currentLocation.getLatitude(), currentLocation.getLongitude());
			}
		}

		return new CursorLoader(getActivity(), CONTENT_URI, PROJECTION_ALL,
				buffer == null ? null : buffer.toString(), selectionArgs,
				orderBy);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CursorAdapter createAdapter() {
		return new ParkingCursorAdapter(this);
	}

}
