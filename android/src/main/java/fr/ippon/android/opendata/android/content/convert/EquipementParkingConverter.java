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
package fr.ippon.android.opendata.android.content.convert;

import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.HORAIRES;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.ID_OBJ;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.LAST_UPDATE;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.LATITUDE;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.LONGITUDE;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.NOM;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.PLACES_ELEC;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.PLACES_MOTO;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.PLACES_PMR;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.PLACES_VELO;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentValues;
import fr.ippon.android.opendata.data.parkings.ParkingEquipement;

/**
 * Convertion d'un Equipement vers un ContentValues destiné au ParkingDao.
 * 
 * @author Damien Raude-Morvan
 */
public class EquipementParkingConverter implements ContentValueConverter<ParkingEquipement> {
	
	/**
	 * Format de stockage de la date de dernière mise à jour.
	 */
	public static final String LAST_UPDATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

	/**
	 * {@inheritDoc}
	 */
	public ContentValues getValuesFrom(final ParkingEquipement equip) {
		ContentValues cv = new ContentValues();

		SimpleDateFormat SDF_DATE = new SimpleDateFormat(LAST_UPDATE_FORMAT);
		
		// Suppression des éléments nom déterminants
		// dans le nom du parking (permet de réduire les données affichées)
		String name = equip.getNom();
		name = name.replaceFirst("(?i)Parc relais tram", "")
				.replaceFirst("(?i)Parc relais TER", "")
				.replaceFirst("(?i)Parc relais busway", "")
				.replaceFirst("(?i)Parc relais", "")
				.replaceFirst("(?i)Pac en enclos", "")
				.replaceFirst("(?i)Parc en enclos", "")
				.replaceFirst("(?i)Parking ouvrage", "")
				.trim()
				.toUpperCase();
		
		cv.put(NOM, name);
		cv.put(LAST_UPDATE, SDF_DATE.format(new Date()));
		cv.put(ID_OBJ, equip.getIdObj());
		cv.put(LATITUDE, equip.getLatitude());
		cv.put(LONGITUDE, equip.getLongitude());
		
		// Ajout des données complémentaires
		cv.put(HORAIRES, equip.getHoraires());
		cv.put(PLACES_VELO, equip.getPlacesVelo());
		cv.put(PLACES_PMR, equip.getPlacesPmr());
		cv.put(PLACES_ELEC, equip.getPlacesElec());
		cv.put(PLACES_MOTO, equip.getPlacesMoto());

		return cv;
	}

}
