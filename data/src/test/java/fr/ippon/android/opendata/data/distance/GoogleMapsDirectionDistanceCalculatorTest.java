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

import java.io.IOException;
import java.io.InputStream;

import org.junit.Ignore;
import org.junit.Test;

/**
 * 
 * @author Damien Raude-Morvan
 */
public class GoogleMapsDirectionDistanceCalculatorTest {

	@Test
	public void testGetOfflineGoogleRoutes() throws IOException {

		InputStream is = this.getClass().getResourceAsStream(
				"/google_routes.json");
		GoogleMapsDirectionDistanceCalculator dist = new GoogleMapsDirectionDistanceCalculator();

		GoogleRoutes gr = dist
				.getGoogleRoutes(GoogleMapsDirectionDistanceCalculator
						.inputStreamAsString(is));
		assertNotNull(gr);
	}

	@Test
	@Ignore("Use network for testing")
	public void testGetOnlineGoogleRoutes() throws IOException {
		// Nantes
		GpsPoint p1 = new GpsPoint(47.2208, -1.5584);
		// Combourg
		GpsPoint p2 = new GpsPoint(48.4116, -1.7525);
		GoogleMapsDirectionDistanceCalculator dist = new GoogleMapsDirectionDistanceCalculator();
		double distance = dist.getDistanceBetweenPoints(p1, p2);
		
		// 146km
		double expectedDistance = 146000;
		// 100 metres
		double tolerance = 100.0d;
		
		assertEquals(expectedDistance, distance, tolerance);
	}
}
