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

import android.util.Log;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import fr.ippon.android.opendata.android.service.EquipementManagerAndroid;
import fr.ybo.opendata.nantes.util.EquipementManager;

public class ParkingAndroidModule extends AbstractModule {

	private static final String TAG = ParkingAndroidModule.class.getName();

	@Override
	protected void configure() {
		Log.i(TAG, "configure");

		// Surcharge de EquipementManager pour utiliser le
		// EquipementManagerAndroid
		// qui sait lire des resources de types "Assets" Android
		bind(EquipementManager.class).to(EquipementManagerAndroid.class);

		// Surcharge de la property @Named opendatanantes_key
		// pour aller la lire dans la configuration Android
		String apikey = MainApplication.getAppContext().getString(
				R.string.data_nantes_api_key);
		bind(String.class).annotatedWith(Names.named("opendatanantes_key"))
				.toInstance(apikey);
	}
}
