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