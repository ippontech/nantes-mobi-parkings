package fr.ippon.android.opendata.android.service;

import static com.google.common.collect.Collections2.filter;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import android.content.Context;
import android.util.Log;
import fr.ippon.android.opendata.android.MainApplication;
import fr.ippon.android.opendata.android.R;
import fr.ippon.android.opendata.data.parkings.ParkingEquipement;
import fr.ippon.android.opendata.data.parkings.ParkingsPredicate;
import fr.ybo.moteurcsv.MoteurCsv;
import fr.ybo.moteurcsv.MoteurCsv.InsertObject;
import fr.ybo.opendata.nantes.util.EquipementManager;

/**
 * Gestionnaire des équipements dédié pour Android (manière différente de
 * charger les resources depuis l'APK).
 */
public class EquipementManagerAndroid extends EquipementManager {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = Logger
			.getLogger(EquipementManagerAndroid.class.getSimpleName());

	/**
	 * Identifiant du fichier qui va permettre de stocker la date de dernière
	 */
	private static final String fileName = "equipements_publics_deplacement_lastUpdate.txt";

	/**
	 * Encoding du fichier fournis par Nantes Métropole.
	 */
	private static final String CHARSET_NAME = "UTF-8";

	private static final String LAST_UPDATE_DATEFORMAT = "dd/MM/yyyy";

	private static final String TAG = EquipementManagerAndroid.class.getName();

	/**
	 * Map des equipements.
	 */
	private Map<Integer, ParkingEquipement> mapParkingEquipements;

	/**
	 * Ouverture du flux vers le fichier
	 * 
	 * @return InputStream du fichier ou null si introuvable
	 */
	@Override
	protected InputStream getInputStream() {
		Context context = MainApplication.getAppContext();

		return context.getResources().openRawResource(
				R.raw.equipements_publics_deplacement);
	}

	/**
	 * Custom...
	 * @return {@link EquipementManager#mapEquipements}.
	 */
	public Map<Integer, ParkingEquipement> getMapDetailedEquipement() {
		if (mapParkingEquipements == null) {
			mapParkingEquipements = new HashMap<Integer, ParkingEquipement>();
			MoteurCsv moteurCsv = new MoteurCsv(
					Arrays.<Class<?>> asList(ParkingEquipement.class));
			BufferedReader bufferedReader = null;
			try {
				bufferedReader = new BufferedReader(new InputStreamReader(
						getInputStream(), CHARSET_NAME));
				try {
					moteurCsv.parseFileAndInsert(bufferedReader,
							ParkingEquipement.class,
							new EquipementInsertObject(mapParkingEquipements));
				} finally {
					try {
						bufferedReader.close();
					} catch (Exception exception) {
						LOGGER.warning(exception.getMessage());
					}
				}
			} catch (UnsupportedEncodingException exception) {
				LOGGER.warning(exception.getMessage());
			}

		}
		return mapParkingEquipements;
	}

	/**
	 * Récupération de la liste des équipements qui correspondent au prédicat
	 * "Parking".
	 * 
	 * @return Collection d'Equipement qui correspondent.
	 */
	public Collection<ParkingEquipement> getParkings() {
		Map<Integer, ParkingEquipement> map = getMapDetailedEquipement();
		return filter(map.values(), new ParkingsPredicate());
	}

	/**
	 * Permet d'enregistrer la date de la mise à jour dans le stockage de
	 * l'application.
	 */
	public void writeAppliedLastUpdate(Context ctx, Date date) {
		DateFormat storageFormat = new SimpleDateFormat(LAST_UPDATE_DATEFORMAT);
		if (date == null) {
			Log.w(TAG,
					"Impossible de lire la date de dernière mise à jour du CSV");
		} else {
			String text = storageFormat.format(date);

			try {
				FileOutputStream fos = ctx.openFileOutput(fileName,
						Context.MODE_PRIVATE);
				fos.write(text.getBytes());
				fos.close();
			} catch (IOException e) {
				Log.w(TAG,
						"Impossible d'écrire le fichier de métadonnées (cache)");
			}
		}
	}

	/**
	 * Permet de lire la date de la mise à jour dans le stockage de
	 * l'application.
	 */
	public Date readAppliedLastUpdate(Context ctx) {
		Date actualDate = null;
		DateFormat storageFormat = new SimpleDateFormat(LAST_UPDATE_DATEFORMAT);

		try {
			InputStream instream = ctx.openFileInput(fileName);
			if (instream != null) {
				BufferedReader inreader = new BufferedReader(
						new InputStreamReader(instream));
				String line = inreader.readLine();
				actualDate = storageFormat.parse(line);
			}
			instream.close();
		} catch (IOException e) {
			Log.w(TAG, "Impossible de lire le fichier de métadonnées (cache)");
		} catch (ParseException e) {
			Log.w(TAG, "fichier de métadonnées (cache) invalide");
		}

		return actualDate;
	}

	/**
	 * Permet de lire la date de la mise à jour dans le stockage de
	 * l'application.
	 */
	public Date readAvailableLastUpdate(Context ctx) {
		Date actualDate = null;
		DateFormat storageFormat = new SimpleDateFormat(LAST_UPDATE_DATEFORMAT);

		try {
			InputStream instream = ctx.getResources().openRawResource(
					R.raw.equipements_publics_deplacement_csv);
			if (instream != null) {
				BufferedReader inreader = new BufferedReader(
						new InputStreamReader(instream,
								Charset.forName("ISO-8859-1")));
				String line = inreader.readLine();
				while (line != null && actualDate == null) {
					if (line.startsWith("Date de derni")) {
						actualDate = storageFormat.parse(line.split(":")[1]
								.trim());
					}
					line = inreader.readLine();
				}

			}
			instream.close();
		} catch (IOException e) {
			Log.w(TAG, "Impossible de lire le fichier CSV");
		} catch (ParseException e) {
			Log.w(TAG, "Fichier CSV avec des données invalides");
		}

		return actualDate;
	}

	/**
	 * Permet de s'avoir s'il faut relancer un import des données du CSV.
	 * 
	 * @param ctx
	 *            Context Android
	 * @return true si la date enregistrée est égale à la date du CSV.
	 */
	public boolean isParkingsUptodate(Context ctx) {
		Date appliedUpdate = readAppliedLastUpdate(ctx);
		Date availableUpdate = readAvailableLastUpdate(ctx);
		if (appliedUpdate != null && availableUpdate != null
				&& (!availableUpdate.after(appliedUpdate))) {
			return true;
		}
		return false;
	}

	/**
	 * Permet de définir la date de dernière mise à jour des équipements à la
	 * valeur de la date contenue dans le CVS.
	 * 
	 * @param ctx
	 *            Context Android
	 */
	public void setUptodate(Context ctx) {
		Date appliedUpdate = readAvailableLastUpdate(ctx);
		writeAppliedLastUpdate(ctx, appliedUpdate);
	}

	/**
	 * Gestion des {@link ParkingEquipement}.
	 */
	private static final class EquipementInsertObject implements
			InsertObject<ParkingEquipement> {
		/**
		 * Map des equipements.
		 */
		private Map<Integer, ParkingEquipement> mapEquipements;

		/**
		 * Constructeur.
		 * 
		 * @param mapEquipements
		 *            map des équipements.
		 */
		private EquipementInsertObject(
				Map<Integer, ParkingEquipement> mapEquipements) {
			this.mapEquipements = mapEquipements;
		}

		@Override
		public void insertObject(ParkingEquipement objet) {
			mapEquipements.put(objet.getIdObj(), objet);
		}
	}
}
