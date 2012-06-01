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
package fr.ippon.android.opendata.data.trafficfluency;

import fr.ippon.android.opendata.data.trafficfluency.modele.BasicDataValue;
import fr.ippon.android.opendata.data.trafficfluency.modele.FreewaySegmentFluency;
import fr.ippon.android.opendata.data.trafficfluency.sax.Datex2Handler;
import fr.ybo.opendata.nantes.OpenDataApi;
import fr.ybo.opendata.nantes.exceptions.ApiException;
import fr.ybo.opendata.nantes.exceptions.ApiReseauException;
import fr.ybo.opendata.nantes.util.Connecteur;
import fr.ybo.opendata.nantes.util.HttpConnecteur;
import org.xml.sax.SAXException;

import javax.inject.Inject;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Logger;
import java.util.ArrayList;

/**
 * User: nicolasguillot
 * Date: 05/05/12
 * Time: 15:27
 */
public class BisonFuteApi {
    /**
     * Logger.
     */
    private static final Logger LOGGER = Logger.getLogger(BisonFuteApi.class.getSimpleName());

    /**
     * URL d'accés au API OpenDataApi.
     */
    private static final String URL = "http://www.bison-fute.gouv.fr/diffusions/datex2/base/Nantes_mvs/";


    /**
     * Connecteur.
     */
    @Inject
    private Connecteur connecteur;

    /**
     * @param connecteur {@link OpenDataApi#connecteur}.
     */
    protected void setConnecteur(Connecteur connecteur) {
        this.connecteur = connecteur;
    }

    public BisonFuteApi() {
        super();
        connecteur = new HttpConnecteur();
    }

    /**
     * Commande pour récupérer la fluidité des axes routier
     */
    private static final String CMD_SEGMENT_FLUENCY = "TraficStatus_cigt_nantes_maintenant.xml";

    /**
     * Cette commande permet de récupérer la fluiditée des axes routiers du périphérique de Nantes en temps réel.
     *
     * @return la fludité des axes routiers du centre ville de Nantes
     * @throws fr.ybo.opendata.nantes.exceptions.ApiReseauException
     *          problème réseaux.
     */
    public List<FreewaySegmentFluency> getBisonFuteTrafficStatus() throws ApiReseauException {
        //FreewaySegmentFluency
        List<FreewaySegmentFluency> freewaySegFluency = new ArrayList<FreewaySegmentFluency>();
        List<BasicDataValue> result = appelApi(getUrl(CMD_SEGMENT_FLUENCY));
        for(BasicDataValue r: result) {
            if(r instanceof FreewaySegmentFluency) {
                freewaySegFluency.add((FreewaySegmentFluency) r);
            }
        }
        return freewaySegFluency;
    }


    /**
     * @param url     url.
     * @return liste d'objets.
     * @throws ApiReseauException en cas d'erreur réseau.
     */
    private List<BasicDataValue> appelApi(String url) throws ApiReseauException {
        Datex2Handler handler = new Datex2Handler();
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            InputStream inputStream = connecteur.openInputStream(url);
            try {
                parser.parse(inputStream, handler);
            } finally {
                try {
                    inputStream.close();
                } catch (Exception exception) {
                    LOGGER.warning(exception.getMessage());
                }
            }
        } catch (IOException ioException) {
            throw new ApiReseauException(ioException);
        } catch (SAXException saxException) {
            throw new ApiReseauException(saxException);
        } catch (ParserConfigurationException exception) {
            throw new ApiException("Erreur lors de l'appel à l'API OpenData", exception);
        }
        if (handler.getData() == null || handler.getData().isEmpty()) {
            throw new ApiReseauException();
        }
        return handler.getData();
    }

    /**
     * Permet de récupérer l'URL d'accés aux API OpenData en fonction de la
     * commande à exécuter.
     *
     * @param commande commande à exécuter.
     * @return l'url.
     */
    private String getUrl(String commande) {
        StringBuilder stringBuilder = new StringBuilder(URL);
        stringBuilder.append(commande);
        return stringBuilder.toString();
    }
}
