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