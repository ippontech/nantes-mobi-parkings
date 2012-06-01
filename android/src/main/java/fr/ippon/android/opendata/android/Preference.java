package fr.ippon.android.opendata.android;

import java.util.prefs.Preferences;


public enum Preference {
	AFFICHE_INDISPO("hide_parkings", "true"),
    SHOW_TRAFFIC("show_traffic","false"),
    DEFAULT_SORT("default_sort", "DISTANCE");

	/**
	 * Clef de la preference
	 */
	private String key;

	/**
	 * Valeur par défaut
	 */
	private String defaultValue;

	/**
	 * Constructeur.
	 * 
	 * @param key
     * @param defaultValue
	 */
	Preference(final String key, final String defaultValue) {
		this.key = key;
		this.defaultValue = defaultValue;
	}

	/**
	 * Renvoie un enum en fonction de la clef
	 * 
	 * @param key
	 *            clef.
	 * @return l'enum.
	 */
	public static Preference fromValue(String key) {
		for (Preference pref : values()) {
			if (pref.key.equals(key)) {
				return pref;
			}
		}
		return null;
	}

	public String getKey() {
		return this.key;
	}

	public String getDefaultValue() {
		return this.defaultValue;
	}
}
