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

import static fr.ippon.android.opendata.android.service.ServiceStatus.SERVICE_FINISHED;
import static fr.ippon.android.opendata.android.service.ServiceStatus.SERVICE_RUNNING;

import javax.inject.Inject;

import roboguice.activity.RoboActionBarActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import fr.ippon.android.opendata.android.content.FavorisDao;
import fr.ippon.android.opendata.android.content.ParkingDao;
import fr.ippon.android.opendata.android.map.EquipementsItemizedOverlay;
import fr.ippon.android.opendata.android.map.MapFragment;
import fr.ippon.android.opendata.android.service.DataRefreshManager;
import fr.ippon.android.opendata.android.tabs.SlidingTabLayout;
import fr.ippon.android.opendata.data.distance.DistanceCalculator;
import fr.ippon.android.opendata.data.parkings.ParkingEntity;

/**
 * Activité principale qui permet de configurer les onglets et lancer les
 * services.
 * 
 * @author Damien Raude-Morvan
 * @author Guillaume Granger
 */
public class StartActivity extends RoboActionBarActivity {


	private static final String TAG = StartActivity.class.getName();

	@Inject
	DistanceCalculator distanceCalculator;

	@Inject
	ParkingDao parkingDao;

	@Inject
	FavorisDao favorisDao;

	@Inject
	private LocationChangeDispatcher locationDispatcher;

	private LocalBroadcastManager localBroadcastManager;
	private BroadcastReceiver receiver;
	private Menu optionsMenu;
	private View refreshIndeterminateProgressView = null;
	private ParkingPagerAdapter parkingPagerAdapter;
    private ViewPager mViewPager;
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Branchement des systemes de geo-loc
		locationDispatcher.registerGpsUpdate();

		// We use this to send broadcasts within our local process.
		localBroadcastManager = LocalBroadcastManager.getInstance(this);
		IntentFilter filter = new IntentFilter();
		filter.addAction(SERVICE_RUNNING.name());
		filter.addAction(SERVICE_FINISHED.name());
		receiver = new ServiceStatusReceiver();
		localBroadcastManager.registerReceiver(receiver, filter);

		setContentView(R.layout.start);
		
		parkingPagerAdapter = new ParkingPagerAdapter(getSupportFragmentManager(), this);
	
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(parkingPagerAdapter);
        
        // Initialize the SlidingTabLayout. Note that the order is important. First init ViewPager and Adapter and only then init SlidingTabLayout
        SlidingTabLayout mSlidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
		
	
		// si on a des favoris, démarrage sur l'onglet "favoris"
		if (parkingDao.hasFavoris(getContentResolver())) {
			mViewPager.setCurrentItem(2);
		}
		
		// Lancement des services
		DataRefreshManager.requestRefresh(this, true);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onResume() {
		super.onResume();
		DataRefreshManager.requestRefresh(this, false);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();

		// Action inverse de registerGpsUpdate()
		locationDispatcher.unregisterGpsUpdate();
		localBroadcastManager.unregisterReceiver(receiver);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		optionsMenu = menu;
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return MenuHandler.onOptionsItemSelected(this, item);
	}

	protected void showMap() {
		// Changement de tab
		//TODO à remettre
//		if (getActionBar().getTabCount() > 0) {
//			getActionBar().selectTab(getActionBar().getTabAt(1));
//		}
	}

	/**
	 * Affichage du fragment Carte
	 */
	void showMap(final ParkingEntity park) {
		Log.d(TAG, "showMap");

		EquipementsItemizedOverlay.clearSelectedParking();
		MapFragment.selectedParking = park;
		EquipementsItemizedOverlay.markParkAsSelected(park.getIdObj());

		// Passage a la carte
		showMap();
	}

	/**
	 * Permet d'afficher des messages / de changer des éléments graphiques
	 * lorsque certains traitements changent de status.
	 * 
	 * @author Damien Raude-Morvan
	 */
	class ServiceStatusReceiver extends BroadcastReceiver {
		
		/**
		 * Compte le nombre de service lancés
		 */
		private int servicesRunning = 0;

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void onReceive(final Context context, final Intent intent) {			
			// Incrément ou décrément du nombre de service
			if (SERVICE_RUNNING.is(intent.getAction())) {
				servicesRunning++;
			} else if (SERVICE_FINISHED.is(intent.getAction())) {
				servicesRunning--;
			}
						
			Log.d(TAG, "servicesRunning:" + servicesRunning);

			// Il faut mettre à jour le composant de refresh suivant le nombre de service en cours d'exécution
			StartActivity.this.runOnUiThread(new Runnable() {
				public void run() {
					if (optionsMenu == null) {
			            return;
			        }
					
					final MenuItem refreshItem = optionsMenu.findItem(R.id.menu_refresh);
					if (refreshItem != null) {
						if (servicesRunning > 0) {
			                if (refreshIndeterminateProgressView == null) {
			                	ServiceStatusReceiver.this.loadProgressView(context);
			                }
			                //TODO à remettre
			               // refreshItem.setActionView(refreshIndeterminateProgressView);
						} else {
			                //TODO à remettre
			               // refreshItem.setActionView(null);
			                // si le rafraichissement a été lancé par un scroll sur la liste, on vire l'icone
			                SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
			                if (swipeLayout != null) { 
			                	swipeLayout.setRefreshing(false);
			                }
			                ServiceStatusReceiver.this.toastIfError(intent);
			            }
			        }
				}
			});
		}

		/**
		 * Affichage d'un message d'erreur sous la forme d'un toast
		 */
		private void toastIfError(final Intent intent) {
			if (intent.getExtras() != null) {
				String error = intent.getExtras()
						.getString("error");
				Toast.makeText(StartActivity.this, error,
						Toast.LENGTH_SHORT).show();
			}
		}

		/**
		 * Chargement du layout qui permet de remplacer le bouton refresh
		 */
		private void loadProgressView(final Context context) {
			final LayoutInflater inflater = LayoutInflater.from(context);
			refreshIndeterminateProgressView = inflater.inflate(
			        R.layout.actionbar_indeterminate_progress, null);
		}
	}

}
