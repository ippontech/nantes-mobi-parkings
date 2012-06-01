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

import android.content.ContentValues;
import fr.ippon.android.opendata.android.content.SegmentDao;
import fr.ippon.android.opendata.data.trafficfluency.modele.FreewaySegmentFluency;

import java.text.SimpleDateFormat;

import static fr.ippon.android.opendata.android.content.SegmentColorTableDescription.LAST_UPDATE_SEG;
import static fr.ippon.android.opendata.android.content.SegmentColorTableDescription.SEGMENT_ID;
import static fr.ippon.android.opendata.android.content.SegmentColorTableDescription.COLOR_ID;

/**
 * User: nicolasguillot
 * Date: 06/05/12
 * Time: 00:37
 */
public class FreewaySegmentFluencyConverter implements ContentValueConverter<FreewaySegmentFluency> {
    @Override
    public ContentValues getValuesFrom(FreewaySegmentFluency obj) {
        SimpleDateFormat sdf = new SimpleDateFormat(SegmentDao.LAST_UPDATE_FORMAT);
        ContentValues cv = new ContentValues();
        cv.put(SEGMENT_ID, obj.getLocationId());
        cv.put(COLOR_ID, ColorId.getColorIdFromTrafficStatus(obj.getTrafficStatus()));
        cv.put(LAST_UPDATE_SEG, sdf.format(obj.getTime()));

        return cv;
    }
}
