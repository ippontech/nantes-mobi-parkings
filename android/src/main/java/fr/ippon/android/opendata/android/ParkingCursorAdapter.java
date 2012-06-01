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

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.location.Location;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import fr.ippon.android.opendata.data.distance.GpsPoint;
import fr.ippon.android.opendata.data.parkings.ParkingEntity;
import fr.ippon.android.opendata.data.parkings.ParkingUtils;

/**
 * Permet de chargement de le contenu depuis des données provenant d'un Cursor
 * de charger le layout (R.layout.item_parking) correspondant à une ligne dans
 * un ListView.
 * 
 * @author Alvin Berthelot
 * @author Damien Raude-Morvan
 */
class ParkingCursorAdapter extends CursorAdapter {

	private final AbstractParkingListFragment fragment;

	/**
	 * Lien vers l'activité qui manage les Cursor.
	 */
	private final StartActivity activity;

	private Map<String, String> mapLabels;

	private ToMapClickListener toMapClickListener = new ToMapClickListener();
	private StarOnCheckedChangeListener starChangeListener = new StarOnCheckedChangeListener();

	public ParkingCursorAdapter(AbstractParkingListFragment fragment) {
		super(fragment.getActivity(), (Cursor) null,
				FLAG_REGISTER_CONTENT_OBSERVER);
		this.fragment = fragment;
		this.activity = (StartActivity) fragment.getActivity();

		// Chargement des libellés
		mapLabels = new HashMap<String, String>();
		mapLabels.put("SUBSCRIBE_ONLY",
				activity.getString(R.string.park_status_only_subscribed));
		mapLabels
				.put("CLOSED", activity.getString(R.string.park_status_closed));
		mapLabels.put("FULL", activity.getString(R.string.park_status_full));
		mapLabels.put("OK", activity.getString(R.string.park_status_ok));
		mapLabels.put("INVALID",
				activity.getString(R.string.park_status_invalid));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public View newView(final Context context, final Cursor cursor,
			final ViewGroup parent) {

		final LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.item_parking, parent, false);

		// declarartion de la vue du parking
		ParkingViewHolder holder = new ParkingViewHolder();
		holder.star = (CheckBox) view.findViewById(R.id.btn_star);
		holder.star.setOnCheckedChangeListener(starChangeListener);
		holder.star.setTag(holder);

		holder.name = (TextView) view.findViewById(R.id.parking_name);
		holder.places = (TextView) view.findViewById(R.id.parking_places);
		holder.distance = (TextView) view.findViewById(R.id.parking_distance);

		holder.item = view.findViewById(R.id.item_info);
		holder.item.setOnClickListener(toMapClickListener);
		holder.item.setTag(holder);

		view.setTag(holder);

		return view;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bindView(final View view, final Context context,
			final Cursor cursor) {

		// recuperation du parking
		ParkingEntity parking = activity.parkingDao.loadFromCursor(cursor);

		ParkingViewHolder holder = (ParkingViewHolder) view.getTag();

		// valorisation de la vue
		boolean isFavori = activity.favorisDao.isFavori(activity,
				parking.getIdObj());
		holder.star.setChecked(isFavori);
		holder.name.setText(parking.getNom());

		String displayPlaces = ParkingUtils.getPlacesStatus(mapLabels, parking);
		holder.places.setText(displayPlaces);
		holder.currentPark = parking;

		final Location currentLocation = fragment.locationDispatcher
				.getLastKnownLocation();
		// La location peut être null (pas de GPS par exemple)
		if (currentLocation != null) {
			GpsPoint parkingPoint = new GpsPoint(parking.getLatitude(),
					parking.getLongitude());
			GpsPoint currentPoint = new GpsPoint(currentLocation.getLatitude(),
					currentLocation.getLongitude());
			String displayedDistance = ParkingUtils.getDistanceStatus(
					activity.distanceCalculator, parkingPoint, currentPoint);
			// Affichage de la distance
			holder.distance.setVisibility(View.VISIBLE);
			holder.distance.setText(displayedDistance);
		} else {
			// Impossible de récupérer une distance, on cache la colonne
			holder.distance.setVisibility(View.GONE);
		}
	}

	class StarOnCheckedChangeListener implements OnCheckedChangeListener {

		/**
		 * {@inheritDoc}
		 */
		public void onCheckedChanged(CompoundButton view, boolean isChecked) {
			if (view != null && view.isShown()) {
				ParkingViewHolder holder = (ParkingViewHolder) view.getTag();
				if (holder.currentPark != null) {
					if (isChecked) {
						activity.favorisDao.addFavori(activity,
								holder.currentPark);
					} else {
						activity.favorisDao.removeFavori(activity,
								holder.currentPark);
					}
				}
			}
		}
	};

	class ToMapClickListener implements OnClickListener {

		/**
		 * {@inheritDoc}
		 */
		public void onClick(View view) {
			final int position = fragment.getListView()
					.getPositionForView(view);
			if (position != ListView.INVALID_POSITION) {
				fragment.getListView().setItemChecked(position, true);
				ParkingViewHolder holder = (ParkingViewHolder) view.getTag();
				activity.showMap(holder.currentPark);
			}
		}
	};

	private class ParkingViewHolder {
		View item;
		CheckBox star;
		TextView name;
		TextView places;
		TextView distance;
		ParkingEntity currentPark;
	}
}