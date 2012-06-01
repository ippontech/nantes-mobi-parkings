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
