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
import org.n52.sta.awiingestor.model.sta.Observation;
import org.n52.sta.awiingestor.model.sta.ObservedProperty;
import org.n52.sta.awiingestor.model.sta.Sensor;
import org.n52.sta.awiingestor.model.sta.Thing;
import org.n52.sta.awiingestor.model.sta.UnitOfMeasurement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author <a href="mailto:j.speckamp@52north.org">Jan Speckamp</a>
 */
public class STATransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(STATransformer.class);
    private static final String OBS_TYPE_MEASUREMENT =
        "http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_Measurement";
    private static final GeometryFactory geometryFactory = new GeometryFactory();

    static Thing toSTAThing(O2APlatform o2APlatform) {
        Thing staThing = new Thing();
        staThing.setId(String.valueOf(o2APlatform.getId()));
        staThing.setName(o2APlatform.getPlatform() + " - " + o2APlatform.getCode());
        staThing.setDescription(o2APlatform.getPlatform() + " - " + o2APlatform.getType());
        return staThing;
    }

    static Sensor toSTASensor(O2ASensor o2ASensor) {
        Sensor staSensor = new Sensor();
        String sensorName = o2ASensor.getSensor().substring(0, o2ASensor.getSensor().lastIndexOf(":"));
        staSensor.setId(sensorName);
        staSensor.setName(sensorName);
        staSensor.setDescription(sensorName);
        staSensor.setProperties(o2ASensor.getAdditionalProperties());
        staSensor.setMetadata(sensorName);
        staSensor.setEncodingType("http://www.opengis.net/doc/IS/SensorML/2.0");

        return staSensor;
    }

    static Datastream toSTADatastream(String sensorId, AWIDataset o2ADataset) {
        Datastream staDatastream = new Datastream();
        staDatastream.setId(String.valueOf(o2ADataset.getId()) + " - " + sensorId);
        staDatastream.setName(String.valueOf(o2ADataset.getId()));
        staDatastream.setDescription("Id:" + o2ADataset.getId());
        staDatastream.setObservationType(OBS_TYPE_MEASUREMENT);
        staDatastream.setUnitOfMeasurement(new UnitOfMeasurement());
        staDatastream.setProperties(o2ADataset.getAdditionalProperties());
        return staDatastream;
    }

    static ObservedProperty toSTAObservedProperty(AWISensorOutput awiSensorOutput) {
        ObservedProperty obsProp = new ObservedProperty();

        obsProp.setId(awiSensorOutput.getShortname());
        obsProp.setDescription(awiSensorOutput.getName());
        obsProp.setName(awiSensorOutput.getName());
        obsProp.setDefinition(awiSensorOutput.getShortname() + awiSensorOutput.getSensorOutputType().getSystemName());

        Map<String, Object> properties = new HashMap<>();
        properties.put("sensorOutputType", awiSensorOutput.getSensorOutputType());
        properties.put("unitOfMeasurement", awiSensorOutput.getUnitOfMeasurement());
        properties.put("sourceUrl", awiSensorOutput.getSourceUrl());

        obsProp.setProperties(properties);
        return obsProp;
    }

    static Observation toSTAObservation(List<String> datum, Datastream datastream, List<FeatureOfInterest> events) {
        LocalDateTime timestamp = LocalDateTime.parse(datum.get(0));
        String value = datum.get(1);
        FeatureOfInterest matchingFOI = null;

        // Search for matching event to get foi
        for (FeatureOfInterest event : events) {
            if (timestamp.compareTo((LocalDateTime) event.getProperties().get("startDate")) >= 0
                && timestamp.compareTo((LocalDateTime) event.getProperties().get("endDate")) <= 0) {
                matchingFOI = event;
                break;
            }
        }
        Observation obs = new Observation();
        FeatureOfInterest foiRef = new FeatureOfInterest();
        if (matchingFOI == null) {
            // LOGGER.debug("Could not find matching event. using empty dummy foi");
            foiRef.setId("unknown");
        } else {
            foiRef.setId(matchingFOI.getId());
        }
        obs.setFeatureOfInterest(foiRef);

        obs.setResult(value);
        //TODO: check if this is correct
        obs.setPhenomenonTime(timestamp.atOffset(ZoneOffset.UTC));

        Datastream datastreamRef = new Datastream();
        datastreamRef.setId(datastream.getId());
        obs.setDatastream(datastreamRef);
        return obs;
    }
}
