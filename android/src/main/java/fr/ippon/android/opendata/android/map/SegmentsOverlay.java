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
package fr.ippon.android.opendata.android.map;

import org.osmdroid.views.overlay.PathOverlay;

import android.content.Context;
import android.graphics.Color;
import fr.ippon.android.opendata.data.traffic.IE6GeoPoint;
import fr.ippon.android.opendata.data.traffic.Segment;

/**
 * User: nicolasguillot
 * Date: 06/05/12
 * Time: 19:16
 */
public class SegmentsOverlay extends PathOverlay {
    private String segmentId;

    public SegmentsOverlay(Context ctx, Segment seg) {
        super(Color.GRAY, ctx);
        getPaint().setAntiAlias(true);

        segmentId = seg.getName();
        for (IE6GeoPoint c : seg.getCoords()) {
            this.addPoint(c.getLat(), c.getLgt());
        }
    }

    public String getSegmentId() {
        return segmentId;
    }

    public void setSegmentId(String segmentId) {
        this.segmentId = segmentId;
    }
}
