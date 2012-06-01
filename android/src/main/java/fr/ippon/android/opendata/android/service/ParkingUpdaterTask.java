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

import javax.inject.Inject;

import android.content.Context;
import android.net.Uri;
import fr.ippon.android.opendata.android.content.Dao;
import fr.ippon.android.opendata.android.content.ParkingContentProvider;
import fr.ippon.android.opendata.android.content.ParkingDao;
import fr.ippon.android.opendata.android.content.ParkingsTableDescription;
import fr.ippon.android.opendata.android.content.convert.ContentValueConverter;
import fr.ippon.android.opendata.android.content.convert.ParkingConverter;
import fr.ippon.android.opendata.data.parkings.ParkingEntity;
import fr.ybo.opendata.nantes.OpenDataApi;
import fr.ybo.opendata.nantes.exceptions.ApiReseauException;
import fr.ybo.opendata.nantes.modele.Parking;

/**
 * Tache qui effectue un appel distant par data.nantes.fr (via
 * {@link ParkingDataProxy}) pour ensuite effectuer le stockage par le biais de
 * {@link ParkingDao}.
 * 
 * @author Damien Raude-Morvan
 */
public class ParkingUpdaterTask extends
		AbstractUpdaterTask<Parking, ParkingEntity, Long> {

	private static final String TAG = ParkingUpdaterTask.class.getName();

	@Inject
	private OpenDataApi api;

	@Inject
	private ParkingDao dao;

	protected ParkingUpdaterTask(Context context) {
		// super == RoboGuice effectue l'injection
		super(context, TAG);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected TaskData<Parking> getData() {
		try {
			return TaskData.ok(api.getParkings());
		} catch (ApiReseauException e) {
			return TaskData.error(e.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Dao<ParkingEntity, Long> getDao() {
		return dao;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ContentValueConverter<Parking> createConverter() {
		return new ParkingConverter();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Long getId(Parking obj) {
		return new Long(obj.getIdObj());
	}

	@Override
	protected Uri getContentUri() {
		return ParkingsTableDescription.CONTENT_URI;
	}
	
	@Override
	protected String getAuthority() {
		return ParkingContentProvider.AUTHORITY;
	}

}