package fr.ippon.android.opendata.data.distance;

import static fr.ippon.android.opendata.data.distance.GeolocConstants.MAX_LATITUDE;
import static fr.ippon.android.opendata.data.distance.GeolocConstants.MAX_LONGITUDE;
import static fr.ippon.android.opendata.data.distance.GeolocConstants.MIN_LATITUDE;
import static fr.ippon.android.opendata.data.distance.GeolocConstants.MIN_LONGITUDE;
import fr.ybo.opendata.nantes.modele.Parking;

/**
 * Représentation d'un point GPS au format WGS84 (GPS)
 * 
 * @author Damien Raude-Morvan
 */
public class GpsPoint {

	/**
	 * Latitude au format WGS84 (GPS)
	 */
	private double latitude;

	/**
	 * Longitude au format WGS84 (GPS)
	 */
	private double longitude;

	public GpsPoint(final double latitude, final double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.checkBounds();
	}

	public GpsPoint(final Parking parking) {
		this(parking.getLatitude(), parking.getLongitude());
	}
	
	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(final double longitude) {
		this.longitude = longitude;
		this.checkBounds();
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
		this.checkBounds();
	}

	/**
	 * Vérifie que les données sont valides.
	 */
	private void checkBounds() {
		if (Math.toRadians(this.latitude) < MIN_LATITUDE
				|| Math.toRadians(this.latitude) > MAX_LATITUDE
				|| Math.toRadians(this.longitude) < MIN_LONGITUDE
				|| Math.toRadians(this.longitude) > MAX_LONGITUDE)
			throw new IllegalArgumentException();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof GpsPoint)) {
			return false;
		}
		GpsPoint other = (GpsPoint) obj;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude)) {
			return false;
		}
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		// Format proche de celui utilisé par OpenStreetMap
		// Ajouter http://www.openstreetmap.org/? devant !
		return new StringBuilder().append("GpsPoint[").append("mlat=")
				.append(this.latitude).append("&mlong=").append(this.longitude)
				.append("]").toString();
	}
}
