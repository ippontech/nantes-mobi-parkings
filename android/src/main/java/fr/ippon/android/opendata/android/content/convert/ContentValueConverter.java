package fr.ippon.android.opendata.android.content.convert;

import android.content.ContentValues;

/**
 * L'objectif d'un ContentValueConverter est de permettre de convertir
 * n'importe quel POJO afin d'obtenir un ContentValues qui puisse être
 * injecter en base de données.
 * 
 * @author Damien Raude-Morvan
 * @param <T>
 *            Type de l'objet que l'on obtient depuis une source externe
 */
public interface ContentValueConverter<T> {
	
	/**
	 * Tranformation d'un objet en ContentValues destiner à être envoyer à un ContentProvider.
	 * 
	 * @param obj Objet que l'on souhaite convertir.
	 * @return ContentValues contenant les champs à mettre à jour
	 */
	ContentValues getValuesFrom(T obj);

}
