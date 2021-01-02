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

import org.n52.sta.awiingestor.model.awi.AWIDataset;
import org.n52.sta.awiingestor.model.awi.AWISensorDetailedItem;
import org.n52.sta.awiingestor.model.awi.AWISensorOutput;
import org.n52.sta.awiingestor.model.awi.O2ASensor;
import org.n52.sta.awiingestor.model.sta.Datastream;
import org.n52.sta.awiingestor.model.sta.FeatureOfInterest;
import org.n52.sta.awiingestor.model.sta.ObservedProperty;
import org.n52.sta.awiingestor.model.sta.Sensor;
import org.n52.sta.awiingestor.model.sta.Thing;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

/**
 * @author <a href="mailto:j.speckamp@52north.org">Jan Speckamp</a>
 */
@Component
public class O2AHarvesterJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(O2AHarvesterJob.class);

    @Autowired private DataAPI dataAPI;
    @Autowired private StaAPI staAPI;
    @Autowired private SensorsAPI sensorsAPI;

    @Override
    public void execute(JobExecutionContext context)
        throws JobExecutionException {
        try {
            LOGGER.info("starting harvesting");

            // Get all Platforms
            Arrays.stream(dataAPI.getPlatforms())
                .forEach(p -> staAPI.postThing(STATransformer.toSTAThing(p)));

            // Get all Sensors connected to moses project
            O2ASensor[] sensors = dataAPI.getSensors("*moses*");

            for (O2ASensor o2aSensor : sensors) {
                try {
                LOGGER.info("handling" + o2aSensor.getSensor());
                // Split o2aSensor into STAThing + STASensor
                staAPI.postSensor(STATransformer.toSTASensor(o2aSensor));

                // Retrieve additional Metadata for sensor
                dataAPI.augmentWithMetadata(o2aSensor);

                // Check if linkId is properly set
                // Needed for cross-referencing in SensorsAPI
                String linkId = o2aSensor.getMetadata().getMetadata().getSensorLinkId();
                if (linkId == null || linkId.equals("")) {
                    LOGGER.error("could not parse linkId of Sensor: " + o2aSensor.getSensor() + ". skipping sensor!");
                    continue;
                }

                // Get Sensor Outputs and parse to STAObservedProperty
                Set<String> sensorOutputURNs = sensorsAPI.getSensorOutputURNs(linkId);
                for (String urn : sensorOutputURNs) {
                    AWISensorOutput sensorOutputByURN = sensorsAPI.getSensorOutputByURN(urn);
                    // Parse ObservedProperties from AWISensorOutput
                    staAPI.postObservedProperty(STATransformer.toSTAObservedProperty(sensorOutputByURN));
                }

                // Get Sensor Details and parse to STALocation
                AWISensorDetailedItem sensorDetailedItem = sensorsAPI.getSensorDetailedItem(linkId);
                if (sensorDetailedItem != null) {
                    sensorDetailedItem.getEventItem()
                        .stream()
                        .filter(Objects::nonNull)
                        .filter(e -> e.getEvent() != null)
                        .forEach(eventItem -> {
                            FeatureOfInterest foi = STATransformer.toSTAFeatureOfInterest(eventItem.getEvent());
                            if (foi != null) {
                                staAPI.postFeatureOfInterest(foi);
                            }
                        });
                }

                // Get Dataset
                AWIDataset[] datasets = dataAPI.getDataset(o2aSensor.getSensor());
                Arrays.stream(datasets)
                    .forEach(dataset -> {
                        Datastream staDatastream = STATransformer.toSTADatastream(dataset);
                        Thing thingRef = new Thing();
                        thingRef.setId(String.valueOf(o2aSensor.getPlatformId()));
                        staDatastream.setThing(thingRef);

                        Sensor sensorRef = new Sensor();
                        sensorRef.setId(o2aSensor.getSensor());
                        staDatastream.setSensor(sensorRef);

                        ObservedProperty observedProperty = new ObservedProperty();
                        observedProperty.setId(o2aSensor.getSensor()
                                                   .substring(o2aSensor.getSensor().lastIndexOf(":") + 1));
                        staDatastream.setObservedProperty(observedProperty);
                        staAPI.postDatastream(staDatastream);
                    });

                // Get Data

                // Create STAFeatureOfInterest by searching STAThing->STAHistoricalLocations for a matching timeframe
                // if there is no valid deployment information we fallback to a generic FOI without a feature

                } catch (Exception e) {
                    LOGGER.error("error while handling:" + e.getMessage());
                    // throw new JobExecutionException(e);
                }

            }
            LOGGER.info("finished harvesting");
        } catch (Exception e) {
            // throw new JobExecutionException(e);
        }
    }
}
