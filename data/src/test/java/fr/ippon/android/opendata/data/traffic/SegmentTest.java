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
package fr.ippon.android.opendata.data.traffic;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;
import org.junit.Test;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: nicolasguillot
 * Date: 02/05/12
 * Time: 22:51
 * To change this template use File | Settings | File Templates.
 */
public class SegmentTest {

    @Test
    public void parseSegments() {
        //given
        InputStream is = this.getClass().getResourceAsStream(
                "/segments.json");

        //when
        List<Segment> segs = Segment.parseSegments(is);

        //then
        assertNotNull(segs);
        assertEquals(3, segs.size());
        assertEquals(5, segs.get(2).coords.size());
        assertEquals("80", segs.get(2).name);
        assertNull(segs.get(0).getColorId());
    }
}
