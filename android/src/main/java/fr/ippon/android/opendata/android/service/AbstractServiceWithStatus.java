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

import static fr.ippon.android.opendata.android.service.ServiceStatus.SERVICE_FINISHED;
import static fr.ippon.android.opendata.android.service.ServiceStatus.SERVICE_RUNNING;
import static fr.ippon.android.opendata.android.service.ServiceStatus.SERVICE_STARTED;
import static fr.ippon.android.opendata.android.service.ServiceStatus.SERVICE_STOPPED;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import roboguice.service.RoboIntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import fr.ippon.android.opendata.android.MainApplication;

/**
 * Mise en oeuvre du LocalBroadcastManager tel que présenté ici : <a href=
 * "http://developer.android.com/resources/samples/Support4Demos/src/com/example/android/supportv4/content/LocalServiceBroadcaster.html"
 * >LocalServiceBroadcaster</a> afin de permettre de transmettre des
 * informations (en l'occurence le status) entre le service et les activités.
 * 
 * @author Damien Raude-Morvan
 */
public abstract class AbstractServiceWithStatus<I extends TaskData<?>> extends
		RoboIntentService {

	/**
	 * Identifiant du service.
	 */
	private String name;

	/**
	 * Broadcaster inter à l'application
	 */
	private LocalBroadcastManager localBroadcastManager;

	public AbstractServiceWithStatus(final String name) {
		super(name);
		this.name = name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		localBroadcastManager = LocalBroadcastManager.getInstance(this);
		localBroadcastManager.sendBroadcast(SERVICE_STARTED.intent());
	}

	/**
	 * {@inheritDoc}
	 */
	protected void onHandleIntent(final Intent intent) {
		localBroadcastManager.sendBroadcast(new Intent(SERVICE_RUNNING
				.toString()));

		Future<I> future = MainApplication.executor.submit(createTask());

		Intent finishIntent = SERVICE_FINISHED.intent();
		try {
			I taskData = future.get();
			if (taskData != null) {
				finishIntent = SERVICE_FINISHED.intent(taskData.error);
			}
		} catch (InterruptedException e) {
		} catch (ExecutionException e) {
		} finally {
			localBroadcastManager.sendBroadcast(finishIntent);
		}
	}

	protected abstract Callable<I> createTask();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onDestroy() {
		Log.d(name, "onDestroy");
		super.onDestroy();
		localBroadcastManager.sendBroadcast(SERVICE_STOPPED.intent());
	}
}
