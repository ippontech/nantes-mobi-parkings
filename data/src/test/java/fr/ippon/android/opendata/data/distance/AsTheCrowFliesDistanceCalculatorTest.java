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

import static junit.framework.Assert.assertEquals;

import org.junit.Test;

/**
 * @author Damien Raude-Morvan
 */
public class AsTheCrowFliesDistanceCalculatorTest {

	@Test
	public void testGetDistanceBetweenPointsSmallDistance() throws Exception {

		// Haluchère
		GpsPoint p1 = new GpsPoint(47.2481, -1.5214);
		// Beaujoire
		GpsPoint p2 = new GpsPoint(47.2565, -1.5280);

		AsTheCrowFliesDistanceCalculator cal = new AsTheCrowFliesDistanceCalculator();
		double distance = cal.getDistanceBetweenPoints(p1, p2);

		// 1km
		double expectedDistance = 1058.5;
		// Tolérance de 10cm
		double tolerance = 0.1d;

		assertEquals(expectedDistance, distance, tolerance);
	}

	@Test
	public void testGetDistanceBetweenPointsBigDistance() throws Exception {

		// Statue of Liberty
		GpsPoint p1 = new GpsPoint(40.6892, -74.0444);
		// Eiffel Tower
		GpsPoint p2 = new GpsPoint(48.8583, 2.2945);

		AsTheCrowFliesDistanceCalculator cal = new AsTheCrowFliesDistanceCalculator();
		double distance = cal.getDistanceBetweenPoints(p1, p2);

		// 5837km
		double expectedDistance = 5837 * 1000;
		// Tolérance de 2%
		double tolerance = 2 * expectedDistance / 100;

		assertEquals(expectedDistance, distance, tolerance);
	}
}
