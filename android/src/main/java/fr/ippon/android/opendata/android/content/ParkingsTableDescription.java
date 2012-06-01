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
