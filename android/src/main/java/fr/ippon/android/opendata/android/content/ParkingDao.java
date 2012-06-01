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

import static android.provider.BaseColumns._ID;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.CONTENT_URI;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.ID_OBJ;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.LAST_UPDATE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.ippon.android.opendata.data.parkings.ParkingEntity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * Repository qui permet d'interragir avec le ContentResolver et de traiter les
 * Cursor en ce qui concerne les Parkings.
 * 
 * @author Damien Raude-Morvan
 */
public class ParkingDao implements Dao<ParkingEntity, Long> {

	/**
	 * Format de stockage de la date de dernière mise à jour.
	 */
	public static final String LAST_UPDATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

	private static final String TAG = ParkingDao.class.getName();

	//
	// METHODES DE TRAITEMENTS DE CURSOR
	//

	public ParkingEntity loadFromCursor(Cursor cursor) {
		if (cursor.isBeforeFirst() || cursor.isAfterLast() || cursor.isClosed()) {
			throw new IllegalArgumentException();
		}

		ParkingEntity park = new ParkingEntity();
		int index = 1; // 0 == _ID

		park.setIdentifiant(cursor.getString(index++));
		park.setNom(cursor.getString(index++));
		park.setStatut(cursor.getInt(index++));
		park.setPriorite(cursor.getInt(index++));
		park.setDisponibles(cursor.getInt(index++));
		park.setSeuilComplet(cursor.getInt(index++));
		park.setPlacesTotales(cursor.getInt(index++));
		park.setHoraires(cursor.getString(index++));
		park.setPlacesPmr(cursor.getInt(index++));
		park.setPlacesElec(cursor.getInt(index++));
		park.setPlacesVelo(cursor.getInt(index++));
		park.setPlacesMoto(cursor.getInt(index++));
		park.setLastUpdate(cursor.getString(index++));
		park.setIdObj(cursor.getInt(index++));
		park.setLatitude(cursor.getDouble(index++));
		park.setLongitude(cursor.getDouble(index++));

		return park;
	}

	//
	// METHODES D'INTERACTION AVEC ContentResolver
	//

	/**
	 * Insertion ou mise à jour d'un parking à partir d'une liste de valeurs.
	 * 
	 * @param resolver
	 *            ContentResolver qui stocke les données.
	 * @param park
	 *            Objet parking possédant un attribut ID_OBJ non-null
	 * @param values
	 *            Liste de valeurs que l'on souhaite insérer
	 * @return Retourne 1 si la mise à jour à fonctionner, 0 sinon
	 */
	protected int updateParking(ContentResolver resolver, ParkingEntity park,
			ContentValues values) {
		Log.d(TAG, "updateParking values");
		return resolver.update(CONTENT_URI, values, ID_OBJ + " = ?",
				new String[] { Long.toString(park.getIdObj()) });
	}

	/**
	 * Permet de construire l'Uri unique de l'objet Parking à partir de son
	 * ID_OBJ.
	 * 
	 * @param resolver
	 *            ContentResolver qui stocke les données.
	 * @param idObj
	 *            attribut ID_OBJ
	 * @return Uri vers le parking
	 */
	public Uri computeUniqueUri(ContentResolver resolver, Long idObj) {
		long id = -1;
		Cursor cursor = resolver.query(CONTENT_URI, new String[] { _ID },
				ID_OBJ + " = ?", new String[] { Long.toString(idObj) }, null);
		if (cursor.moveToFirst()) {
			id = cursor.getLong(0);
		}
		cursor.close();
		if (id == -1) {
			// Impossible de trouver un parking à partir de son id_obj
			// on retourne null
			return null;
		} else {
			return ContentUris.withAppendedId(CONTENT_URI, id);
		}
	}

	/**
	 * Supprime toutes les entrées de type parkings de la base.
	 */
	public int refreshCache(ContentResolver resolver) {
		Log.d(TAG, "refreshCache");
		int delete = resolver.delete(CONTENT_URI, null, null);
		System.out.println("Removed " + delete + " parkings from DB");
		return delete;
	}

	public Date getLastUpdate(ContentResolver resolver) {
		Log.d(TAG, "getLastUpdate");
		Date date = null;

		String[] projection = new String[] { LAST_UPDATE };
		String sortOrder = LAST_UPDATE + " DESC";
		Cursor cursor = resolver.query(CONTENT_URI, projection, null, null,
				sortOrder);
		if (null != cursor && cursor.moveToNext()) {
			SimpleDateFormat SDF_DATE = new SimpleDateFormat(LAST_UPDATE_FORMAT);
			int index = cursor.getColumnIndex(LAST_UPDATE);
			try {
				date = SDF_DATE.parse(cursor.getString(index));
			} catch (ParseException e) {
				date = null;
			}
		}
		cursor.close();

		return date;
	}

}
