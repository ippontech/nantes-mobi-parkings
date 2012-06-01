package fr.ippon.android.opendata.data.distance;

/**
 * Constantes utilisées pour les calculs liés à la géoloc.
 * 
 * @author Damien Raude-Morvan
 */
public class GeolocConstants {

	/**
	 * Earth is not really spherical but this radius should be fine for our
	 * computation :)
	 * 
	 * <a href="http://en.wikipedia.org/wiki/Earth_radius">Wikipedia: Earth
	 * radius</a>
	 * 
	 * This value is in meters.
	 */
	public static long EARTH_RADIUS = 6371009;

	/**
	 * -PI/2
	 */
	public static final double MIN_LATITUDE = Math.toRadians(-90d);

	/**
	 * PI/2
	 */
	public static final double MAX_LATITUDE = Math.toRadians(+90d);

	/**
	 * -PI
	 */
	public static final double MIN_LONGITUDE = Math.toRadians(-180d);

	/**
	 * PI
	 */
	public static final double MAX_LONGITUDE = Math.toRadians(+180d);

}
