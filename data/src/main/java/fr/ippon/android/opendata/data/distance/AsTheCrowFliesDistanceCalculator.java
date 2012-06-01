package fr.ippon.android.opendata.data.distance;

import static fr.ippon.android.opendata.data.distance.GeolocConstants.EARTH_RADIUS;

/**
 * Calcul d'une distance directe (à vol d'oiseau) en deux points.
 * 
 * <a href="http://en.wikipedia.org/wiki/Haversine_formula">Wikipedia: Haversine
 * formula</a>
 * 
 * @author Damien Raude-Morvan
 */
public class AsTheCrowFliesDistanceCalculator implements DistanceCalculator {

	/**
	 * {@inheritDoc}
	 */
	public double getDistanceBetweenPoints(GpsPoint p1, GpsPoint p2) {
		// http://en.wikipedia.org/wiki/Haversine_formula
		double dLat = Math.toRadians(p2.getLatitude() - p1.getLatitude());
		double dLon = Math.toRadians(p2.getLongitude() - p1.getLongitude());
		double lat1 = Math.toRadians(p1.getLatitude());
		double lat2 = Math.toRadians(p2.getLatitude());

		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2)
				* Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double distance = EARTH_RADIUS * c;

		return distance;
	}
}
