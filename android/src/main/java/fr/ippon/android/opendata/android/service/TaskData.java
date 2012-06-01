package fr.ippon.android.opendata.android.service;

import java.util.Collection;

/**
 * Stockage du résultat / de l'erreur d'une tache
 * 
 * @author Damien Raude-Morvan
 *
 * @param <I> Type de retour de la tache (données)
 */
public class TaskData<I> {

	/**
	 * Liste d'items (et donc pas d'erreur) 
	 */
	public Collection<I> items;

	/**
	 * Erreur (et donc pas de liste d'items)
	 */
	public String error;

	/**
	 * Création d'une {@link TaskData} avec une liste d'items.
	 */
	public static <I> TaskData<I> ok(final Collection<I> items) {
		TaskData<I> res = new TaskData<I>();
		res.items = items;
		return res;
	}

	/**
	 * Création d'une {@link TaskData} avec une erreur.
	 */
	public static <I> TaskData<I> error(String message) {
		TaskData<I> res = new TaskData<I>();
		res.error = message;
		return res;
	}

}