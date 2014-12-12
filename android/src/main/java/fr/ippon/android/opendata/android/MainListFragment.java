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
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.LATITUDE;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.LONGITUDE;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.NOM;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.DISPONIBLES;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.PROJECTION_ALL;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;

/**
 * Activité qui affiche la liste des parkings complête.
 * 
 * @author Damien Raude-Morvan
 */
public class MainListFragment extends AbstractParkingListFragment {
	
	public MainListFragment() {
		super(R.string.list_parking_empty_download);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		StringBuilder buffer = new StringBuilder();
		String[] selectionArgs = null;
		if (queryText != null) {
			buffer.append("UPPER(");
			buffer.append(NOM);
			buffer.append(") GLOB ?");
			selectionArgs = new String[] { "*"
					+ queryText.toString().toUpperCase() + "*" };
		}		

		return new CursorLoader(getActivity(), CONTENT_URI, PROJECTION_ALL,
				buffer.toString(), selectionArgs, getOrderBy());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected CursorAdapter createAdapter() {
		return new ParkingCursorAdapter(this);
	}
	
	

    @Override
    public void onStart() {
        super.onStart();
                
        // Get tracker.
        Tracker t = ((MainApplication) this.getActivity().getApplication()).getTracker();

        // Set screen name.
        t.setScreenName("Liste des parkings");

        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());  
    }
    
}
