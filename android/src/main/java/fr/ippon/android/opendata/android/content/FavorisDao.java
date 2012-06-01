package fr.ippon.android.opendata.android.content;

import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.CONTENT_URI;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.FAVORI;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.ID_OBJ;

import javax.inject.Inject;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import fr.ippon.android.opendata.data.parkings.ParkingEntity;

/**
 * Stockage des parkings selectionnés en "favoris" dans un fichier de préférence
 * spécifique. Chaque parking "favoris" correspond a une ligne du fichier (clef
 * = identifiant du parking, valeur arbitraire = true).
 * 
 * @author Damien Raude-Morvan
 */
public class FavorisDao extends ParkingDao {

	private static final String TAG = FavorisDao.class.getName();

	@Inject
	private ParkingDao parkingDao;

	@Inject
	private ContentResolver resolver;

	/**
	 * Permet de vérifier si le parking est un "favori" à partir de son
	 * identifiant.
	 */
	public boolean isFavori(Context context, long idObj) {
		boolean retFavori = false;

		String[] projection = new String[] { FAVORI };
		String selection = ID_OBJ + "= ?";
		String[] selectArgs = new String[] { String.valueOf(idObj) };
		Cursor cursor = resolver.query(CONTENT_URI, projection, selection,
				selectArgs, null);
		try {
			if (cursor.moveToFirst()) {
				retFavori = cursor.getInt(0) == 1 ? true : false;
			}
		} finally {
			cursor.close();
		}

		return retFavori;

	}

	/**
	 * Ajout d'un favori (attribut favori == true).
	 */
	public void addFavori(Context context, ParkingEntity park) {
		Log.d(TAG, "addFavori:" + park.getIdentifiant());

		ContentValues mUpdateValues = new ContentValues();
		mUpdateValues.put(FAVORI, 1);

		parkingDao.updateParking(resolver, park, mUpdateValues);
	}

	/**
	 * Suppression d'un favori (attribut favori == false).
	 */
	public void removeFavori(Context context, ParkingEntity park) {
		Log.d(TAG, "removeFavori:" + park.getIdentifiant());

		ContentValues mUpdateValues = new ContentValues();
		mUpdateValues.put(FAVORI, 0);

		parkingDao.updateParking(resolver, park, mUpdateValues);
	}

}
