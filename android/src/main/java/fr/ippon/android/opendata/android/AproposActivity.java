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


import roboguice.inject.InjectView;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

/**
 * Affiche la vue "A Propos"
 * 
 * @author guillaume
 * 
 */
public class AproposActivity extends RoboSherlockActivity {

	private static final String TAG = AproposActivity.class.getName();

	private boolean useLogo = true;
	private boolean showHomeUp = true;

	@InjectView(R.id.apropos_objectif)
	private TextView apropos_objectif;

	@InjectView(R.id.apropos_outils_utilises)
	private TextView apropos_outils_utilises;

	@InjectView(R.id.apropos_developpeurs)
	private TextView apropos_developpeurs;

	@InjectView(R.id.apropos_votez)
	private TextView apropos_votez;

	@InjectView(R.id.apropos_fonctionnalite_a_venir)
	private TextView apropos_fonctionnalite_a_venir;

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apropos);
		
		final ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(showHomeUp);
		ab.setDisplayUseLogoEnabled(useLogo);

		apropos_objectif.setMovementMethod(LinkMovementMethod.getInstance());
		apropos_objectif.setText(extractHtml(R.string.apropos_objectif));

		apropos_outils_utilises.setMovementMethod(LinkMovementMethod
				.getInstance());
		apropos_outils_utilises
				.setText(extractHtml(R.string.apropos_outils_utilises));

		apropos_developpeurs
				.setMovementMethod(LinkMovementMethod.getInstance());
		apropos_developpeurs
				.setText(extractHtml(R.string.apropos_developpeurs));

		apropos_votez.setMovementMethod(LinkMovementMethod.getInstance());
		apropos_votez.setText(extractHtml(R.string.apropos_votez));

		apropos_fonctionnalite_a_venir.setMovementMethod(LinkMovementMethod
				.getInstance());
		apropos_fonctionnalite_a_venir
				.setText(extractHtml(R.string.apropos_fonctionnalite_a_venir));

	}
	
	@Override
    public void onStart() {
        super.onStart();

        // Get tracker.
        Tracker t = ((MainApplication) this.getApplication()).getTracker();

        // Set screen name.
        t.setScreenName("A propos");

        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());       
    }
	
	


	private Spanned extractHtml(int resourceId) {
		return Html.fromHtml(getResources().getString(resourceId));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main_menu, menu);

		menu.setGroupEnabled(R.id.group_apropos, false);

		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return MenuHandler.onOptionsItemSelected(this, item);
	}

}
