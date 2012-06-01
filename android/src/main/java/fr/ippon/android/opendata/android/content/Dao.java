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
