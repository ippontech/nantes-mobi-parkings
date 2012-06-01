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

import android.content.Context;
import fr.ippon.android.opendata.android.R;
import fr.ippon.android.opendata.data.traffic.Segment;
import roboguice.util.RoboAsyncTask;

import java.io.InputStream;
import java.util.List;

/**
 * User: nicolasguillot
 * Date: 16/05/12
 * Time: 23:22
 */
public class SegmentsAsyncTask extends RoboAsyncTask<List<Segment>> {

    private CustomMapView mapView;

    public SegmentsAsyncTask(Context context, CustomMapView mapView) {
        super(context);
        this.mapView = mapView;
    }

    protected List<Segment> getSegementsWithLineString() {
        InputStream raw = getContext().getResources()
                .openRawResource(R.raw.segments);
        return Segment.parseSegments(raw);
    }

    @Override
    public List<Segment> call() throws Exception {
        return getSegementsWithLineString();
    }

    @Override
    protected void onSuccess(List<Segment> result) {
        mapView.buildSegmentsOverlays(result);
    }
}
