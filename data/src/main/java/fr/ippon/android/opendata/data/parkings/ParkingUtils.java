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

import java.text.NumberFormat;
import java.util.Date;
import java.util.Map;

import fr.ippon.android.opendata.data.distance.DistanceCalculator;
import fr.ippon.android.opendata.data.distance.GpsPoint;
import fr.ybo.opendata.nantes.modele.StatutParking;
import fr.ybo.opendata.nantes.modele.Type;

public class ParkingUtils {

	/**
	 * Delai maximal (en millisecondes) avant que la disponibilité du parking ne
	 * soit considérée comme trop ancienne
	 */
	private static final long MAX_LASTUPDATE_DELAY = 15 * 60 * 1000;

	/**
	 * Calcul du libellé à afficher pour la distance entre le parking et la
	 * position de l'usager.
	 * 
	 * @param dc
	 *            Outil de calcul de la distance
	 * @param parking
	 *            Parking
	 * @param location
	 *            Position de l'usager
	 * @return Chaine XX km ou XX m
	 */
	public static String getDistanceStatus(final DistanceCalculator dc,
			final GpsPoint parkingPoint, final GpsPoint currentPoint) {

		double distance = dc.getDistanceBetweenPoints(currentPoint,
				parkingPoint);

		// Si la distance est supérieure à 1200 metres, on affiche en
		// kilometre
		StringBuilder displayedDistance = new StringBuilder("À : ");
		if (distance > 1200) {
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(1);
			displayedDistance.append(nf.format(distance / 1000)).append(" km")
					.toString();
		} else {
			NumberFormat nf = NumberFormat.getInstance();
			nf.setMaximumFractionDigits(0);
			displayedDistance.append(nf.format(distance)).append(" m")
					.toString();
		}

		return displayedDistance.toString();
	}

	/**
	 * Calcul du libellé à afficher pour le status du parking.
	 * 
	 * @param context
	 * 
	 * @param parking
	 *            Parking
	 * @return Chaine "(XX / XX)" ou bien le status du parking
	 */
	public static String getPlacesStatus(final Map<String, String> labels,
			final ParkingEntity parking) {
		StringBuilder displayPlaces = new StringBuilder();

		if (hasValidPlacesStatus(parking)) {
			int placesRestantes = parking.getDisponibles();
			int placesTotalesHoraires = parking.getPlacesTotales();
			int placesSeuilComplet = parking.getSeuilComplet();
			if (parking.getStatut() == StatutParking.ABONNES) {
				displayPlaces.append(labels.get("SUBSCRIBE_ONLY"));
			} else if (parking.getStatut() == StatutParking.FERME) {
				displayPlaces.append(labels.get("CLOSED"));
			} else if (placesRestantes <= placesSeuilComplet) {
				displayPlaces.append(labels.get("FULL"));
			} else {
				displayPlaces.append(String.format(labels.get("OK"), placesRestantes));
			}
		} else {
			displayPlaces.append(labels.get("INVALID"));
		}

		return displayPlaces.toString();
	}

	/**
	 * Indique si l'affichage du nombre de place est valide
	 * 
	 * @param parking
	 * @return
	 */
	protected static boolean hasValidPlacesStatus(final ParkingEntity parking) {
		Date parkLastUpdate = parking.getLastUpdate();
		Date maxDelay = new Date(System.currentTimeMillis()
				- MAX_LASTUPDATE_DELAY);
		return parkLastUpdate.after(maxDelay)
				&& parking.getStatut() != StatutParking.INVALIDE;
	}

	public static String formatParkName(final ParkingEquipement eq) {
		StringBuilder nom = new StringBuilder();
		nom.append("<b>");
		nom.append(eq.getNom());
		nom.append("</b>");
		Type type = eq.getType();
		if(type != null){
			nom.append(" (type <i>");
			nom.append(type.getLabel());
			nom.append("</i>)");
		}
		return nom.toString();
	}
	
	public static String getParkingDescription(final ParkingEntity park, final ParkingEquipement eq) {
		StringBuilder descr = new StringBuilder();
		descr.append(formatParkName(eq));
		descr.append(": ");
		if (hasValidPlacesStatus(park)) {
			int placesRestantes = park.getDisponibles();
			int placesSeuilComplet = park.getSeuilComplet();
			if (park.getStatut() == StatutParking.ABONNES) {
				descr.append("réservé aux abonnés");
			} else if (park.getStatut() == StatutParking.FERME) {
				descr.append("fermé");
			} else if (placesRestantes <= placesSeuilComplet) {
				descr.append("complet");
			} else {
				descr.append("<b>");
				descr.append(placesRestantes);
				descr.append("</b>");
				descr.append(" place(s) restante(s)");
			}
		} else {
			descr.append("nombre de place(s) inconnu");
		}
		if (eq != null &&eq.getAdresse() != null && eq.getCommune() != null) {
				descr.append("<br/>Adresse: ");
				descr.append(eq.getAdresse());
				descr.append(", ");
				descr.append(eq.getCommune());
		}

		if (park.getHoraires() != null && park.getHoraires().length() > 0) {
			descr.append("<br/><br/>Horaires : <small>").append(park.getHoraires()).append("</small>");
		}
		if (park.getPlacesMoto() > 0 || park.getPlacesElec() > 0 || park.getPlacesPmr() > 0 || park.getPlacesVelo() > 0) {
			descr.append("<br/><br/>Emplacement réservés : <small>");
			boolean added = false;
			if (park.getPlacesMoto() > 0) {
				if (added) { descr.append(", "); }
				descr.append(park.getPlacesMoto()).append(" motos");
				added = true;
			}
			if (park.getPlacesElec() > 0) {
				if (added) { descr.append(", "); }
				descr.append(park.getPlacesElec()).append(" véhicules électriques");
				added = true;
			}
			if (park.getPlacesPmr() > 0) {
				if (added) { descr.append(", "); }
				descr.append(park.getPlacesPmr()).append(" PMR");
				added = true;
			}
			if (park.getPlacesVelo() > 0) {
				if (added) { descr.append(", "); }
				descr.append(park.getPlacesVelo()).append(" vélos");
				added = true;
			}
			descr.append("</small>");
		}
		
		return descr.toString();
	}
}
