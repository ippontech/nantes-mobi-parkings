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

import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Freeway segment trafficfluency indicator
 * User: nicolasguillot
 * Date: 05/05/12
 * Time: 15:19
 * To change this template use File | Settings | File Templates.
 */
public class FreewaySegmentFluency implements BasicDataValue, Serializable {
    /**
     * Serial.
     */
    private static final long serialVersionUID = -1L;

    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(FreewaySegmentFluency.class.getSimpleName());

    private Date time;

    private String locationId;

    private TrafficStatus trafficStatus;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public TrafficStatus getTrafficStatus() {
        return trafficStatus;
    }

    public void setTrafficStatus(TrafficStatus trafficStatus) {
        this.trafficStatus = trafficStatus;
    }

    @Override
    public DataValueType getDataValueType() {
        return DataValueType.TrafficStatusValue;
    }
}
