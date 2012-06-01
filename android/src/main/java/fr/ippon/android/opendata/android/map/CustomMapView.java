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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBoxE6;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedOverlay;
import org.osmdroid.views.overlay.Overlay;

import roboguice.RoboGuice;
import roboguice.inject.InjectResource;
import roboguice.inject.RoboInjector;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import fr.ippon.android.opendata.android.MainApplication;
import fr.ippon.android.opendata.android.R;
import fr.ippon.android.opendata.data.distance.BoundingBox;
import fr.ippon.android.opendata.data.traffic.Segment;

/**
 * Configuration de la carte pour utiliser les tuiles MapQuest
 * 
 * @author Damien Raude-Morvan
 */
public class CustomMapView extends MapView implements MapListener {

	private static final String TAG = CustomMapView.class.getName();

	protected TextView ovlerayDescr;
	protected RelativeLayout descrLayout;

	private EquipementsItemizedOverlay equipementsOverlay;
	private SegmentItemizedOvlerlay segmentsOverlay;

	@InjectResource(R.drawable.parkingmarker)
	private Drawable parkingMarker;

	private LoadEquipementOverlayTask loadEquipementOverlayTask;

	private LoadSegmentsOverlayTask loadSegmentsOverlayTask;

	public CustomMapView(Context context, AttributeSet attrs) {
		super(context, attrs);

		RoboInjector injector = RoboGuice.getInjector(context);
		injector.injectMembersWithoutViews(this);

		setTileSource(TileSourceFactory.MAPNIK);
		setBuiltInZoomControls(true);
		setMultiTouchControls(true);
		setMapListener(this);

		this.equipementsOverlay = new EquipementsItemizedOverlay(context,
				parkingMarker, this);

		// On ajoute toujours l'overlay avec les équipements
		addOverlay(this.equipementsOverlay);

		// création de l'overlay de la circulation
		this.segmentsOverlay = new SegmentItemizedOvlerlay();
		SegmentsAsyncTask segmentLoader = new SegmentsAsyncTask(context, this);
		MainApplication.executor.execute(segmentLoader.future());
	}

	/*
	 * Méthode appelée par SegmentsAsyncTask pour construire les overlay à
	 * partir de la liste des segments.
	 */
	public void buildSegmentsOverlays(List<Segment> segments) {
		segmentsOverlay.buildSegmentsOverlays(segments, getContext());
		setTraffic(MainApplication.isDefaultPrefShowTraffic());
	}

	/*
	 * Méthode utilisée pour affichée ou non le traffic selon les préférences.
	 * Le listener est disposé dans MapFragment.
	 */
	public void setTraffic(boolean hasTraffic) {
		if (hasTraffic) {
			addAllOverlay(segmentsOverlay.getOverlay());
		} else {
			getOverlays().removeAll(segmentsOverlay.getOverlay());
			this.postInvalidate();
		}
	}

    /**
     * Méthode utilisée pour rafraichir les overlays de la circulation
     * @param paths
     */
	public void addAllOverlay(final List<SegmentsOverlay> paths) {
		getOverlays().removeAll(paths);
		getOverlays().addAll(paths);
		ensureOverlayOrder();
		this.postInvalidate();
	}

	/**
	 * Méthode utilisée afin de garantir que les overlays des parkings ne soient
	 * pas dessiné en dessous de ceux de la circulation.
	 */
	protected void ensureOverlayOrder() {
		Collections.sort(getOverlays(), new OverlayComparator());
	}

	/**
	 * Calcul de la bounding box à partir de la vue actuelle.
	 */
	protected BoundingBox computeBBox() {
		BoundingBoxE6 bbox = getBoundingBox();
		double lonWest = bbox.getLonWestE6() / 1E6;
		double latNorth = bbox.getLatNorthE6() / 1E6;
		double lonEast = bbox.getLonEastE6() / 1E6;
		double latSouth = bbox.getLatSouthE6() / 1E6;
		return new BoundingBox(latNorth, lonWest, lonEast, latSouth);
	}

	/**
	 * Ajout d'une nouvelle couche de données si elle n'existe pas
	 */
	protected void addOverlay(ItemizedOverlay<?> overlay) {
		if (!getOverlays().contains(overlay)) {
			getOverlays().add(overlay);
		}
		ensureOverlayOrder();
	}

	/**
	 * Suppression d'une couche de données
	 */
	protected void removeOverlay(ItemizedOverlay<?> overlay) {
		if (getOverlays().contains(overlay)) {
			getOverlays().remove(overlay);
		}
	}

	/**
	 * Création des taches pour affectuer le rafraichissement de la carte
	 */
	protected void requestBackgroundRefresh() {
		Log.d(TAG, "requestBackgroundRefresh");
		BoundingBox bbox = computeBBox();
		if (this.equipementsOverlay != null) {
			try {
				if (loadEquipementOverlayTask != null) {
					loadEquipementOverlayTask.cancel(true);
				}
				loadEquipementOverlayTask = new LoadEquipementOverlayTask(
						getContext(), this.equipementsOverlay, bbox);
				loadEquipementOverlayTask.execute();
			} catch (RejectedExecutionException e) {
			}
		}

		requestRefreshTraffic();
	}

	protected void requestRefreshTraffic() {
		if (this.segmentsOverlay != null) {
			try {
				if (loadSegmentsOverlayTask != null) {
					loadSegmentsOverlayTask.cancel(true);
				}
				loadSegmentsOverlayTask = new LoadSegmentsOverlayTask(
						getContext(), this, this.segmentsOverlay);
				loadSegmentsOverlayTask.execute();
			} catch (RejectedExecutionException e) {
			}
		}
	}

	/**
	 * Annule les demandes de refresh en cours via une suppression de la file
	 * d'attente.
	 */
	protected void disableBackgroundRefresh() {
		Log.d(TAG, "disableBackgroundRefresh");
		if (this.equipementsOverlay != null) {
			this.equipementsOverlay.stopExecutor();
		}
		if (this.segmentsOverlay != null)
			this.segmentsOverlay.stopExecutor();
	}

	/**
	 * Active la file d'attente
	 */
	protected void enableBackgroundRefresh() {
		Log.d(TAG, "enableBackgroundRefresh");
		if (this.equipementsOverlay != null) {
			this.equipementsOverlay.startExecutor();
		}
		if (this.segmentsOverlay != null)
			this.segmentsOverlay.startExecutor();
	}

	public boolean onScroll(ScrollEvent scrollEvent) {
		requestBackgroundRefresh();
		return false;
	}

	public boolean onZoom(ZoomEvent zoomEvent) {
		requestBackgroundRefresh();
		return false;
	}

	public void setDescriptionItems(TextView ovlerayDescr,
			RelativeLayout descrLayout) {
		this.ovlerayDescr = ovlerayDescr;
		this.descrLayout = descrLayout;
	}

	public void updateOverlayDescriptionWith(String text) {
		this.ovlerayDescr.setText(Html.fromHtml(text));
		this.descrLayout.setVisibility(View.VISIBLE);
	}

	private class OverlayComparator implements Comparator<Overlay> {
		@Override
		public int compare(Overlay overlay, Overlay overlay1) {
			if (overlay instanceof EquipementsItemizedOverlay
					&& overlay1 instanceof EquipementsItemizedOverlay) {
				return 0;
			} else if (overlay instanceof EquipementsItemizedOverlay
					&& !(overlay1 instanceof EquipementsItemizedOverlay)) {
				return 1;
			} else {
				return -1;
			}
		}
	}
}
