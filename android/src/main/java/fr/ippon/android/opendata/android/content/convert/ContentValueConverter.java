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
