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
package fr.ippon.android.opendata.android.map;

import javax.inject.Inject;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.overlay.MyLocationOverlay;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.rtyley.android.sherlock.roboguice.fragment.RoboSherlockFragment;

import fr.ippon.android.opendata.android.MainApplication;
import fr.ippon.android.opendata.android.Preference;
import fr.ippon.android.opendata.android.R;
import fr.ippon.android.opendata.android.content.ParkingsTableDescription;
import fr.ippon.android.opendata.data.parkings.ParkingEntity;

/**
 * @author Damien Raude-Morvan
 */
public class MapFragment extends RoboSherlockFragment {

	private static final String TAG = MapFragment.class.getName();

	@Inject
	ContentResolver contentResolver;

	CustomMapView mapView;

	private MyLocationOverlay myLocation = null;

	private MapController mc = null;

	private DataObserver dataObserver;

	private GeoPoint nantes = new GeoPoint(47218371, -1553621);

	public static ParkingEntity selectedParking = null;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		final View view = inflater.inflate(R.layout.map, container, false);

		// text view décrivant l'overelay sélectionné
		TextView ovlerayDescr = (TextView) view
				.findViewById(R.id.overlay_descr);
		final RelativeLayout descrLayout = (RelativeLayout) view
				.findViewById(R.id.main_overlay_descr);
		descrLayout.setVisibility(View.GONE);
		ImageView overlayDescrBtn = (ImageView) view
				.findViewById(R.id.overlay_descr_close);
		overlayDescrBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				descrLayout.setVisibility(View.GONE);
			}
		});
		ImageView overlayNavigationBtn = (ImageView) view.findViewById(R.id.overlay_descr_navigation);
        overlayNavigationBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	 if (selectedParking != null)

                 {
                     String latitude = String.valueOf(selectedParking.getLatitude());
                     String longitude = String.valueOf(selectedParking.getLongitude());
                     Intent navigation = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" + latitude + "," + longitude));
                     startActivity(navigation);
                 }
            }
        });

		mapView = (CustomMapView) view.findViewById(R.id.mapview);
		mapView.setDescriptionItems(ovlerayDescr, descrLayout);

		// Ajout de la position courante de l'utilisateur
		myLocation = new MyLocationOverlay(getActivity(), mapView);
		mapView.getOverlays().add(myLocation);

		myLocation.enableMyLocation();

		// Activation du refresh lors du déplacement de la carte
		mapView.enableBackgroundRefresh();

		mapView.invalidate();
		mc = mapView.getController();

		if (selectedParking != null) {
			int latitude = (int) (selectedParking.getLatitude() * 1E6);
			int longitude = (int) (selectedParking.getLongitude() * 1E6);
			GeoPoint point = new GeoPoint(latitude, longitude);

			mc.setZoom(18);
			mc.setCenter(point);
		} else {
			mc.setZoom(13);
			mc.setCenter(nantes);
		}

        // Branchement du listener sur les preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.registerOnSharedPreferenceChangeListener(spChanged);

		return view;
	}

	public void onResume() {
		Log.d(TAG, "onResume");
		super.onResume();
		dataObserver = new DataObserver();
		contentResolver.registerContentObserver(
				ParkingsTableDescription.CONTENT_URI, true, dataObserver);
		myLocation.enableMyLocation();
		mapView.enableBackgroundRefresh();
		// Utilisation d'un delai avant l'affichage des overlay
		// pour obtenir un BoundingBox valide de la part de la MapView
		this.mapView.postDelayed(new Runnable() {
			public void run() {
				MapFragment.this.mapView.requestBackgroundRefresh();
			}
		}, 150);
	}

	@Override
	public void onPause() {
		Log.d(TAG, "onPause");
		myLocation.disableMyLocation();
		contentResolver.unregisterContentObserver(dataObserver);
		mapView.disableBackgroundRefresh();
		super.onPause();
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "onDestroy");
        // Supprime le listener sur les preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        prefs.unregisterOnSharedPreferenceChangeListener(spChanged);
		super.onDestroy();
	}

    SharedPreferences.OnSharedPreferenceChangeListener spChanged = new SharedPreferences.OnSharedPreferenceChangeListener() {
        /**
         * {@inheritDoc}
         */
        public void onSharedPreferenceChanged(
                SharedPreferences sharedPreferences, String key) {

            if ((Preference.SHOW_TRAFFIC.getKey()).equals(key)) {
                boolean value = MainApplication.isDefaultPrefShowTraffic();
                Log.d(TAG, "onSharedPreferenceChanged: " + key + "/value:"
                        + value);
                mapView.setTraffic(value);
            }
        }
    };

	/**
	 * Pour se tenir informer des changements de donnees.
	 */
	private class DataObserver extends ContentObserver {

		public DataObserver() {
			super(new Handler());
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void onChange(boolean selfChange) {
			mapView.requestBackgroundRefresh();
		}
	}
}
