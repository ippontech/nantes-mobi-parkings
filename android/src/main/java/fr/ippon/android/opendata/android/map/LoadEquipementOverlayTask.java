package fr.ippon.android.opendata.android.map;

import static com.google.common.collect.Collections2.filter;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.CONTENT_URI;
import static fr.ippon.android.opendata.android.content.ParkingsTableDescription.PROJECTION_ALL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import fr.ippon.android.opendata.android.content.ParkingDao;
import fr.ippon.android.opendata.android.service.EquipementManagerAndroid;
import fr.ippon.android.opendata.data.distance.BoundingBox;
import fr.ippon.android.opendata.data.parkings.ParkingEntity;
import fr.ippon.android.opendata.data.parkings.DistancePredicate;

/**
 * Tache qui permet de charger l'ensemble des parkings depuis la base de données
 * locale.
 * 
 * Une fois la liste mise à jour, cela déclenche automatiquemen le redraw de la
 * carte.
 * 
 * @author Damien Raude-Morvan
 */
public class LoadEquipementOverlayTask extends
		AbstractLoadOverlayTask<EquipementOverlayItem> {

	@Inject
	private ContentResolver contentResolver;

	@Inject
	private ParkingDao parkingDao;
	
	@Inject
	private EquipementManagerAndroid equipementManager;

	private BoundingBox boundingBox;

	protected LoadEquipementOverlayTask(final Context context,
			final AbstractItemizedOverlay<EquipementOverlayItem> overlay,
			final BoundingBox boundingBox) {
		// super == RoboGuice effectue l'injection
		super(context, overlay);
		this.boundingBox = boundingBox;
	}

	public List<EquipementOverlayItem> call() throws Exception {
		try {
			// Attente de 200 millisecondes
			// ... on ne sait jamais on pourrait se faire annuler 
			Thread.sleep(200);
		} catch (InterruptedException e) {
		}

		List<EquipementOverlayItem> items = new ArrayList<EquipementOverlayItem>();
		//Log.d(TAG, "call");
		Cursor cursor = contentResolver.query(CONTENT_URI, PROJECTION_ALL,
				null, null, null);
		if (cursor.moveToFirst()) {
			List<ParkingEntity> listParkings = new ArrayList<ParkingEntity>();
			do {
				ParkingEntity parking = parkingDao.loadFromCursor(cursor);
				listParkings.add(parking);
			} while (cursor.moveToNext());

			// Filtre des parkings avec le viewport actuel
			Collection<ParkingEntity> filtered;
			if (boundingBox != null) {
				// Filtre des parkings par bounding box
				filtered = filter(listParkings, new DistancePredicate(
						boundingBox));
			} else {
				filtered = listParkings;
			}

			// Injection des parkings filtrés
			for (ParkingEntity park : filtered) {
				EquipementOverlayItem item = EquipementOverlayItem.create(equipementManager, park);
				items.add(item);
			}

		}
		cursor.close();
		return items;
	}
}