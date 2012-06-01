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
package fr.ippon.android.opendata.android.service;

import android.content.Intent;

/**
 * Représentation de l'état d'un {@link android.app.Service}.
 * 
 * @author Damien Raude-Morvan
 */
public enum ServiceStatus {
	/**
	 * Le service est démarré
	 */
	SERVICE_STARTED,
	/**
	 * Le service exécute actuellement une tache
	 */
	SERVICE_RUNNING,
	/**
	 * La tache exécutée par le service vient de se terminer
	 */
	SERVICE_FINISHED,
	/**
	 * Le service est arrété
	 */
	SERVICE_STOPPED;

	/**
	 * Création d'un {@link Intent} à partir de l'enum
	 */
	public Intent intent() {
		Intent intent = new Intent(name());
		return intent;
	}

	/**
	 * Création d'un {@link Intent} vaec un message d'erreur à partir de l'enum
	 */
	public Intent intent(final String errorMessage) {
		Intent intent = intent();
		if (errorMessage != null) {
			intent.putExtra("error", errorMessage);
		}
		return intent;
	}

	/**
	 * Est-ce que <code>name</code> corresspond à l'enum courante ?
	 */
	public boolean is(final String name) {
		return name().equals(name);
	}
}
