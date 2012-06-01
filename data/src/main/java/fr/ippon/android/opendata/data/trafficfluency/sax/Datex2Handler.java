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
package fr.ippon.android.opendata.data.trafficfluency.sax;

import fr.ippon.android.opendata.data.trafficfluency.modele.BasicDataValue;
import fr.ippon.android.opendata.data.trafficfluency.modele.DataValueType;
import fr.ippon.android.opendata.data.trafficfluency.modele.FreewaySegmentFluency;
import fr.ippon.android.opendata.data.trafficfluency.modele.TrafficStatus;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Created with IntelliJ IDEA.
 * User: nicolasguillot
 * Date: 05/05/12
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */
public class Datex2Handler extends DefaultHandler {
    private final static String TIME_ELT = "time";
    private final static String LOCATION_REF_ELT = "predefinedLocationReference";
    private final static String TRAFFIC_STATUS_ELT = "trafficStatus";

    /**
     * StringBuilder servant au parsing xml.
     */
    private StringBuilder currentContent;

    protected List<BasicDataValue> data;

    protected BasicDataValue currentElement;

    protected SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public Datex2Handler() {
        super();
        dateFormatter.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
        currentContent = new StringBuilder();
        data = new ArrayList<BasicDataValue>();
    }

    @Override
    public void characters(char[] cars, int start, int length) throws SAXException {
        super.characters(cars, start, length);
        currentContent.append(cars, start, length);
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        currentContent.setLength(0);
        String type = attributes.getValue("xsi:type");
        if (qName.equalsIgnoreCase(BasicDataValue.QNAME) && DataValueType.TrafficStatusValue.getTagName().equalsIgnoreCase(type)) {
            currentElement = new FreewaySegmentFluency();
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if (currentElement instanceof FreewaySegmentFluency) {
            if (qName.equalsIgnoreCase(TIME_ELT)) {
                try {
                    String reduceDate = currentContent.toString().substring(0, currentContent.toString().length() - 6);
                    ((FreewaySegmentFluency) currentElement).setTime(dateFormatter.parse(reduceDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else if (qName.equalsIgnoreCase(LOCATION_REF_ELT)) {
                ((FreewaySegmentFluency) currentElement).setLocationId(currentContent.toString());
            } else if (qName.equalsIgnoreCase(TRAFFIC_STATUS_ELT)) {
                ((FreewaySegmentFluency) currentElement).setTrafficStatus(TrafficStatus.getTrafficStatusFrom(currentContent.toString()));
            }
        }

        if (qName.equalsIgnoreCase(BasicDataValue.QNAME) && currentElement != null) {
            data.add(currentElement);
            currentElement = null;
        }

    }

    public List<BasicDataValue> getData() {
        return data;
    }
}
