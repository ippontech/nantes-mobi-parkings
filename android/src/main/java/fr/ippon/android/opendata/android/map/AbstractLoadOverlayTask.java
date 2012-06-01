/**
 * 
 */
package fr.ippon.android.opendata.android.map;

import java.util.List;

import org.osmdroid.views.overlay.OverlayItem;

import roboguice.util.RoboAsyncTask;
import android.content.Context;

/**
 * @author nicolasguillot
 * @author Damien Raude-Morvan
 * 
 * @param <O>
 *            Type de l'objet traité dans la couche (overlay) de la carte
 */
public abstract class AbstractLoadOverlayTask<O extends OverlayItem> extends
		RoboAsyncTask<List<O>> {

	private AbstractItemizedOverlay<O> overlay;

	protected AbstractLoadOverlayTask(Context context,
			AbstractItemizedOverlay<O> overlay) {
		super(context, overlay.getExecutor());
		this.overlay = overlay;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void onSuccess(List<O> list) throws Exception {
		overlay.clear();
		overlay.addAll(list);
		overlay.doPopulate();
		overlay.postInvalidate();

		super.onSuccess(list);
	};

	@Override
	protected void onInterrupted(Exception e) {
	}

}
