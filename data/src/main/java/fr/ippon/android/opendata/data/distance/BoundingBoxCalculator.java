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
package fr.ippon.android.opendata.data.distance;

import static fr.ippon.android.opendata.data.distance.GeolocConstants.EARTH_RADIUS;
import static fr.ippon.android.opendata.data.distance.GeolocConstants.MAX_LATITUDE;
import static fr.ippon.android.opendata.data.distance.GeolocConstants.MAX_LONGITUDE;
import static fr.ippon.android.opendata.data.distance.GeolocConstants.MIN_LATITUDE;
import static fr.ippon.android.opendata.data.distance.GeolocConstants.MIN_LONGITUDE;

/**
 * 
 * @author Damien Raude-Morvan
 */
public class BoundingBoxCalculator {

	/**
	 * <p>
	 * Computes the bounding coordinates of all points on the surface of a
	 * sphere that have a great circle distance to the point represented by this
	 * GpsPoint instance that is less or equal to the distance argument.
	 * </p>
	 * <p>
	 * For more information about the formulae used in this method visit <a
	 * href="http://JanMatuschek.de/LatitudeLongitudeBoundingCoordinates">
	 * http://JanMatuschek.de/LatitudeLongitudeBoundingCoordinates</a>.
	 * </p>
	 * 
	 * @param distance
	 *            the distance (in meter) from the point represented by this
	 *            GpsPoint instance.
	 * @return an BoundingBox object such that:
	 *         <ul>
	 *         <li>The latitude of any point within the specified distance is
	 *         greater or equal to the latitude of the first array element and
	 *         smaller or equal to the latitude of the second array element.</li>
	 *         <li>If the longitude of the first array element is smaller or
	 *         equal to the longitude of the second element, then the longitude
	 *         of any point within the specified distance is greater or equal to
	 *         the longitude of the first array element and smaller or equal to
	 *         the longitude of the second array element.</li>
	 *         <li>If the longitude of the first array element is greater than
	 *         the longitude of the second element (this is the case if the
	 *         180th meridian is within the distance), then the longitude of any
	 *         point within the specified distance is greater or equal to the
	 *         longitude of the first array element <strong>or</strong> smaller
	 *         or equal to the longitude of the second array element.</li>
	 *         </ul>
	 */
	public BoundingBox getBoundingBox(GpsPoint center, double distance) {

		// angular distance in radians on a great circle
		double radDist = distance / EARTH_RADIUS;
		double radLat = Math.toRadians(center.getLatitude());
		double radLon = Math.toRadians(center.getLongitude());

		double minLat = radLat - radDist;
		double maxLat = radLat + radDist;

		double minLon, maxLon;
		if (minLat > MIN_LATITUDE && maxLat < MAX_LATITUDE) {
			double deltaLon = Math.asin(Math.sin(radDist) / Math.cos(radLat));
			minLon = radLon - deltaLon;
			if (minLon < MIN_LONGITUDE) {
				minLon += 2d * Math.PI;
			}
			maxLon = radLon + deltaLon;
			if (maxLon > MAX_LONGITUDE) {
				maxLon -= 2d * Math.PI;
			}
		} else {
			// a pole is within the distance
			minLat = Math.max(minLat, MIN_LATITUDE);
			maxLat = Math.min(maxLat, MAX_LATITUDE);
			minLon = MIN_LONGITUDE;
			maxLon = MAX_LONGITUDE;
		}

		GpsPoint min = new GpsPoint(Math.toDegrees(minLat),
				Math.toDegrees(minLon));
		GpsPoint max = new GpsPoint(Math.toDegrees(maxLat),
				Math.toDegrees(maxLon));

		return new BoundingBox(min, max);
	}

}
