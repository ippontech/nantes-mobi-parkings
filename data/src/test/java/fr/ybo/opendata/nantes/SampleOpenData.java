package fr.ybo.opendata.nantes;

import fr.ybo.opendata.nantes.exceptions.ApiReseauException;
import fr.ybo.opendata.nantes.util.Connecteur;

import java.io.InputStream;

public class SampleOpenData extends OpenDataApi {

    public SampleOpenData(final String stub) {
        super("INVALID_KEY");
        this.setConnecteur(new Connecteur() {
            public InputStream openInputStream(String url) throws ApiReseauException {
                return SimpleOpenDataApiTest.class.getResourceAsStream(stub);
            }
        });
    }
}
