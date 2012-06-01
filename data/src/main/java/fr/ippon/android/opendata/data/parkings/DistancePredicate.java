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