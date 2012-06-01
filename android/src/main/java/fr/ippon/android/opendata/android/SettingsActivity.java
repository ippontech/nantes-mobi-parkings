package fr.ippon.android.opendata.android;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Nullable;
import javax.inject.Inject;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockPreferenceActivity;

import roboguice.inject.InjectView;
import android.content.ContentResolver;
import android.os.Bundle;
import android.widget.TextView;
import fr.ippon.android.opendata.android.content.ParkingDao;
import fr.ippon.android.opendata.android.service.EquipementManagerAndroid;

public class SettingsActivity extends RoboSherlockPreferenceActivity {

	private static final String LAST_UPDATE_TIMEFORMAT = "HH:mm";

	private static final String LAST_UPDATE_DATEFORMAT = "dd/MM/yyyy";

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
	
	private boolean useLogo = true;
    private boolean showHomeUp = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Affichage de notre vue custom
		setContentView(R.layout.settings);
		
		final ActionBar ab = getSupportActionBar();
		ab.setDisplayHomeAsUpEnabled(showHomeUp);
		ab.setDisplayUseLogoEnabled(useLogo);
		
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
		getSupportMenuInflater().inflate(R.menu.main_menu, menu);
		
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
}
