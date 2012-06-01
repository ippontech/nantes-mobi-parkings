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
package fr.ippon.android.opendata.data;

import static com.google.common.collect.Collections2.filter;
import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;

import fr.ippon.android.opendata.data.parkings.ParkingsPredicate;
import fr.ybo.opendata.nantes.modele.Equipement;
import fr.ybo.opendata.nantes.util.EquipementManager;

/**
 * Tests les différentes méthodes de traitement de filtres sur les équipements.
 * 
 * @author Damien Raude-Morvan
 */
public class EquipementParkingTest {

	@Inject
	private EquipementManager equipementManager;

	@Before
	public void setup() {
		Guice.createInjector().injectMembers(this);
	}

	/**
	 * Tests du {@link ParkingsPredicate}
	 */
	@Test
	public void testParkingsPredicateFilter() {
		Map<Integer, Equipement> map = equipementManager.getMapEquipements();

		Collection<Equipement> parkings = filter(map.values(),
				new ParkingsPredicate());
		assertEquals(62, parkings.size());
		for (Equipement eq : parkings) {
			System.out.println(eq.getIdObj() + ":\t" + eq.getNom());
		}
	}

}
