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
package fr.ippon.android.opendata.android.content;

import android.net.Uri;
import android.provider.BaseColumns;

public final class ParkingsTableDescription implements BaseColumns {
	public static final String PARKINGS_TABLE_NAME = "parkings";
	
	public static final Uri CONTENT_URI = Uri.parse("content://"
			+ ParkingContentProvider.AUTHORITY + "/"+PARKINGS_TABLE_NAME);
	
	private ParkingsTableDescription() {
	}

	// Identifiants des colonnes
	public static final String IDENTIFIANT = "identifiant";
	public static final String NOM = "nom";
	public static final String STATUS = "status";
	public static final String PRIORITE = "priorite";
	public static final String DISPONIBLES = "disponibles";
	public static final String SEUIL_COMPLET = "seuil_complet";
	public static final String PLACES_TOTALES = "places_totales";
	public static final String HORAIRES = "horaires";
	public static final String PLACES_PMR = "places_pmr";
	public static final String PLACES_ELEC = "places_elec";
	public static final String PLACES_VELO = "places_velo";
	public static final String PLACES_MOTO = "places_moto";
	public static final String LAST_UPDATE = "last_update";
	public static final String ID_OBJ = "id_obj";
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String FAVORI = "favori";

	public static final String[] PROJECTION_ALL = new String[] {
		_ID,
		IDENTIFIANT,
		NOM,
		STATUS,
		PRIORITE,
		DISPONIBLES,
		SEUIL_COMPLET,
		PLACES_TOTALES,
		HORAIRES,
		PLACES_PMR,
		PLACES_ELEC,
		PLACES_VELO,
		PLACES_MOTO,
		LAST_UPDATE,
		ID_OBJ,
		LATITUDE,
		LONGITUDE};
}
