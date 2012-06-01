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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.ybo.opendata.nantes.modele.StatutParking;

/**
 * Entité manipulée par l'application NMP (dans les DAO et les couches d'UI)
 * au contraire de la classe {@link fr.ybo.opendata.nantes.modele.Parking} qui
 * elle provient de l'API distante.
 * 
 * On peut ainsi utiliser des champs complémentaires qui ne sont pas présents
 * dans l'API distante.
 */
public class ParkingEntity {

	/**
	 * Identifiant du parking.
	 */
	private String identifiant;
	
	/**
	 * Nom du parking.
	 */
	private String nom;
	
	/**
	 * Statut du parking.
	 */
	private StatutParking statut;

	/**
	 * Niveau de priorité du mode automatique.
	 */
	private int priorite;
	
	/**
	 * Nombre de places disponibles.
	 */
	private int disponibles;
	
	/**
	 * Nombre de places minimum devant déclencher l'affichage 'COMPLET'.
	 */
	private int seuilComplet;
	
	/**
	 * Nombre de places ouvertes au clients horaires.
	 */
	private int placesTotales;

	/**
	 * Timestamp de l'information (format <> de xs:date) au delà de 15 min,
	 * l'information ne doit plus être considérée comme valide.
	 */
	private Date lastUpdate;

	/**
	 * Identifiant de l'objet.
	 */
	private int idObj;

	/**
	 * Latitude.
	 */
	private Double latitude;

	/**
	 * Longitude.
	 */
	private Double longitude;

	/**
	 * Horaires du parking (potentiellement avec de la mise en forme)
	 */
	private String horaires;

	/**
	 * Nombre de places pour personnes à mobilité réduite
	 */
	private int placesPmr;

	/**
	 * Nombre de places avec bornes de recharge véhicules électriques
	 */
	private int placesElec;

	/**
	 * Nombre de places motos
	 */
	private int placesVelo;

	/**
	 * Nombre de places vélos
	 */
	private int placesMoto;

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public StatutParking getStatut() {
		return statut;
	}

	public void setStatut(int statut) {
		this.statut = StatutParking.fromValue(statut);
	}

	public int getPriorite() {
		return priorite;
	}

	public void setPriorite(int priorite) {
		this.priorite = priorite;
	}

	public int getDisponibles() {
		return disponibles;
	}

	public void setDisponibles(int disponibles) {
		this.disponibles = disponibles;
	}

	public int getSeuilComplet() {
		return seuilComplet;
	}

	public void setSeuilComplet(int seuilComplet) {
		this.seuilComplet = seuilComplet;
	}

	public int getPlacesTotales() {
		return placesTotales;
	}

	public void setPlacesTotales(int placesTotales) {
		this.placesTotales = placesTotales;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		final SimpleDateFormat SDF_DATE = new SimpleDateFormat(
				"dd/MM/yyyy HH:mm:ss");
		try {
			this.lastUpdate = SDF_DATE.parse(lastUpdate);
		} catch (ParseException exception) {
		}
	}

	public int getIdObj() {
		return idObj;
	}

	public void setIdObj(int idObj) {
		this.idObj = idObj;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

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
