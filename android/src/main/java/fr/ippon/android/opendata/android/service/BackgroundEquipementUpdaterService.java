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
