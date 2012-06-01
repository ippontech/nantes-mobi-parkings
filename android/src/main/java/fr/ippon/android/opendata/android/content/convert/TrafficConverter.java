package fr.ippon.android.opendata.android.content.convert;

import static fr.ippon.android.opendata.android.content.TrafficTableDescription.IS_VALID;
import static fr.ippon.android.opendata.android.content.TrafficTableDescription.LAST_UPDATE;
import static fr.ippon.android.opendata.android.content.TrafficTableDescription.PATH_ID;
import static fr.ippon.android.opendata.android.content.TrafficTableDescription.TEMPS;

import java.text.SimpleDateFormat;

import android.content.ContentValues;
import fr.ybo.opendata.nantes.modele.Itineraire;

/**
 * Convertion d'un Itineraire vers un ContentValues destiné au TrafficDao.
 * 
 * @author Damien Raude-Morvan
 */
public class TrafficConverter implements ContentValueConverter<Itineraire> {
	
	/**
	 * Format de stockage de la date de dernière mise à jour.
	 */
	public static final String LAST_UPDATE_FORMAT = "dd/MM/yyyy HH:mm:ss";

	/**
	 * {@inheritDoc}
	 */
	public ContentValues getValuesFrom(Itineraire itineraire) {
		ContentValues cv = new ContentValues();

		SimpleDateFormat SDF_DATE = new SimpleDateFormat(LAST_UPDATE_FORMAT);
		cv.put(PATH_ID, itineraire.getIdentifiant());
		cv.put(IS_VALID, itineraire.isValide());
		cv.put(TEMPS, itineraire.getTemps());
		cv.put(LAST_UPDATE, SDF_DATE.format(itineraire.getLastUpdate()));

		return cv;
	}

}
