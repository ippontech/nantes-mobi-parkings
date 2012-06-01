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
package fr.ippon.android.opendata.data.distance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;

/**
 * Calcul de la distance entre deux points en utilisant l'API Google Maps
 * Direction.
 * 
 * <a href="http://code.google.com/intl/fr-FR/apis/maps/documentation/directions/">Google Maps Direction API</a>
 * 
 * @author Damien Raude-Morvan
 */
public class GoogleMapsDirectionDistanceCalculator implements
		DistanceCalculator {

	/**
	 * URL de l'API Google Maps Direction
	 */
	private static final String GOOGLE_API = "http://maps.googleapis.com/maps/api/directions/json";

	/**
	 * {@inheritDoc}
	 */
	public double getDistanceBetweenPoints(GpsPoint p1, GpsPoint p2) {

		HttpGet httpRequest = new HttpGet(appendParameterToUrl(GOOGLE_API, p1,
				p2));

		HttpClient httpclient = new DefaultHttpClient();
		HttpResponse response = null;
		GoogleRoutes pl = null;
		try {
			response = httpclient.execute(httpRequest);
			String jsonString = inputStreamAsString(response.getEntity()
					.getContent());
			pl = getGoogleRoutes(jsonString);
		} catch (ClientProtocolException e) {
			// Impossible de traiter la requete...
			// FIXME Log ou Exception ?
			e.printStackTrace();
		} catch (IOException e) {
			// FIXME Log ou Exception ?
			e.printStackTrace();
		}

		// Extraction de la seule étape du trajet
		if (pl != null) {
			return pl.getLeg(0).getDistance();
		}

		// FIXME Retour d'un objet complexe capable de contenir (valeur +
		// statut)
		return -1;
	}

	/**
	 * Ajout des paramètres à l'URL.
	 * 
	 * @param url URL Racine sur laquelle faire les appels
	 * @param p1 Point GPS 1
	 * @param p2 Point GPS 2
	 * @return URL complète, utilisant par HTTP Client
	 */
	protected String appendParameterToUrl(String url, GpsPoint p1, GpsPoint p2) {
		if (!url.endsWith("?")) {
			url += "?";
		}

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		StringBuilder sbp1 = new StringBuilder();
		sbp1.append(p1.getLatitude()).append(",").append(p1.getLongitude());
		params.add(new BasicNameValuePair("origin", sbp1.toString()));
		StringBuilder sbp2 = new StringBuilder();
		sbp2.append(p2.getLatitude()).append(",").append(p2.getLongitude());
		params.add(new BasicNameValuePair("destination", sbp2.toString()));
		params.add(new BasicNameValuePair("region", "fr"));
		params.add(new BasicNameValuePair("units", "metric"));
		params.add(new BasicNameValuePair("sensor", "false"));

		String paramString = URLEncodedUtils.format(params, "utf-8");

		url += paramString;

		return url;
	}

	/**
	 * Transformation du flux Json (String) en un objet Java (GoogleRoutes).
	 * 
	 * @param jsonString
	 *            Flux renvoyé par l'API Google Directions
	 * @return object GoogleRoutes
	 */
	protected GoogleRoutes getGoogleRoutes(String jsonString) {
		GoogleRoutes pl = null;
		Gson gson = new Gson();
		pl = gson.fromJson(jsonString, GoogleRoutes.class);
		return pl;
	}

	/**
	 * Transformation du flux en un String
	 * 
	 * @param stream
	 *            Flux d'entré (réseau)
	 * @return String contenant l'ensemble des données
	 * @throws IOException
	 */
	protected static String inputStreamAsString(InputStream stream)
			throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
		StringBuilder sb = new StringBuilder();
		String line = null;

		while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
		}

		br.close();
		return sb.toString();
	}
}
