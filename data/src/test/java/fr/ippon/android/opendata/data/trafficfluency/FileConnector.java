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