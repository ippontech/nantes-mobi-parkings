package fr.ippon.android.opendata.android.content.convert;

import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.DISPONIBLES;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.IDENTIFIANT;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.ID_OBJ;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.LAST_UPDATE;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.PLACES_TOTALES;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.PRIORITE;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.SEUIL_COMPLET;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.STATUS;

import java.text.SimpleDateFormat;

import android.content.ContentValues;
import fr.ybo.opendata.nantes.modele.Parking;

/**
 * Convertion d'un Parking vers un ContentValues destiné au ParkingDao.
 * 
 * @author Damien Raude-Morvan
 */
public class ParkingConverter implements ContentValueConverter<Parking> {

	/**
	 * Format de stockage de la date de dernière mise à jour.
	 */
	public static final String LAST_UPDATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	
	/**
	 * {@inheritDoc}
	 */
	public ContentValues getValuesFrom(final Parking park) {
		ContentValues cv = new ContentValues();

		SimpleDateFormat SDF_DATE = new SimpleDateFormat(LAST_UPDATE_FORMAT);

		cv.put(IDENTIFIANT, park.getIdentifiant());
		// Inutile de mettre a jour le nom (données statiques)
		// cv.put(NOM, park.getNom());
		cv.put(STATUS, park.getStatut().getValue());
		cv.put(PRIORITE, park.getPriorite());
		cv.put(DISPONIBLES, park.getDisponibles());
		cv.put(SEUIL_COMPLET, park.getSeuilComplet());
		cv.put(PLACES_TOTALES, park.getPlacesTotales());
		if (park.getLastUpdate() != null) {
			cv.put(LAST_UPDATE, SDF_DATE.format(park.getLastUpdate()));
		}
		cv.put(ID_OBJ, park.getIdObj());
		// Inutile de mettre a jour la lat/long (données statiques)
		// cv.put(LATITUDE, park.getLatitude());
		// cv.put(LONGITUDE, park.getLongitude());

		return cv;
	}
}
