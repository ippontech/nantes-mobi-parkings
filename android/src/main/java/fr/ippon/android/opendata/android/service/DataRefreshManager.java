package fr.ippon.android.opendata.android.service;

import java.util.Date;

import android.content.Context;
import android.content.Intent;
import fr.ippon.android.opendata.android.MainApplication;

/**
 * Classe qui permet de gérer l'intervalle de rafraichissement des données en
 * provenance de data.nantes.fr (et donc les appels vers les services de
 * Background*UpdaterService).
 * 
 * @author Damien Raude-Morvan
 */
public class DataRefreshManager {

	/**
	 * Temps minimal entre deux refresh 'automatique' des données lors de la
	 * restauration de l'activité.
	 */
	public static final int THROTTLING_MIN_INTERVAL = 5 * 60 * 1000;

	/**
	 * Date de la prochaine mise à jour. null lors du premier lancement.
	 */
	private static Date nextUpdateDate;

	/**
	 * Demande de refresh des données
	 * 
	 * @param force
	 *            Permet d'indiquer si l'on souhaite tenir compte de
	 *            l'intervalle minimal de refresh.
	 */
	public static void requestRefresh(final Context context, final boolean force) {
		if (force || isDataExpired()) {
			// Lancement des services
			context.startService(new Intent(context,
					BackgroundEquipementUpdaterService.class));
			context.startService(new Intent(context,
					BackgroundParkingUpdaterService.class));
            // Le service n'est lancé que si la préférence l'indique
            if (MainApplication.isDefaultPrefShowTraffic()) {
                context.startService(new Intent(context,
                        BackgroundFreewaySegmentColorUpdaterService.class));
                context.startService(new Intent(context,
                        BackgroundCityCenterSegmentColorUpdaterTask.class));
            }
            nextUpdateDate = computeNextUpdateDate();
		}
	}

	/**
	 * Calcul de la prochaine date de mise à jour.
	 */
	protected static Date computeNextUpdateDate() {
		return new Date(System.currentTimeMillis() + THROTTLING_MIN_INTERVAL);
	}

	/**
	 * Calcul si les données sont expirées.
	 */
	protected static boolean isDataExpired() {
		return nextUpdateDate != null && nextUpdateDate.before(new Date());
	}
}
