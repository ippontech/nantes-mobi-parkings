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
