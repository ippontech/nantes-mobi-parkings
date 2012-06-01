package fr.ippon.android.opendata.data;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class ParkingDataModuleTest extends AbstractModule {

	@Override
	protected void configure() {
		bind(String.class).annotatedWith(Names.named("opendatanantes_key"))
				.toInstance("39W9VSNCSASEOGV");
	}
}
