/*
 * Copyright (C) 2018-2020 52°North Initiative for Geospatial Open Source
 * Software GmbH
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 as published
 * by the Free Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of
 * the following licenses, the combination of the program with the linked
 * library is not considered a "derivative work" of the program:
 *
 *     - Apache License, version 2.0
 *     - Apache Software License, version 1.0
 *     - GNU Lesser General Public License, version 3
 *     - Mozilla Public License, versions 1.0, 1.1 and 2.0
 *     - Common Development and Distribution License (CDDL), version 1.0
 *
 * Therefore the distribution of the program linked with libraries licensed
 * under the aforementioned licenses, is permitted by the copyright holders
 * if the distribution is compliant with both the GNU General Public
 * License version 2 and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 */

package org.n52.sta.awiingestor;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.n52.sta.awiingestor.model.awi.AWIDataset;
import org.n52.sta.awiingestor.model.awi.AWISensorOutput;
import org.n52.sta.awiingestor.model.awi.Event;
import org.n52.sta.awiingestor.model.awi.O2APlatform;
import org.n52.sta.awiingestor.model.awi.O2ASensor;
import org.n52.sta.awiingestor.model.sta.Datastream;
import org.n52.sta.awiingestor.model.sta.Feature;
import org.n52.sta.awiingestor.model.sta.FeatureOfInterest;
import org.n52.sta.awiingestor.model.sta.ObservedProperty;
import org.n52.sta.awiingestor.model.sta.Sensor;
import org.n52.sta.awiingestor.model.sta.Thing;
import org.n52.sta.awiingestor.model.sta.UnitOfMeasurement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:j.speckamp@52north.org">Jan Speckamp</a>
 */
public class STATransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(STATransformer.class);
    private static final String OBS_TYPE_MEASUREMENT =
        "http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_Measurement";

    static Thing toSTAThing(O2APlatform o2APlatform) {
        Thing staThing = new Thing();
        staThing.setId(String.valueOf(o2APlatform.getId()));
        staThing.setName(o2APlatform.getPlatform() + " - " + o2APlatform.getCode());
        staThing.setDescription(o2APlatform.getPlatform() + " - " + o2APlatform.getType());

        Map<String, Object> properties = new HashMap<>();
        staThing.setProperties(properties);

        return staThing;
    }

    static Sensor toSTASensor(O2ASensor o2ASensor) {
        Sensor staSensor = new Sensor();
        staSensor.setId(o2ASensor.getSensor());
        staSensor.setName(o2ASensor.getSensor());
        staSensor.setDescription(o2ASensor.getSensor());
        staSensor.setProperties(o2ASensor.getAdditionalProperties());
        staSensor.setMetadata(o2ASensor.getSensor());
        staSensor.setEncodingType("http://www.opengis.net/doc/IS/SensorML/2.0");

        return staSensor;
    }

    static Datastream toSTADatastream(AWIDataset o2ADataset) {
        Datastream staDatastream = new Datastream();
        staDatastream.setName(String.valueOf(o2ADataset.getId()));
        staDatastream.setDescription("Id:" + o2ADataset.getId());
        staDatastream.setObservationType(OBS_TYPE_MEASUREMENT);
        staDatastream.setUnitOfMeasurement(new UnitOfMeasurement());
        return staDatastream;
    }

    static ObservedProperty toSTAObservedProperty(AWISensorOutput awiSensorOutput) {
        ObservedProperty obsProp = new ObservedProperty();

        obsProp.setId(awiSensorOutput.getShortname());
        String description = awiSensorOutput.getSensorOutputType().getDescription();
        obsProp.setDescription(description.equals("") ? "default_description" : description);
        obsProp.setName(awiSensorOutput.getSensorOutputType().getGeneralName());
        obsProp.setDefinition(awiSensorOutput.getShortname() + awiSensorOutput.getSensorOutputType().getSystemName());

        Map<String, Object> properties = new HashMap<>();
        properties.put("lastModified", awiSensorOutput.getSensorOutputType().getLastModified());
        properties.put("vocabularyID", awiSensorOutput.getSensorOutputType().getVocabularyID());
        properties.put("vocabularyValue", awiSensorOutput.getSensorOutputType().getVocableValue());
        properties.put("shortname", awiSensorOutput.getShortname());
        properties.put("sourceUrl", awiSensorOutput.getSourceUrl());

        obsProp.setProperties(properties);
        return obsProp;
    }

    static FeatureOfInterest toSTAFeatureOfInterest(Event event) {
        try {
            if (event.getEventType()
                .getVocableValue()
                .equals("http://vocab.nerc.ac.uk/collection/W03/current/W030002/")
                && event.getLatitude() != null
                && event.getLongitude() != null
            ) {
                FeatureOfInterest foi = new FeatureOfInterest();
                Feature feature = new Feature();
                GeometryFactory geometryFactory = new GeometryFactory();

                Point point = geometryFactory.createPoint(new Coordinate(event.getLatitude(), event.getLongitude()));
                feature.setGeometry(point);
                foi.setName(event.getLabel());
                foi.setDescription("Desc:" + event.getDescription());
                foi.setEncodingType("application/vnd.geo+json");
                // foi.setProperties(mapper.convertValue(event, ObjectNode.class));
                foi.setFeature(feature);
                return foi;
            }
            return null;
        } catch (Exception e) {
            //e.printStackTrace();
            LOGGER.error("Skipped parsing event due to error: " + event.getUuid() + e.getMessage());
            return null;
        }
    }
}
