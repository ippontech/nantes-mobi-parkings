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
