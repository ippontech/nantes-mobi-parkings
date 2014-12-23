/*
* Copyright 2014 Florence Herrou
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
package fr.ippon.android.opendata.android;

import fr.ippon.android.opendata.android.map.MapFragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;

public class ParkingPagerAdapter extends FragmentPagerAdapter {
	
	private static final String TAG = ParkingPagerAdapter.class.getName();

	private Context mContext;

	public ParkingPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		mContext = context;
	}

	@Override
	public Fragment getItem(int position) {
		Log.d(TAG, "position de l'onglet == " + position);
		switch (position) {
	        case 0:
	        	return MainListFragment.instantiate(mContext, MainListFragment.class.getName());
	        case 1:
	        	return MapFragment.instantiate(mContext, MapFragment.class.getName());
	        case 2:
	        	return FavouritesListFragment.instantiate(mContext, FavouritesListFragment.class.getName());
	        	
		}
		return null;
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
	        case 0:
	            return getIconeTexte(R.drawable.ic_menu_recherche, R.string.main_button_list);
	        case 1:
	            return getIconeTexte(R.drawable.ic_menu_carte, R.string.main_button_map);
	        case 2:
	            return getIconeTexte(R.drawable.ic_menu_favoris, R.string.main_button_stars);
		}
		return null;
    }
	
	private CharSequence getIconeTexte(int idDrawable, int idTexte) {
		SpannableStringBuilder sb = new SpannableStringBuilder(" " + mContext.getString(idTexte)); // space added before text for convenience
		
		Drawable myDrawable = mContext.getResources().getDrawable(idDrawable);
	    myDrawable.setBounds(0, 0, myDrawable.getIntrinsicWidth(), myDrawable.getIntrinsicHeight()); 
	    ImageSpan span = new ImageSpan(myDrawable, ImageSpan.ALIGN_BASELINE); 
	    sb.setSpan(span, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
	    
	    return sb;
	}
}
