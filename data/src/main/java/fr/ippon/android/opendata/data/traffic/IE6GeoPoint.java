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

/**
 * Created with IntelliJ IDEA.
 * User: nicolasguillot
 * Date: 02/05/12
 * Time: 22:39
 * To change this template use File | Settings | File Templates.
 */
public class IE6GeoPoint {
    protected int lat, lgt;

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLgt() {
        return lgt;
    }

    public void setLgt(int lgt) {
        this.lgt = lgt;
    }
}
