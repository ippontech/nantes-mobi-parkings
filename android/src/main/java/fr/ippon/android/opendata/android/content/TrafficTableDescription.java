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
/**
 *
 */
package fr.ippon.android.opendata.android.content;

import android.provider.BaseColumns;

/**
 * @author nicolasguillot
 *
 */
public class TrafficTableDescription implements BaseColumns {
    public static final String TRAFFIC_TABLE_NAME = "traffics";


    private TrafficTableDescription() {
    }

    // Identifiants des colonnes
    public static final String PATH_ID = "path_id";
    public static final String IS_VALID = "is_valid";
    public static final String TEMPS = "temps";
    public static final String LAST_UPDATE = "last_update";

    public static final String[] PROJECTION_ALL = new String[] {
            TrafficTableDescription._ID, TrafficTableDescription.PATH_ID,
            TrafficTableDescription.IS_VALID, TrafficTableDescription.TEMPS,
            TrafficTableDescription.LAST_UPDATE };
}
