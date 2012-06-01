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
