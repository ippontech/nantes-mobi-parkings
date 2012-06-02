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
package fr.ippon.android.opendata.data.trafficfluency;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fr.ippon.android.opendata.data.trafficfluency.modele.FreewaySegmentFluency;
import fr.ippon.android.opendata.data.trafficfluency.modele.TrafficStatus;
import fr.ybo.opendata.nantes.exceptions.ApiReseauException;

/**
 * User: nicolasguillot
 * Date: 05/05/12
 * Time: 18:03
 */
public class BisonFuteApiTest {
    protected BisonFuteApi api;

    @Before
    public void setup() {
        api = new BisonFuteApi();
    }

    @Test
    public void testGetBisonFuteTrafficStatus() throws ApiReseauException {
        //given
        api.setConnecteur(new FileConnector("/TraficStatus.xml"));

        //when
        List<FreewaySegmentFluency> res = api.getBisonFuteTrafficStatus();

        //then
        assertNotNull(res);
        assertEquals(44, res.size());
        assertTrue(res.get(0) instanceof FreewaySegmentFluency);
        assertNotNull(((FreewaySegmentFluency) res.get(0)).getTime());
        assertEquals(TrafficStatus.FREE_FlOW, ((FreewaySegmentFluency) res.get(0)).getTrafficStatus());
        assertEquals("MWL44.31", ((FreewaySegmentFluency) res.get(0)).getLocationId());
    }

}
