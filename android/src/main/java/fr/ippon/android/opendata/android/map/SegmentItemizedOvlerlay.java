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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.Context;
import fr.ippon.android.opendata.android.R;
import fr.ippon.android.opendata.data.traffic.Segment;

/**
 * User: nicolasguillot
 * Date: 11/05/12
 * Time: 21:42
 */
public class SegmentItemizedOvlerlay {

    private List<SegmentsOverlay> overlay;

    /**
     * Exécuteur de tache.
     * Doit être initialiser avec {@link #startExecutor()}
     * et arrêter avec {@link #stopExecutor()}.
     */
    private ExecutorService executor;

    public SegmentItemizedOvlerlay() {
        super();
        this.overlay = new ArrayList<SegmentsOverlay>();
    }

    public void buildSegmentsOverlays(List<Segment> segments, Context ctx) {
        SegmentsOverlay sOverlay;
        for(Segment s: segments) {
            sOverlay = new SegmentsOverlay(ctx, s);
            overlay.add(sOverlay);
        }
    }

    protected List<Segment> getSegementsWithLineString(Context context) {
        InputStream raw = context.getResources()
                .openRawResource(R.raw.segments);
        return Segment.parseSegments(raw);
    }

    public List<SegmentsOverlay> getOverlay() {
        return overlay;
    }

    /**
     * Permet d'obtenir le pool d'execution de tache.
     */
    protected ExecutorService getExecutor() {
        return executor;
    }

    /**
     * Création et démarrage d'un nouveau pool d'execution de tache
     * en mode sérialisée.
     */
    protected void startExecutor() {
        if (executor == null || executor.isShutdown()
                || executor.isTerminated()) {
            executor = Executors.newSingleThreadExecutor();
        }
    }

    /**
     * Arrêt du pool d'exécution de tache.
     */
    public void stopExecutor() {
        getExecutor().shutdownNow();
    }
}
