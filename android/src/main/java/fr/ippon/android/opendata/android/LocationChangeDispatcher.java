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
package fr.ippon.android.opendata.android;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * On met en place un LocationListener unique pour l'ensemble de l'application,
 * afin de centraliser sa configuration (voir la partie registerGpsUpdate).
 * 
 * Cette implémentation se charge alors de dispatcher les différents messages au
 * "sous-listener" qui se sont incrits.
 * 
 * @author Damien Raude-Morvan
 */
public class LocationChangeDispatcher implements LocationListener {

	@Inject
	LocationManager locationManager;
	
	/**
	 * Liste des listeners auquels on dispatche le message
	 */
	private List<LocationListener> listeners;

	public LocationChangeDispatcher() {
		listeners = new ArrayList<LocationListener>();
	}

	/**
	 * Enregistrement de <code>mLocationListener</code> pour recevoir les
	 * changements de position.
	 */
	protected void registerGpsUpdate() {
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			// Abonnement au GPS
			// 2 minutes (car consommateur en resources)
			long gpsMinTime = 2 * 60 * 1000;
			// 20 metres (mais precis)
			float gpsMinDistance = 20;
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
					gpsMinTime, gpsMinDistance, this);
		}

		// Abonnement au CellID + Wifi
		if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			// 20 secondes (car peu consommateur)
			long networkMinTime = 20 * 1000;
			// 300 metres (mais peu precis)
			float networkMinDistance = 300;
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, networkMinTime,
					networkMinDistance, this);
		}
	}

	/**
	 * Permet de stopper la réception des messages GPS
	 */
	protected void unregisterGpsUpdate() {
		// Action inverse de registerGpsUpdate()
		locationManager.removeUpdates(this);
	}

	/**
	 * Permet d'obtenir la derniere localisation (en utilisant le provider avec
	 * la meilleure ACCURACY_FINE).
	 * 
	 * @return Dernière localisation connue (ou rien, en mode avion par exemple)
	 */
	protected Location getLastKnownLocation() {
		Criteria criteres = new Criteria();
		criteres.setAccuracy(Criteria.ACCURACY_FINE);
		boolean enabledOnly = true;
		String bestProvider = locationManager.getBestProvider(criteres, enabledOnly);

		// Impossible de trouver un provider remplissant les critères,
		// on essaye alors avec le NETWORK_PROVIDER
		if (bestProvider == null) {
			return locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		} else {
			return locationManager.getLastKnownLocation(bestProvider);
		}
	}

	/**
	 * Ajout d'un listener
	 */
	public void addListener(LocationListener listener) {
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	/**
	 * Suppression d'un listener
	 */
	public void removeListener(LocationListener listener) {
		listeners.remove(listener);
	}

	/**
	 * {@inheritDoc}
	 */
	public void onLocationChanged(Location location) {
		for (LocationListener listener : listeners) {
			listener.onLocationChanged(location);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void onProviderDisabled(String provider) {
		for (LocationListener listener : listeners) {
			listener.onProviderDisabled(provider);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void onProviderEnabled(String provider) {
		for (LocationListener listener : listeners) {
			listener.onProviderEnabled(provider);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void onStatusChanged(String provider, int status, Bundle extras) {
		for (LocationListener listener : listeners) {
			listener.onStatusChanged(provider, status, extras);
		}
	}

}
