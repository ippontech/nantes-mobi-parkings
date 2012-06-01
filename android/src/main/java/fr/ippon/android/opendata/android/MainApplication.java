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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Classe de démarrage de l'application. Permet de récupérer un pointeur
 * statique vers le contexte et de gérer la lecture des préférences.
 * 
 * @author Damien Raude-Morvan
 */
public class MainApplication extends Application {

	private static Context context;

	private static final String TAG = MainApplication.class.getName();

	/**
	 * Executor unique qui permet de sérialiser les traitements de chargement
	 * (plutot que de tout lancer en même temps en parralèle)
	 */
	public final static ExecutorService executor = Executors.newSingleThreadExecutor();

	public void onCreate() {
		context = this.getApplicationContext();

		// Chargement des valeurs par defaut des preferences
		// si elles n'ont jamais ete definies
		// http://stackoverflow.com/a/2877795
		boolean readAgain = false;
		PreferenceManager.setDefaultValues(this, R.xml.preferences, readAgain);
	}

	public static Context getAppContext() {
		return context;
	}

	/**
	 * Permet de récupérer l'activation de l'affichage du traffic
	 */
	public static boolean isDefaultPrefShowTraffic() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		Preference pref = Preference.SHOW_TRAFFIC;
		// On peut manipuler cette preference comme un boolean
		// car la PreferenceActivity traite correctement les CheckBoxPreference
		boolean defaultValue = Boolean.parseBoolean(pref.getDefaultValue());
		boolean actualValue = prefs.getBoolean(pref.getKey(), defaultValue);
		return actualValue;
	}

	public static String getDefaultSort() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);
		Preference pref = Preference.DEFAULT_SORT;
		String defaultValue = pref.getDefaultValue();
		String actualValue = prefs.getString(pref.getKey(), defaultValue);
		return actualValue;
	}
}
