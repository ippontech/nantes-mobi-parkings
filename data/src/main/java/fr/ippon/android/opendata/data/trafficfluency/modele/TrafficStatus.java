package fr.ippon.android.opendata.data.trafficfluency.modele;

/**
 * Created with IntelliJ IDEA.
 * User: nicolasguillot
 * Date: 05/05/12
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
 */
public enum TrafficStatus {
    FREE_FlOW("freeFlow"),
    HEAVY("heavy"),
    CONGESTED("congested"),
    IMPOSSIBLE("impossible"),
    UNKNOWN("unknown");

    private String name;

    TrafficStatus(String tagName) {
        this.name = tagName;
    }

    public String getName() {
        return name;
    }

    public static TrafficStatus getTrafficStatusFrom(String status) {
        if(FREE_FlOW.getName().equalsIgnoreCase(status))
            return FREE_FlOW;
        if(HEAVY.getName().equalsIgnoreCase(status))
            return HEAVY;
        if(CONGESTED.getName().equalsIgnoreCase(status))
            return CONGESTED;
        if(IMPOSSIBLE.getName().equalsIgnoreCase(status))
            return IMPOSSIBLE;

        return UNKNOWN;
    }
}
