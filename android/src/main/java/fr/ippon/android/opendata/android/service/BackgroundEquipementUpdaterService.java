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

import fr.ippon.android.opendata.data.parkings.ParkingEquipement;

/**
 * Ce service prend en charge l'exécution en tache de fond pour l'insertion
 * initiale des données relatives aux parkings.
 * 
 * @author Damien Raude-Morvan
 */
public class BackgroundEquipementUpdaterService extends
		AbstractServiceWithStatus<TaskData<ParkingEquipement>> {

	public BackgroundEquipementUpdaterService() {
		super("BackgroundEquipementUpdaterService");
	}

	@Override
	protected Callable<TaskData<ParkingEquipement>> createTask() {
		return new EquipementUpdaterTask(getApplicationContext());
	}
}
