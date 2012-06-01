package fr.ippon.android.opendata.data.parkings;

import fr.ybo.moteurcsv.annotation.BaliseCsv;
import fr.ybo.moteurcsv.annotation.FichierCsv;
import fr.ybo.opendata.nantes.modele.Equipement;
import fr.ybo.opendata.nantes.util.MyAdapteurInt;

/**
 * En complément des données lues depuis {@link Equipement}, ajout de données
 * complémentaires en provenance d'autres sources d'informations que data.nantes.fr.
 */
@FichierCsv(value = "Equipements_publics_deplacement.csv", separateur = ";")
public class ParkingEquipement extends Equipement {
	
	/**
	 * Horaires du parking (potentiellement avec de la mise en forme)
	 */
	@BaliseCsv(value = "HORAIRES")
	private String horaires;

	/**
	 * Nombre de places pour personnes à mobilité réduite
	 */
	@BaliseCsv(value = "PLACES_PMR", adapter = MyAdapteurInt.class)
	private int placesPmr;

	/**
	 * Nombre de places avec bornes de recharge véhicules électriques
	 */
	@BaliseCsv(value = "PLACES_ELEC", adapter = MyAdapteurInt.class)
	private int placesElec;

	/**
	 * Nombre de places motos
	 */
	@BaliseCsv(value = "PLACES_VELO", adapter = MyAdapteurInt.class)
	private int placesVelo;

	/**
	 * Nombre de places vélos
	 */
	@BaliseCsv(value = "PLACES_MOTO", adapter = MyAdapteurInt.class)
	private int placesMoto;
	

	public String getHoraires() {
		return horaires;
	}

	public void setHoraires(String horaires) {
		this.horaires = horaires;
	}


	public int getPlacesPmr() {
		return placesPmr;
	}

	public void setPlacesPmr(int placesPmr) {
		this.placesPmr = placesPmr;
	}

	public int getPlacesElec() {
		return placesElec;
	}

	public void setPlacesElec(int placesElec) {
		this.placesElec = placesElec;
	}

	public int getPlacesVelo() {
		return placesVelo;
	}

	public void setPlacesVelo(int placesVelo) {
		this.placesVelo = placesVelo;
	}

	public int getPlacesMoto() {
		return placesMoto;
	}

	public void setPlacesMoto(int placesMoto) {
		this.placesMoto = placesMoto;
	}

}
