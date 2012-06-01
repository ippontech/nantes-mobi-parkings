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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

import fr.ippon.android.opendata.data.ParkingDataModule;
import fr.ippon.android.opendata.data.ParkingDataModuleTest;
import fr.ippon.android.opendata.data.parkings.ParkingDistanceComparator;
import fr.ybo.opendata.nantes.OpenDataApi;
import fr.ybo.opendata.nantes.SampleOpenData;
import fr.ybo.opendata.nantes.modele.Parking;

/**
 * @author Damien Raude-Morvan
 */
public class ParkingDistanceComparatorTest {

	private OpenDataApi openDataApi;

	private DistanceCalculator calculator;

	@Before
	public void setup() {
		Injector injector = Guice.createInjector(new ParkingDataModule(), new ParkingDataModuleTest());
		this.calculator = injector.getInstance(DistanceCalculator.class);
		
		String stub = "data-test-20111213.xml";
		this.openDataApi = new SampleOpenData(stub);
	}

	@Test
	public void testCompare() throws Exception {

		List<Parking> parkings = openDataApi.getParkings();
		GpsPoint center = new GpsPoint(47.2139, -1.5552);

		List<Parking> orderedParkings = new ArrayList<Parking>();
		orderedParkings.addAll(parkings);

		Collections.sort(orderedParkings, new ParkingDistanceComparator(
				calculator, center));

		assertEquals(23, parkings.size());
		assertEquals(23, orderedParkings.size());

	}
}
