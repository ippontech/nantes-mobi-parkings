package fr.ippon.android.opendata.data.trafficfluency.modele;

/**
 * Created with IntelliJ IDEA.
 * User: nicolasguillot
 * Date: 05/05/12
 * Time: 17:14
 * To change this template use File | Settings | File Templates.
 */
public enum DataValueType {
    TrafficStatusValue("TrafficStatusValue");

    private String tagName;

    private DataValueType(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }
}
