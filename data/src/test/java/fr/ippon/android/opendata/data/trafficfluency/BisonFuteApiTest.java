package fr.ippon.android.opendata.data.trafficfluency;

import fr.ippon.android.opendata.data.trafficfluency.modele.BasicDataValue;
import fr.ippon.android.opendata.data.trafficfluency.modele.FreewaySegmentFluency;
import fr.ippon.android.opendata.data.trafficfluency.modele.TrafficStatus;
import fr.ybo.opendata.nantes.exceptions.ApiReseauException;


import org.junit.Before;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;


import java.util.List;

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
