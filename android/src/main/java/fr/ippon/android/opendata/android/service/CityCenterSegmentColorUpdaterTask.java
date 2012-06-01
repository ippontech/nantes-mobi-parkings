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
package fr.ippon.android.opendata.android.service;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import fr.ippon.android.opendata.android.content.Dao;
import fr.ippon.android.opendata.android.content.SegmentColorContentProvider;
import fr.ippon.android.opendata.android.content.SegmentColorTableDescription;
import fr.ippon.android.opendata.android.content.SegmentDao;
import fr.ippon.android.opendata.android.content.convert.ContentValueConverter;
import fr.ippon.android.opendata.android.content.convert.SegmentContentConverter;
import fr.ippon.android.opendata.data.traffic.Segment;
import fr.ybo.opendata.nantes.OpenDataApi;
import fr.ybo.opendata.nantes.exceptions.ApiReseauException;
import fr.ybo.opendata.nantes.modele.SegmentFluency;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.inject.Inject;

/**
 * User: nicolasguillot
 * Date: 15/05/12
 * Time: 21:28
 */
public class CityCenterSegmentColorUpdaterTask extends
        AbstractUpdaterTask<Segment, Segment, String> {

    private static final String TAG = CityCenterSegmentColorUpdaterTask.class
            .getName();

    @Inject
    private OpenDataApi api;

    @Inject
    private SegmentDao dao;

    protected CityCenterSegmentColorUpdaterTask(Context context) {
        // super == RoboGuice effectue l'injection
        super(context, TAG);
    }

    @Override
    protected Uri getContentUri() {
        return SegmentColorTableDescription.CONTENT_URI;
    }

    @Override
    protected String getAuthority() {
        return SegmentColorContentProvider.AUTHORITY;
    }

    protected List<Segment> convertSegmentsFluencies(List<SegmentFluency> segs) {
        List<Segment> segments = new ArrayList<Segment>();
        Date now = new Date();//TODO
        if(segs != null) {
            Segment seg;
            for(SegmentFluency sf: segs) {
                seg = new Segment();
                seg.setColorId(sf.getColorId());
                seg.setName(Integer.toString(sf.getId()));
                seg.setLastUpdate(now);
                segments.add(seg);
            }
        }
        return segments;
    }
    @Override
    protected TaskData<Segment> getData() {
        try {
            List<SegmentFluency> segmentsFluencies = api.getCityCenterSegmentsFluencies();
            return TaskData.ok(convertSegmentsFluencies(segmentsFluencies));
        } catch (ApiReseauException e) {
            Log.e(TAG, e.getMessage());
            return TaskData.error(e.getMessage());
        }
    }

    @Override
    protected Dao<Segment, String> getDao() {
        return dao;
    }

    @Override
    protected ContentValueConverter<Segment> createConverter() {
        return new SegmentContentConverter();
    }

    @Override
    protected String getId(Segment obj) {
        return obj.getName();
    }
}
