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
package fr.ippon.android.opendata.android.map;

import static com.google.common.collect.Collections2.filter;

import java.util.Collection;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.OverlayItem;

import com.google.common.base.Predicate;

import fr.ippon.android.opendata.android.service.EquipementManagerAndroid;
import fr.ippon.android.opendata.data.parkings.ParkingEntity;
import fr.ippon.android.opendata.data.parkings.ParkingEquipement;
import fr.ippon.android.opendata.data.parkings.ParkingUtils;

public class EquipementOverlayItem extends OverlayItem {
	
	//on ne peut pas injecter cette dépendance ici
	private EquipementManagerAndroid equipementManager;
	
	protected ParkingEntity parking; 
	
	protected String description;

	public EquipementOverlayItem(final EquipementManagerAndroid equipementManager, final GeoPoint point, final String label, final ParkingEntity park) {
		super(label, null, point);
		this.parking = park;
		this.equipementManager = equipementManager;
		this.description = ParkingUtils.getParkingDescription(park, getEquipement(park.getIdObj()));
	}

	/**
	 * Creation a partir d'un parking
	 */
	public static EquipementOverlayItem create(EquipementManagerAndroid equipementManager, ParkingEntity park) {
		int latitude = (int) (park.getLatitude() * 1E6);
		int longitude = (int) (park.getLongitude() * 1E6);
		GeoPoint point = new GeoPoint(latitude, longitude);
		return new EquipementOverlayItem(equipementManager, point, park.getNom(), park);
	}
	
	protected ParkingEquipement getEquipement(final int parkingId) {
		Collection<ParkingEquipement> eqs = equipementManager.getParkings();
		Collection<ParkingEquipement> filteredEqs = filter(eqs, new Predicate<ParkingEquipement>() {
			public boolean apply(ParkingEquipement input) {
				return input.getIdObj() == parkingId;
			}
		});
		
		return filteredEqs.isEmpty() ? null : filteredEqs.iterator().next();
	}
	/**
	 * @return the parkId
	 */
	public int getParkId() {
		return parking.getIdObj();
	}

	/**
	 * @return the description
	 */
	protected String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	protected void setDescription(String description) {
		this.description = description;
	}
}
