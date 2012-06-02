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
