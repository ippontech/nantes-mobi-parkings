package fr.ippon.android.opendata.android.map;

import android.content.Context;
import fr.ippon.android.opendata.android.R;
import fr.ippon.android.opendata.android.content.SegmentDao;
import fr.ippon.android.opendata.data.traffic.Segment;
import roboguice.util.RoboAsyncTask;

import javax.inject.Inject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
