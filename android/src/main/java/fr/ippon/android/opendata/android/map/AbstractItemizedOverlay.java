package fr.ippon.android.opendata.android.map;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Permet de gérer le fonctionnement d'un ItemizedOverlay (couche de données
 * pour l'API GMaps) avec comme backend une List<O>.
 * 
 * @author Damien Raude-Morvan
 * 
 * @param <O>
 *            Type de l'objet traité dans la couche (overlay) de la carte
 */
public abstract class AbstractItemizedOverlay<O extends OverlayItem> extends
		ItemizedIconOverlay<O> {

	private static final String TAG = AbstractItemizedOverlay.class.getName();

	/**
	 * Liste qui stocke les items actuellement affichés sur la carte
	 */
	protected List<O> items;

	/**
	 * Contexte courant (pour l'affichage de boite de dialogue).
	 */
	protected Context context;

	/**
	 * Composant carte
	 */
	private CustomMapView mapView;

	/**
	 * Exécuteur de tache.
	 * Doit être initialiser avec {@link #startExecutor()}
	 * et arrêter avec {@link #stopExecutor()}.
	 */
	private ExecutorService executor;

	public AbstractItemizedOverlay(Drawable defaultMarker, Context context,
			List<O> items, OnItemGestureListener<O> listener,
			CustomMapView mapView) {
		super(items, defaultMarker, listener, new DefaultResourceProxyImpl(
				context));
		this.items = items;
		this.context = context;
		this.mapView = mapView;
	}

	/**
	 * Ajout d'un nouvel élément <code>elt</code> et lancement de
	 * {@link #doPopulate()}.
	 */
	public void add(O elt) {
		items.add(elt);
	}

	/**
	 * Ajout de l'ensemble des éléments <code>list</code> et lancement de
	 * {@link #doPopulate()}.
	 */
	public void addAll(Collection<O> list) {
		items.addAll(list);
	}

	/**
	 * Suppression de l'ensemble des éléments et lancement de
	 * {@link #doPopulate()}.
	 */
	public void clear() {
		items.clear();
	}

	/**
	 * Méthode qui permet de demande un recalcul graphique du calque et
	 * ensaillant de ne pas faire plante l'API GMap...
	 */
	protected void doPopulate() {
		populate();
	}

	protected void postInvalidate() {
		//Log.d(TAG, "postInvalidate");
		mapView.postInvalidate();
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

	public List<O> getItems() {
		return this.items;
	}
}
