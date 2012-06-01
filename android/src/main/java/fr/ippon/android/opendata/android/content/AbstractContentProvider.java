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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentProvider;
import android.net.Uri;

public abstract class AbstractContentProvider extends ContentProvider {

	public AbstractContentProvider() {
		super();
	}

	/**
	 * Ajout automatique de la <b>valeur de l'ID</b> dans la requete vers
	 * SQLite.
	 * 
	 * @param uri
	 *            URL de l'item
	 * @param whereArgs
	 *            Arguments de recherche actuels
	 * @return Arguments de recherche avec l'ID
	 */
	protected String[] appendIdValueToWhere(Uri uri, String[] whereArgs) {
		List<String> whereParams = new ArrayList<String>();
		if (whereArgs != null) {
			whereParams = Arrays.asList(whereArgs);
		}
		whereParams.add(uri.getLastPathSegment());
		whereArgs = whereParams.toArray(new String[] {});
		return whereArgs;
	}

	/**
	 * Ajout automatique du <b>champ _ID</b> dans la requete vers SQLite.
	 * 
	 * @param where
	 *            Requete de recherche actuelle
	 * @return Requete de recherche avec l'ID
	 */
	protected String appendIdClauseToWhere(String where) {
		if (where == null) {
			where = "_ID = ?";
		} else {
			where = where + " AND _ID = ?";
		}
		return where;
	}

}