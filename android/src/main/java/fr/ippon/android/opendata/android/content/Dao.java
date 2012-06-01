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
package fr.ippon.android.opendata.android.content;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

/**
 * Représentation du pattern DAO pour les méthodes d'accès aux données
 * persistés.
 * 
 * @author Damien Raude-Morvan
 * @param <T>
 *            Type de l'objet principal manipulé par ce DAO
 * @param <X>
 *            Type de l'idenfiant fonctionnel de l'objet principal
 */
public interface Dao<T, X> {

	/**
	 * Calcul de l'Uri unique de l'objet dans le ContentProvider
	 * 
	 * @param resolver ContentProvider
	 * @param obj Objet pour lequel on souhaite récupérer sont Uri
	 * @return Uri ou null si l'objet n'existe pas dans le CP
	 */
	Uri computeUniqueUri(ContentResolver resolver, X obj);

	/**
	 * Chargement de l'objet (en utilisant la projection par défaut)
	 * 
	 * @param cursor Curseur suite à une requete dans le CP
	 * @return Objet chargé à partir des données du Cursor
	 */
	T loadFromCursor(Cursor cursor);

}
