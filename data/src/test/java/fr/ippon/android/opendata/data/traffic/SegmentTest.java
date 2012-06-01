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
