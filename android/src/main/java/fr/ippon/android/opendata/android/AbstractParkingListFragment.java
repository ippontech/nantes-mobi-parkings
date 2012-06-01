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

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.*;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import roboguice.fragment.RoboListFragment;
import roboguice.inject.InjectResource;

import javax.inject.Inject;

/**
 * Version abstraite d'un Fragment qui prend en charge l'affichage d'une liste
 * de parkings.
 * 
 * @author Damien Raude-Morvan
 */
public abstract class AbstractParkingListFragment extends RoboListFragment
		implements LoaderManager.LoaderCallbacks<Cursor> {

	private static final String TAG = AbstractParkingListFragment.class
			.getName();

	private CursorAdapter adapter;

	private View headerView;

	protected EditText searchField;

	private TextView emptyText;

	/**
	 * Identifiant de la ressource String.
	 */
	private int emptyTextResource;

	protected String queryText = null;

	@InjectResource(R.string.search_hint)
	private String searchHint;

	@Inject
	LocationChangeDispatcher locationDispatcher;

	private boolean hasSearched = false;

	public AbstractParkingListFragment(int emptyTextResource) {
		super();
		this.emptyTextResource = emptyTextResource;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.d(TAG, "onActivityCreated");
		super.onActivityCreated(savedInstanceState);

		if (emptyText != null) {
			emptyText.setText(getResources().getString(emptyTextResource));
		}

		if (headerView != null) {
			getListView().addHeaderView(headerView);
		}

		if (adapter == null) {
			adapter = createAdapter();
		}
		setListAdapter(adapter);

		getLoaderManager().initLoader(0, null, this);

		hasSearched = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.d(TAG, "onCreateView");
		RelativeLayout r = (RelativeLayout) inflater.inflate(R.layout.list,
				null);
		emptyText = (TextView) r.findViewById(android.R.id.empty);
		headerView = inflater.inflate(R.layout.items_parkings_header, null);

		// Branchement du listener sur les preferences
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		prefs.registerOnSharedPreferenceChangeListener(spChanged);

		configureSearchField(r);

		locationDispatcher.addListener(locationListener);

		return r;
	}

	private void configureSearchField(RelativeLayout r) {
		final AbstractParkingListFragment loaderCallBack = this;
		searchField = (EditText) r.findViewById(R.id.searchField);

		if (TextUtils.isEmpty(searchField.getText()))
			searchField.setText(searchHint);

		searchField.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN)
						&& (keyCode == KeyEvent.KEYCODE_ENTER)) {
					// hide the keyboard
					InputMethodManager imm = (InputMethodManager) getActivity()
							.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(searchField.getWindowToken(), 0);

					// Perform action on key press
					boolean okSearch = !TextUtils.isEmpty(searchField.getText())
							&& !searchHint.equals(searchField.getText());
					queryText = okSearch ? searchField.getText().toString()
							: null;
					Log.d(TAG, "query: " + queryText);
					hasSearched = true;
					getLoaderManager().restartLoader(0, null, loaderCallBack);
					if (queryText == null) {
						searchField.setText(searchHint);
					}
					return true;
				}
				return false;
			}
		});
		// afichage d'une croix dans l'edit text pour effacer le texte saisie
		final Drawable x = getResources().getDrawable(R.drawable.ic_cancel);
		x.setBounds(0, 0, x.getIntrinsicWidth(), x.getIntrinsicHeight());
		searchField.setCompoundDrawables(null, null, x, null);
		searchField.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN
						&& searchHint.equals(searchField.getText().toString())) {
					searchField.setText("");
				}
				if (searchField.getCompoundDrawables()[2] == null) {
					// cross is not being shown so no need to handle
					return false;
				}
				if (event.getAction() != MotionEvent.ACTION_DOWN) {
					// only respond to the down type
					return false;
				}
				if (event.getX() > searchField.getMeasuredWidth()
						- searchField.getPaddingRight() - x.getIntrinsicWidth()) {
					searchField.setText("");
					return true;
				} else {
					return false;
				}
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onDestroyView() {
		Log.d(TAG, "onDestroyView");
		super.onDestroyView();
		setListAdapter(null);

		// Branchement du listener sur les preferences
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		prefs.unregisterOnSharedPreferenceChangeListener(spChanged);

		locationDispatcher.removeListener(locationListener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		Log.d(TAG, "onLoadFinished");
		adapter.swapCursor(data);

		if (hasSearched) {
			getListView().getEmptyView().setVisibility(View.GONE);
			getListView().setVisibility(View.VISIBLE);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void onLoaderReset(Loader<Cursor> loader) {
		adapter.swapCursor(null);
	}

	/**
	 * A surcharger pour definir la méthode de création de l'adapteur entre le
	 * CursorAdapter et l'affichage de la ListView.
	 */
	protected abstract CursorAdapter createAdapter();

	SharedPreferences.OnSharedPreferenceChangeListener spChanged = new SharedPreferences.OnSharedPreferenceChangeListener() {

		/**
		 * {@inheritDoc}
		 */
		public void onSharedPreferenceChanged(
				SharedPreferences sharedPreferences, String key) {

			if ((Preference.DEFAULT_SORT.getKey()).equals(key)) {
				String value = MainApplication.getDefaultSort();
				Log.d(TAG, "onSharedPreferenceChanged: " + key + "/value:"
						+ value);
				getLoaderManager().restartLoader(0, null,
						AbstractParkingListFragment.this);
			}

		}
	};

	protected LocationListener locationListener = new LocationListener() {

		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		public void onProviderEnabled(String provider) {

		}

		public void onProviderDisabled(String provider) {

		}

		public void onLocationChanged(Location location) {
			if (location != null) {
				getLoaderManager().restartLoader(0, null,
						AbstractParkingListFragment.this);
			}
		}
	};

}
