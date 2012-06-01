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
package fr.ippon.android.opendata.data;

import com.google.inject.AbstractModule;

import fr.ippon.android.opendata.data.distance.AsTheCrowFliesDistanceCalculator;
import fr.ippon.android.opendata.data.distance.DistanceCalculator;
import fr.ybo.opendata.nantes.util.Connecteur;
import fr.ybo.opendata.nantes.util.EquipementManager;
import fr.ybo.opendata.nantes.util.HttpConnecteur;

public class ParkingDataModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(DistanceCalculator.class).to(
				AsTheCrowFliesDistanceCalculator.class);

		// Implémentation HTTP
		bind(Connecteur.class).to(HttpConnecteur.class);

		// Compatibilité avec les accès de type getInstance() !
		requestStaticInjection(EquipementManager.class);
	}
}
