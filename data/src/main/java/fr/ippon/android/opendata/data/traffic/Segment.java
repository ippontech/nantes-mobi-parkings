package fr.ippon.android.opendata.data.traffic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

/**
 * User: nicolasguillot
 * Date: 02/05/12
 * Time: 22:46
 */
public class Segment {
    protected String name;
    protected Integer colorId;
    protected Date lastUpdate;
    protected List<IE6GeoPoint> coords = new ArrayList<IE6GeoPoint>();

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getColorId() {
        return colorId;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<IE6GeoPoint> getCoords() {
        return coords;
    }

    public void setCoords(List<IE6GeoPoint> coords) {
        this.coords = coords;
    }

    public static List<Segment> parseSegments(InputStream in) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            Gson gson = new Gson();
            return gson.fromJson(reader, new TypeToken<List<Segment>>() {}.getType());
        } catch (UnsupportedEncodingException e) {
            return new ArrayList<Segment>();
        }
    }
}
