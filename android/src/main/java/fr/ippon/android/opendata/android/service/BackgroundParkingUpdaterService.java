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

import java.util.concurrent.Callable;

import fr.ybo.opendata.nantes.modele.Parking;

/**
 * Ce service prend en charge l'exécution en tache de fond de la mise à jour des
 * données temps réelles des parkings.
 * 
 * @author Damien Raude-Morvan
 */
public class BackgroundParkingUpdaterService extends
		AbstractServiceWithStatus<TaskData<Parking>> {

	private static BackgroundParkingUpdaterService service;

	public BackgroundParkingUpdaterService() {
		super("BackgroundParkingUpdaterService");
		service = this;
	}

	public static BackgroundParkingUpdaterService getService() {
		return service;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Callable<TaskData<Parking>> createTask() {
		return new ParkingUpdaterTask(getApplicationContext());
	}
}
