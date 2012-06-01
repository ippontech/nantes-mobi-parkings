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
