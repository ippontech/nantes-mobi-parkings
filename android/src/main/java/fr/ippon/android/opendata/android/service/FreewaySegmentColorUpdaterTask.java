package fr.ippon.android.opendata.android.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import android.util.Log;
import android.net.Uri;
import android.content.Context;
import fr.ippon.android.opendata.android.content.Dao;
import fr.ippon.android.opendata.android.content.SegmentColorContentProvider;
import fr.ippon.android.opendata.android.content.SegmentColorTableDescription;
import fr.ippon.android.opendata.android.content.SegmentDao;
import fr.ippon.android.opendata.android.content.convert.ColorId;
import fr.ippon.android.opendata.android.content.convert.ContentValueConverter;
import fr.ippon.android.opendata.android.content.convert.SegmentContentConverter;
import fr.ippon.android.opendata.data.traffic.Segment;
import fr.ippon.android.opendata.data.trafficfluency.BisonFuteApi;
import fr.ippon.android.opendata.data.trafficfluency.modele.FreewaySegmentFluency;
import fr.ybo.opendata.nantes.exceptions.ApiReseauException;

/**
 * User: nicolasguillot Date: 06/05/12 Time: 15:20
 */
public class FreewaySegmentColorUpdaterTask extends
		AbstractUpdaterTask<Segment, Segment, String> {

	private static final String TAG = FreewaySegmentColorUpdaterTask.class
			.getName();

	@Inject
	private BisonFuteApi api;

	@Inject
	private SegmentDao dao;

	protected FreewaySegmentColorUpdaterTask(Context context) {
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

	protected List<Segment> convertFreewaySegmentFluency(
			Collection<FreewaySegmentFluency> freewaySegment) {
		List<Segment> segments = new ArrayList<Segment>();
		Segment s;
		for (FreewaySegmentFluency fs : freewaySegment) {
			s = new Segment();
			s.setColorId(ColorId.getColorIdFromTrafficStatus(fs
					.getTrafficStatus()));
			s.setName(fs.getLocationId());
			s.setLastUpdate(fs.getTime());

			segments.add(s);
		}

		return segments;
	}

	@Override
	protected TaskData<Segment> getData() {
		try {
			return TaskData.ok(convertFreewaySegmentFluency(api
					.getBisonFuteTrafficStatus()));
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
