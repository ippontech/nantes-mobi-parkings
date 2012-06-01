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
 * Représente un rectangle qui permet d'identifier
 * 
 * @author Damien Raude-Morvan
 */
public class BoundingBox {

	/**
	 * Point avec la latitude et la longitude des plus élevées
	 */
	private GpsPoint northWest;

	/**
	 * Point avec la latitude et la longitude les plus faibles
	 */
	private GpsPoint southEast;

	public BoundingBox(final GpsPoint southEast, final GpsPoint northWest) {
		this.southEast = southEast;
		this.northWest = northWest;
	}

	public BoundingBox(final double latNorth, final double lonWest, final double lonEast,
			final double latSouth) {
		this.southEast = new GpsPoint(latSouth, lonEast);
		this.northWest = new GpsPoint(latNorth, lonWest);
	}

	public GpsPoint getMax() {
		return northWest;
	}

	public GpsPoint getMin() {
		return southEast;
	}

	public boolean contains(GpsPoint point) {
		double lonWest = northWest.getLongitude();
		double latNorth = northWest.getLatitude();
		double lonEast = southEast.getLongitude();
		double latSouth = southEast.getLatitude();
		
		if (point.getLatitude() <= latNorth
				&& point.getLatitude() >= latSouth
				&& point.getLongitude() <= lonEast
				&& point.getLongitude() >= lonWest) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		// Format proche de celui utilisé par OpenStreetMap
		// Ajouter http://www.openstreetmap.org/? devant !
		return new StringBuilder()
				.append("BoundingBox[")
				.append("minlon=").append(southEast.getLongitude())
				.append("&minlat=").append(southEast.getLatitude())
				.append("&maxlon=").append(northWest.getLongitude())
				.append("&maxlat=").append(northWest.getLatitude())
				.append("&box=yes")
				.append("]").toString();
	}

}
