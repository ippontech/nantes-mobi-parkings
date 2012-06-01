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
