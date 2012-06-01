package fr.ippon.android.opendata.data.parkings;

import com.google.common.base.Predicate;

import fr.ippon.android.opendata.data.distance.BoundingBox;
import fr.ippon.android.opendata.data.distance.GpsPoint;

/**
 * Permet de filtrer une liste de parkings par
 * rapport à une zone particulière.
 * 
 * @author Damien Raude-Morvan
 */
public class DistancePredicate implements Predicate<ParkingEntity> {
	
	/**
	 * Zone dans laquelle on souhaite filtrer
	 */
	private final BoundingBox boundingBox;

	public DistancePredicate(final BoundingBox boundingBox) {
		this.boundingBox = boundingBox;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean apply(final ParkingEntity parking) {
		assert parking.getLatitude() != null;
		assert parking.getLongitude() != null;
		GpsPoint point = new GpsPoint(parking.getLatitude(),
				parking.getLongitude());
		return boundingBox.contains(point);
	}
}