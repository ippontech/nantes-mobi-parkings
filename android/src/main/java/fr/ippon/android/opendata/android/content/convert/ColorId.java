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
package fr.ippon.android.opendata.android.content.convert;

import java.util.List;
import java.util.Arrays;
import android.graphics.Color;
import fr.ippon.android.opendata.data.trafficfluency.modele.TrafficStatus;

/**
 * User: nicolasguillot
 * Date: 06/05/12
 * Time: 00:42
 */
public enum ColorId {
    //Green
    FREE_FlOW(Color.rgb(3,88,16), TrafficStatus.FREE_FlOW, 3),
    //yellow
    MEDIUM_FLOW(Color.rgb(236,236,10), null, 4),
    //red
    HEAVY(Color.rgb(255,65,0), TrafficStatus.HEAVY, 5),
    //orange
    CONGESTED(Color.rgb(247, 3,3), TrafficStatus.CONGESTED, 6),
    //gray
    IMPOSSIBLE(Color.rgb(116,116,116), TrafficStatus.IMPOSSIBLE),
    UNKNOWN(Color.rgb(116,116,116), TrafficStatus.UNKNOWN, 0, 1, 2);

    private int color;
    private TrafficStatus status;
    private List<Integer> colorIndices;

    ColorId(int c, TrafficStatus sts, Integer... colorIndices) {
        this.color = c;
        this.status = sts;
        this.colorIndices = Arrays.asList(colorIndices);
    }

    public static int getColorIdFromTrafficStatus(TrafficStatus st) {
        if(st.equals(FREE_FlOW.status))
            return FREE_FlOW.colorIndices.get(0);
        if(st.equals(MEDIUM_FLOW.status))
            return MEDIUM_FLOW.colorIndices.get(0);
        if(st.equals(HEAVY.status))
            return HEAVY.colorIndices.get(0);
        if(st.equals(CONGESTED.status))
            return CONGESTED.colorIndices.get(0);

        return Color.GRAY;
    }

    //city center color id
    public static int getColorFromColorIndice(int colorIndice) {
        if(FREE_FlOW.colorIndices.contains(colorIndice))
            return FREE_FlOW.color;
        if(MEDIUM_FLOW.colorIndices.contains(colorIndice))
            return MEDIUM_FLOW.color;
        if(HEAVY.colorIndices.contains(colorIndice))
            return HEAVY.color;
        if(CONGESTED.colorIndices.contains(colorIndice))
            return CONGESTED.color;
        return Color.GRAY;
    }

    public int getColor() {
        return color;
    }

    public TrafficStatus getStatus() {
        return status;
    }

    public List<Integer> getColorIndices() {
        return colorIndices;
    }
}
