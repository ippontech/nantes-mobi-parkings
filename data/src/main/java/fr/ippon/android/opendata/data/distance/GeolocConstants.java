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
