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

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Nullable;
import javax.inject.Inject;

import roboguice.activity.RoboPreferenceActivity;
import roboguice.inject.InjectView;
import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import fr.ippon.android.opendata.android.content.ParkingDao;
import fr.ippon.android.opendata.android.service.EquipementManagerAndroid;

public class SettingsActivity extends RoboPreferenceActivity {

	private static final String LAST_UPDATE_TIMEFORMAT = "HH:mm";

	private static final String LAST_UPDATE_DATEFORMAT = "dd/MM/yyyy";
	
	private Toolbar mActionBar;

	@InjectView(R.id.refresh_dyndata)
	@Nullable
	TextView refresh_dyndata;

	@InjectView(R.id.refresh_staticdata)
	@Nullable
	TextView refresh_staticdata;

	@Inject
	ParkingDao parkingDao;

	@Inject
	ContentResolver contentResolver;

	@Inject
	EquipementManagerAndroid equipementManager;   

    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		// Affichage de notre vue custom
		setContentView(R.layout.settings);		
		
		mActionBar.setTitle(getTitle());
		mActionBar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
		
		String lastUpdateDispo = getDynLastUpdateTime();
		String dynRefreshSummary = getString(
				R.string.settings_data_dynrefresh_summary, lastUpdateDispo);
		refresh_dyndata.setText(dynRefreshSummary);

		String lastUpdateData = getLastUpdateData();
		String staticRefreshSummary = getString(
				R.string.settings_data_staticrefresh_summary, lastUpdateData);
		refresh_staticdata.setText(staticRefreshSummary);

		addPreferencesFromResource(R.xml.preferences);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		
		menu.setGroupEnabled(R.id.group_preference, false);
		
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return MenuHandler.onOptionsItemSelected(this, item);
	}

	/**
	 * Retourne l'heure de dernière mise à jour des disponibilités.
	 * 
	 * TODO DAMIEN: Peut-être poser un Observer pour obtenir automatiquement un
	 * nouveau calcul...
	 */
	protected String getDynLastUpdateTime() {
		String lastUpdateDispo = "--:--";
		Date lastUpdate = parkingDao.getLastUpdate(contentResolver);
		if (lastUpdate != null) {
			SimpleDateFormat timeFormat = new SimpleDateFormat(
					LAST_UPDATE_TIMEFORMAT);
			lastUpdateDispo = timeFormat.format(lastUpdate);
		}
		return lastUpdateDispo;
	}

	/**
	 * Retourne l'heure de dernière mise à jour des parkings.
	 * 
	 * TODO DAMIEN: Peut-être poser un Observer pour obtenir automatiquement un
	 * nouveau calcul...
	 */
	protected String getLastUpdateData() {
		String lastUpdateData = "XX/YY/ZZZZ";
		Date lastUpdate = equipementManager.readAppliedLastUpdate(this);
		if (lastUpdate != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					LAST_UPDATE_DATEFORMAT);
			lastUpdateData = dateFormat.format(lastUpdate);
		}
		return lastUpdateData;
	}
	
   

    @Override
    public void onStart() {
        super.onStart();
        
       // Get tracker.
       Tracker mTracker = ((MainApplication) this.getApplication()).getTracker();

       // Set screen name.
       mTracker.setScreenName("Préférences");

       // Send a screen view.
       mTracker.send(new HitBuilders.AppViewBuilder().build());  
    }

	@Override
	public void setContentView(int layoutResID) {
		ViewGroup contentView = (ViewGroup) LayoutInflater.from(this).inflate(
                R.layout.settings, new LinearLayout(this), false);

        mActionBar = (Toolbar) contentView.findViewById(R.id.action_bar_prefs);
        mActionBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getWindow().setContentView(contentView);
    }
    
    
}
