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
package fr.ybo.opendata.nantes;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;

import fr.ippon.android.opendata.data.ParkingDataModule;
import fr.ippon.android.opendata.data.ParkingDataModuleTest;
import fr.ybo.opendata.nantes.modele.Itineraire;
import fr.ybo.opendata.nantes.modele.Parking;
import fr.ybo.opendata.nantes.modele.StatutParking;

public class SimpleOpenDataApiTest {

    private OpenDataApi openDataApi;
    private SimpleDateFormat formatDate;

    @Before
    public void setup() {
    	Guice.createInjector(new ParkingDataModule(), new ParkingDataModuleTest());	
    	formatDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    }

    /**
     * Test de la méthode {@link OpenDataApi#getParkings()}.
     */
    @Test
    public void testGetParkings() throws Exception {
    	String stub = "data-test-20111213.xml";
    	this.openDataApi = new SampleOpenData(stub);
    	
        List<Parking> parkings = openDataApi.getParkings();
        assertEquals(23, parkings.size());
        Parking parking1 = parkings.get(0);
        assertEquals("2", parking1.getIdentifiant());
        assertEquals("DECRE-BOUFFAY", parking1.getNom());
        assertEquals(StatutParking.OUVERT, parking1.getStatut());
        assertEquals(0, parking1.getPriorite());
        assertEquals(247, parking1.getDisponibles());
        assertEquals(2, parking1.getSeuilComplet());
        assertEquals(552, parking1.getPlacesTotales());
        assertEquals(formatDate.parse("13/12/2011 12:23:40"), parking1.getLastUpdate());
    }
    
    @Test
    public void testGetTempsParcours() throws Exception {
    	//Given 
    	String stub = "temps-parcours.xml";
    	this.openDataApi = new SampleOpenData(stub);
    	
    	//when
    	List<Itineraire> itinieraires = openDataApi.getTempsParcours();
    	
    	//then
    	assertTrue(itinieraires.size() > 0);
    	Itineraire it = itinieraires.get(0);
    	
    	assertEquals("011",it.getIdentifiant());
    	assertEquals(6, it.getTemps());
    	assertEquals(formatDate.parse("21/01/2012 23:18:03"), it.getLastUpdate());
    	assertFalse(it.isValide());
    }
}
