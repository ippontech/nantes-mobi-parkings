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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class BoundingBoxCalculatorTest {

	@Test
	public void testGetBoundingBox() {
		BoundingBoxCalculator cal = new BoundingBoxCalculator();
		GpsPoint center = new GpsPoint(47.21806, -1.55278);
		
		// 3km
		double bboxsize = 3 * 1000;
		// Tolérance de 1.5/1000
		double tolerance = 1.5d * bboxsize / 1000;
		
		BoundingBox bbox = cal.getBoundingBox(center, bboxsize);
		assertNotNull(bbox);

		AsTheCrowFliesDistanceCalculator distanceCalc = new AsTheCrowFliesDistanceCalculator();
		GpsPoint bottomright = new GpsPoint(bbox.getMin().getLatitude(), bbox
				.getMax().getLongitude());
		GpsPoint topright = new GpsPoint(bbox.getMax().getLatitude(), bbox
				.getMax().getLongitude());
		GpsPoint bottomleft = new GpsPoint(bbox.getMin().getLatitude(), bbox
				.getMin().getLongitude());

		double distanceLat = distanceCalc.getDistanceBetweenPoints(bottomright,
				topright);
		assertEquals(bboxsize * 2, distanceLat, tolerance);

		double distanceLong = distanceCalc.getDistanceBetweenPoints(
				bottomright, bottomleft);
		assertEquals(bboxsize * 2, distanceLong, tolerance);
	}

}
