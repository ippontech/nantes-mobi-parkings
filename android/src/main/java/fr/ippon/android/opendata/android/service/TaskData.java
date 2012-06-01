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