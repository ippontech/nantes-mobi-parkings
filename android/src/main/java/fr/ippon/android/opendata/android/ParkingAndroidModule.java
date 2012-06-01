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
