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
package fr.ippon.android.opendata.android.content;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * User: nicolasguillot
 * Date: 05/05/12
 * Time: 19:15
 */
public class SegmentColorTableDescription implements BaseColumns {
    public static final String SEGMENTS_COLOR_TABLE_NAME = "segments_color";

    public static final Uri CONTENT_URI = Uri.parse("content://"
            + SegmentColorContentProvider.AUTHORITY + "/"+SEGMENTS_COLOR_TABLE_NAME);

    private SegmentColorTableDescription() {
    }

    // Identifiants des colonnes
    public static final String SEGMENT_ID = "id";
    public static final String COLOR_ID = "color_id";
    public static final String LAST_UPDATE_SEG = "last_update";

    public static final String[] PROJECTION_ALL = new String[] {
            _ID,
            SEGMENT_ID,
            COLOR_ID,
            LAST_UPDATE_SEG
    };
}
