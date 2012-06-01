package fr.ippon.android.opendata.android.map;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class EquipementsItemizedOverlay extends
		AbstractItemizedOverlay<EquipementOverlayItem> {
	
	private Drawable parkDrawable;
	private static int selectedItemId = Integer.MIN_VALUE;

	public EquipementsItemizedOverlay(Context context, Drawable marker,
			CustomMapView mapView) {
		super(marker, context, new ArrayList<EquipementOverlayItem>(),
				new EquipementOnItemGestureListener(context, mapView), mapView);
		parkDrawable = marker;
	}

	public static void markParkAsSelected(final int parkingObjId) {
		selectedItemId = parkingObjId;
	}

	public static void clearSelectedParking() {
		selectedItemId = Integer.MIN_VALUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected EquipementOverlayItem createItem(int i) {
		EquipementOverlayItem item = items.get(i);
		if (parkDrawable != null) {
			// Creation d'un clone du Drawable (sinon l'ensemble OverlayItem
			// portent la même instance)
			Drawable clone = parkDrawable.getConstantState().newDrawable();
			if(item.getParkId() == selectedItemId) {
				clone.setLevel(1);	
				Log.d("EquipementsItemizedOverlay", "drawable selected on "+item.getTitle());
			} else {
				clone.setLevel(0);
			}
			item.setMarker(clone);
		}
		return item;
	}
	
	private static class EquipementOnItemGestureListener implements
			OnItemGestureListener<EquipementOverlayItem> {

		private Context context;
        private CustomMapView mapView;

		public EquipementOnItemGestureListener(final Context context, final CustomMapView mapView) {
			this.context = context;
            this.mapView = mapView;
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean onItemSingleTapUp(int index, EquipementOverlayItem item) {
            mapView.updateOverlayDescriptionWith(item.getDescription());
            MapFragment.selectedParking = item.parking;
			return true;
		}

		/**
		 * {@inheritDoc}
		 */
		public boolean onItemLongPress(int index, EquipementOverlayItem item) {
			// Nothing on long press
			return false;
		}

	}

}
