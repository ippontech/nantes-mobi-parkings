package fr.ippon.android.opendata.data.trafficfluency;

import fr.ybo.opendata.nantes.exceptions.ApiReseauException;
import fr.ybo.opendata.nantes.util.Connecteur;

import java.io.InputStream;

/**
 * Created with IntelliJ IDEA.
 * User: nicolasguillot
 * Date: 05/05/12
 * Time: 18:15
 * To change this template use File | Settings | File Templates.
 */
public class FileConnector implements Connecteur {

    /**
     * Le fichier à renvoyer.
     */
    private String file;

    /**
     * Constructeur.
     *
     */
    public FileConnector(String file) {
        this.file = file;
    }

    /**
     * {@inheritDoc}.
     */
    @Override
    public InputStream openInputStream(String url) throws ApiReseauException {
        return FileConnector.class.getResourceAsStream(file);
    }
}