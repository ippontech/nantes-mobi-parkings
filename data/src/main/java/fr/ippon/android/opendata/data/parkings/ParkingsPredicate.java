package fr.ippon.android.opendata.data.parkings;

import com.google.common.base.Predicate;

import fr.ybo.opendata.nantes.modele.Categorie;
import fr.ybo.opendata.nantes.modele.Equipement;
import fr.ybo.opendata.nantes.modele.Theme;

/**
 * Permet de filtrer une liste d'équipements par rapport
 * à leur type et leurs catégories pour n'obtenir que les
 * parkings.
 * 
 * @author Damien Raude-Morvan
 */
public class ParkingsPredicate implements Predicate<Equipement> {

	/**
	 * {@inheritDoc}
	 */
	public boolean apply(final Equipement eq) {
		return eq != null
				&& (eq.getTheme() == Theme.DEPLACEMENT)
				&& (eq.getCategorie() == Categorie.PARKING
					|| eq.getCategorie() == Categorie.PARKING_ENCLOS
					|| eq.getCategorie() == Categorie.PARKING_RELAIS);
	}
}