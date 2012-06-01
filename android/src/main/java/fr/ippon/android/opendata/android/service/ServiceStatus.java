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
