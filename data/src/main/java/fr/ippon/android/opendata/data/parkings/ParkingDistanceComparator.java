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

import java.util.Comparator;

import fr.ippon.android.opendata.data.distance.DistanceCalculator;
import fr.ippon.android.opendata.data.distance.GpsPoint;
import fr.ybo.opendata.nantes.modele.Parking;

/**
 * Permet d'ordonner deux parkings par rapport à leur distance vis-à-vis du point <code>center</code>.
 */
public class ParkingDistanceComparator implements Comparator<Parking> {

    /**
     * Instance du système de calcul des distances
     */
    private DistanceCalculator calculator;

    /**
     * Point à partir duquel toutes les distances sont calculées
     */
    private GpsPoint center;

    public ParkingDistanceComparator(DistanceCalculator calculator, GpsPoint center) {
        this.center = center;
        this.calculator = calculator;
    }

    /**
     * Comparaison de deux parkings vis-à-vis du point <code>center</code>.
     *
     * @param one Premier parking
     * @param other Deuxième parking
     * @return la valeur 0 si one est égale à other
     * -1 si one est plus proche que other
     * +1 si one est plus loin que other.
     */
    public int compare(Parking one, Parking other) {
        if (one == other) { return -1; }
        if (other == null || one.getClass() != other.getClass()) { return -1; }

        double distanceOne = calculator.getDistanceBetweenPoints(center, new GpsPoint(one));
        double distanceOther = calculator.getDistanceBetweenPoints(center, new GpsPoint(other));

        return Double.compare(distanceOne, distanceOther);
    }
}
