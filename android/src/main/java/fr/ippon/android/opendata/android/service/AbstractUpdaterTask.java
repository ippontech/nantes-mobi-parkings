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


import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;

import roboguice.util.Ln;
import roboguice.util.RoboAsyncTask;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Log;
import fr.ippon.android.opendata.android.content.Dao;
import fr.ippon.android.opendata.android.content.convert.ContentValueConverter;

/**
 * Représentation d'une tache d'import de données depuis une source de données
 * (fichier ou distante) pour import dans un ContentProvider en mode batch en
 * utilisant les {@link ContentProviderOperation}.
 * 
 * @author Damien Raude-Morvan
 * @param <I>
 *            Type de l'objet que l'on doit convertir (récupérer depuis
 *            l'extérieur
 * @param <O>
 *            Type de l'objet que l'on doit enregistrer dans le Dao
 * @parem <X> Type de l'identifiant _ID dans la base de données
 */
public abstract class AbstractUpdaterTask<I, O, X> extends
		RoboAsyncTask<TaskData<I>> {

	@Inject
	protected ContentResolver resolver;

	/**
	 * Identifiant de la tache (utiliser pour les logs)
	 */
	private String name;

	public AbstractUpdaterTask(Context context, String name) {
		super(context);
		this.name = name;
	}

	/**
	 * Creation d'une operation
	 */
	protected ContentProviderOperation createOperation(ContentValues values,
			Uri singleUri, Uri rootUri) {
		if (singleUri != null) {
			return ContentProviderOperation.newUpdate(singleUri)
					.withValues(values).build();
		} else {
			return ContentProviderOperation.newInsert(rootUri)
					.withValues(values).build();
		}
	}

	/**
	 * Utilisation du mécanisme d'update en mode "batch" des parkings.
	 * http://developer
	 * .android.com/guide/topics/providers/content-provider-basics.html#Batch
	 */
	public TaskData<I> call() throws Exception {
		TaskData<I> result = getData();

		if (result.error == null) {
			ArrayList<ContentProviderOperation> ops = prepare(result.items);
			run(ops);
		}
		return result;
	}

	/**
	 * Preparation d'une liste d'operations a exécuter à partir d'une liste
	 * d'items.
	 * 
	 * @param items
	 *            Liste des elements à traiter.
	 * @return liste d'operations a exécuter
	 */
	protected ArrayList<ContentProviderOperation> prepare(Collection<I> items) {
		Log.d(name, "prepareBatch");
		long startTime = System.currentTimeMillis();
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		for (I item : items) {
			ContentProviderOperation op = createOperation(item);
			ops.add(op);
		}
		Log.d(name, "prepare in " + (System.currentTimeMillis() - startTime)
				+ "ms");
		return ops;
	}

	/**
	 * Création d'une opération à partir de l'object courant.
	 * 
	 * @param obj
	 *            object courant.
	 * @return opération
	 */
	protected ContentProviderOperation createOperation(I obj) {
		ContentValues values = createConverter().getValuesFrom(obj);
		Uri uri = getDao().computeUniqueUri(resolver, getId(obj));
		return createOperation(values, uri, getContentUri());
	}

	protected abstract Uri getContentUri();
	
	protected abstract String getAuthority();
	
	/**
	 * Lancement de l'ensemble des opérations applicables.
	 * 
	 * @param ops
	 *            Liste des opérations
	 */
	protected void run(ArrayList<ContentProviderOperation> ops)
			throws RemoteException, OperationApplicationException {
		long startTime;
		Log.d(name, "applyBatch for " + ops.size() + " elements");
		startTime = System.currentTimeMillis();
		resolver.applyBatch(getAuthority(), ops);
		Log.d(name, "doneBatch in " + (System.currentTimeMillis() - startTime)
				+ "ms");
	}

	/**
	 * Récupération des données à traiter.
	 */
	protected abstract TaskData<I> getData();

	/**
	 * Récupération d'une instance du DAO.
	 */
	protected abstract Dao<O, X> getDao();

	/**
	 * Récupération d'une instance du ContentValueConverter.
	 */
	protected abstract ContentValueConverter<I> createConverter();

	/**
	 * Transformation de l'objet pour obtenir son idenfiant.
	 */
	protected abstract X getId(I obj);

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onInterrupted(Exception e) {
		Ln.d("Interrupting background task %s", this);
	}

}