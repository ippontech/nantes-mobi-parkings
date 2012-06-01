package fr.ippon.android.opendata.data.distance;

/**
 * Calcul d'une distance entre deux GpsPoint.
 *
 * @author Damien Raude-Morvan
 */
public interface DistanceCalculator {

    /**
     * Calcul de la distance en mètres entre les points <code>p1</code> et <code>p2</code>.
     *
     * @param p1 GpsPoint numéro 1
     * @param p2 GpsPoint numéro 2
     * @return Distance en mètres entre les deux points
     */
    double getDistanceBetweenPoints(GpsPoint p1, GpsPoint p2);
}
