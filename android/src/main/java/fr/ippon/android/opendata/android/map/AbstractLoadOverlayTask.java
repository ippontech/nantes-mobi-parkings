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
