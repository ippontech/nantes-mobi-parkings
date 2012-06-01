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
