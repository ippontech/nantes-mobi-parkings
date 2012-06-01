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
