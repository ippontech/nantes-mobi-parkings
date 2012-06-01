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
