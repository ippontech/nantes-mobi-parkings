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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ParkingPagerAdapter extends FragmentPagerAdapter {

	private Context mContext;
	
	public ParkingPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		mContext = context;
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
	        case 0:
	            return new MainListFragment();
	        case 1:
	            return new MapFragment();
	        case 2:
	            return new FavouritesListFragment();
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
	            return mContext.getString(R.string.main_button_list);
	        case 1:
	            return mContext.getString(R.string.main_button_map);
	        case 2:
	            return mContext.getString(R.string.main_button_stars);
		}
		return null;
    }
	

}
