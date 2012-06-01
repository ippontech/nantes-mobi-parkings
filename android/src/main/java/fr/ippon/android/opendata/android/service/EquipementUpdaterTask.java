package fr.ippon.android.opendata.android.service;

import javax.inject.Inject;

import android.content.Context;
import android.net.Uri;
import fr.ippon.android.opendata.android.content.Dao;
import fr.ippon.android.opendata.android.content.ParkingContentProvider;
import fr.ippon.android.opendata.android.content.ParkingDao;
import fr.ippon.android.opendata.android.content.ParkingsTableDescription;
import fr.ippon.android.opendata.android.content.convert.ContentValueConverter;
import fr.ippon.android.opendata.android.content.convert.EquipementParkingConverter;
import fr.ippon.android.opendata.data.parkings.ParkingEquipement;
import fr.ippon.android.opendata.data.parkings.ParkingEntity;

/**
 * @author Damien Raude-Morvan
 */
public class EquipementUpdaterTask extends
		AbstractUpdaterTask<ParkingEquipement, ParkingEntity, Long> {

	private static final String TAG = EquipementUpdaterTask.class.getName();

	@Inject
	private EquipementManagerAndroid manager;

	@Inject
	private ParkingDao dao;

	protected EquipementUpdaterTask(Context context) {
		// super == RoboGuice effectue l'injection
		super(context, TAG);
	}

	/**
	 * Utilisation du mécanisme d'update en mode "batch" des parkings.
	 * http://developer
	 * .android.com/guide/topics/providers/content-provider-basics.html#Batch
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public TaskData<ParkingEquipement> call() throws Exception {
		TaskData<ParkingEquipement> res = null;

		// Avant de lancer l'import on vérifie si les données sont a jour
		if (!manager.isParkingsUptodate(context)) {
			res = super.call();
			// Mise a jour du flag de dernier update
			manager.setUptodate(context);
		}

		return res;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected TaskData<ParkingEquipement> getData() {
		return TaskData.ok(manager.getParkings());
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
	protected ContentValueConverter<ParkingEquipement> createConverter() {
		return new EquipementParkingConverter();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Long getId(ParkingEquipement obj) {
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