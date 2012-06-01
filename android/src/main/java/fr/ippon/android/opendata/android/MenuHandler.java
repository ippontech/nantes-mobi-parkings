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

import com.actionbarsherlock.view.MenuItem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import fr.ippon.android.opendata.android.service.DataRefreshManager;

/**
 * Les différents Activity de notre projet n'héritent pas de la même classe
 * parente, on ne peut donc pas utiliser l'héritage pour avoir un menu commun.
 * 
 * Cette classe permet donc de disposer de plusieurs méthodes statiques, notamment
 * {@link #onOptionsItemSelected(Activity, MenuItem)}. Ceci permet d'utiliser ces
 * méthodes pour faire de la "composition" dans le comportement des Activity.
 * 
 * @author Damien Raude-Morvan
 */
public class MenuHandler {

	private static final String TAG = StartActivity.class.getName();

	/**
	 * Implémentation du menu "par défaut" de l'application.
	 * 
	 * @param activity Activité courante
	 * @param item Element sélectionné dans le menu
	 * @return true, si l'option sélectionnée a été traitée.
	 */
	public static boolean onOptionsItemSelected(final Activity activity, final MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_preference:
			showPreferences(activity);
			return true;
		case R.id.menu_refresh:
			requestRefresh(activity);
			return true;
		case R.id.menu_apropos:
			showApropos(activity);
			return true;
		case android.R.id.home:
			Intent intent = new Intent(activity, StartActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
            return true;
		default:
			Log.e(TAG, "Impossible de faire le traitement du menu!");
			return false;
		}
	}

	/**
	 * Affichage de l'activité A Propos
	 */
	protected static void showApropos(final Context context) {
		Log.d(TAG, "showApropos");
		Intent aPropos = new Intent(context, AproposActivity.class);
		context.startActivity(aPropos);
	}

	/**
	 * Affichage de l'activité Preferences
	 */
	protected static void showPreferences(final Context context) {
		Log.d(TAG, "showPreferences");
		Intent preferences = new Intent(context, SettingsActivity.class);
		context.startActivity(preferences);
	}

	/**
	 * Demande de refresh des données
	 */
	protected static void requestRefresh(final Context context) {
		Log.d(TAG, "requestRefresh");
		DataRefreshManager.requestRefresh(context, true);
	}

}
