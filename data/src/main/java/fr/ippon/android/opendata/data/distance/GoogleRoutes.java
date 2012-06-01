package fr.ippon.android.opendata.data.distance;

import fr.ippon.android.opendata.data.distance.GoogleRoutes.Route.Leg;

/**
 * Représentation du flux JSON transmis par Google Maps Direction API.
 * 
 * @author Damien Raude-Morvan
 */
public class GoogleRoutes {

	/**
	 * Liste des trajets possible entre deux points.
	 * Par défaut, une seule route est renvoyée.
	 */
	private Route[] routes;

	/**
	 * Tableau Json routes-legs.
	 * Représente un trajet possible entre deux points.
	 */
	class Route {
		
		/**
		 * Source des données
		 */
		private String copyrights;
		
		/**
		 * Version textuelle du trajet
		 */
		private String summary;
		
		/**
		 * Liste des étapes du trajet
		 */
		private Leg[] legs;

		/**
		 * Tableau Json routes-legs Réprésente en étape d'un trajet
		 */
		class Leg {

			/**
			 * Distance entre les deux points de l'étape
			 */
			private Distance distance;
			
			/**
			 * Adresse de départ
			 */
			private String start_address;
			
			/**
			 * Adresse de fin
			 */
			private String end_address;

			/**
			 * Tableau Json routes-legs-distance
			 */
			class Distance {
				
				/**
				 * Valeur textuelle (en km)
				 */
				private String text;
				
				/**
				 * Valeur en mètres
				 */
				private double value;
			}

			/**
			 * Retourne la distance en metres
			 * 
			 * @return distance en metres
			 */
			public double getDistance() {
				return this.distance.value;
			}

		}

	}

	/**
	 * Retourne l'étape à l'index <code>i</code> pour le premier trajet.
	 * 
	 * @param i index de l'étape.
	 * @return Etape
	 */
	public Leg getLeg(int i) {
		if (this.routes == null || this.routes[0] == null) {
			return null;
		} else {
			return this.routes[0].legs[i];
		}
	}

}
