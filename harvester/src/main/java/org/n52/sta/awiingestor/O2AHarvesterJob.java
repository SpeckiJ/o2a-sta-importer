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
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.n52.sta.awiingestor.model.awi.AWISensorDetailedItem;
import org.n52.sta.awiingestor.model.awi.AWISensorOutput;
import org.n52.sta.awiingestor.model.awi.Event;
import org.n52.sta.awiingestor.model.awi.EventItem;
import org.n52.sta.awiingestor.model.awi.O2AData;
import org.n52.sta.awiingestor.model.awi.O2ASensor;
import org.n52.sta.awiingestor.model.sta.Datastream;
import org.n52.sta.awiingestor.model.sta.Feature;
import org.n52.sta.awiingestor.model.sta.FeatureOfInterest;
import org.n52.sta.awiingestor.model.sta.ObservedProperty;
import org.n52.sta.awiingestor.model.sta.Sensor;
import org.n52.sta.awiingestor.model.sta.Thing;
import org.n52.sta.awiingestor.model.sta.UnitOfMeasurement;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:j.speckamp@52north.org">Jan Speckamp</a>
 */
@Component
public class O2AHarvesterJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(O2AHarvesterJob.class);
    private static final GeometryFactory geometryFactory = new GeometryFactory();

    @Autowired private DataAPI dataAPI;
    @Autowired private StaAPI staAPI;
    @Autowired private SensorsAPI sensorsAPI;

    @Override
    public void execute(JobExecutionContext context)
        throws JobExecutionException {
        try {
            LOGGER.info("starting harvesting");

            // Create dummy feature
            createDummyFOI();

            // Get all Platforms
            Arrays.stream(dataAPI.getPlatforms())
                .forEach(p -> staAPI.postThing(STATransformer.toSTAThing(p)));

            // Get all Sensors connected to moses project
            O2ASensor[] sensors = dataAPI.getSensors("*vessel:litto*");

            for (int i = 2, sensorsLength = sensors.length; i < sensorsLength; i++) {
                O2ASensor o2aSensor = sensors[i];
                try {
                    LOGGER.info("handling " + i + "/" + sensors.length + " - " + o2aSensor.getSensor());

                    // Retrieve O2ADatasets
                    Set<Datastream> datastreams =
                        Arrays.stream(dataAPI.getDataset(o2aSensor.getSensor()))
                            .map(elem -> STATransformer.toSTADatastream(o2aSensor.getSensor(), elem))
                            .collect(Collectors.toSet());

                    // Parse o2aSensor to staSensor
                    Sensor sensor = STATransformer.toSTASensor(o2aSensor);
                    staAPI.postSensor(sensor);

                    // Associate each Datastream with the Sensor +  Platform
                    datastreams.forEach(
                        ds -> {
                            // Create Thing reference
                            Thing thing = new Thing();
                            thing.setId(String.valueOf(o2aSensor.getPlatformId()));
                            ds.setThing(thing);

                            // Create Sensor reference
                            ds.setSensor(sensor.getReference());
                        }
                    );

                    // Retrieve additional Metadata for sensor
                    // e.g. the linkId to find the Sensor in the SensorsAPI
                    dataAPI.augmentWithMetadata(o2aSensor);

                    // Check if linkId is properly set
                    // Needed for cross-referencing in SensorsAPI
                    String linkId = o2aSensor.getMetadata().getMetadata().getSensorLinkId();
                    if (linkId == null || linkId.equals("")) {
                        LOGGER.error(
                            "could not parse linkId of Sensor: " + o2aSensor.getSensor() + ". skipping sensor!");
                        continue;
                    }

                    // Get Sensor Outputs and parse to STAObservedProperty
                    Set<String> sensorOutputURNs = sensorsAPI.getSensorOutputURNs(linkId);
                    for (String urn : sensorOutputURNs) {
                        if (urn.equals(o2aSensor.getCode())) {
                            AWISensorOutput sensorOutputByURN = sensorsAPI.getSensorOutputByURN(urn);
                            // Parse ObservedProperties from AWISensorOutput
                            ObservedProperty observedProperty = STATransformer.toSTAObservedProperty(sensorOutputByURN);
                            staAPI.postObservedProperty(observedProperty);
                            datastreams.forEach(ds -> ds.setObservedProperty(observedProperty.getReference()));
                        }
                    }

                    final GeometryFactory geometryFactory = new GeometryFactory();

                    // Get Sensor Details and parse to STALocation
                    AWISensorDetailedItem sensorDetailedItem = sensorsAPI.getSensorDetailedItem(linkId);
                    if (sensorDetailedItem == null) {
                        LOGGER.error(
                            "could not get SensorDetailedItem of sensor: " + o2aSensor.getSensor() +
                                ". skipping sensor!");
                        continue;
                    }
                    Set<EventItem> validEvents = sensorDetailedItem.getEventItem()
                        .stream()
                        .filter(Objects::nonNull)
                        .filter(e -> e.getEvent() != null)
                        .filter(e -> e.getEvent().getEventType() != null)
                        .collect(Collectors.toSet());

                    Map<Event, Set<Event>> missions = new HashMap<>();
                    // find MISSION Item
                    for (EventItem event : validEvents) {
                        if (event.getEvent().getEventType()
                            .getVocableValue()
                            .equals("http://vocab.nerc.ac.uk/collection/G07/current/006/")) {
                            missions.put(event.getEvent(), new HashSet<>());
                        }
                    }

                    // Get all Deployments inside mission timeframe
                    for (EventItem event : validEvents) {
                        missions.forEach((k, v) -> {
                                             if (event.getEvent().getStartDate().isAfter(k.getStartDate()) &&
                                                 event.getEvent().getEndDate().isBefore(k.getEndDate())) {
                                                 v.add(event.getEvent());
                                             }
                                         }
                        );
                    }

                    // Create actual featureofInterest
                    List<FeatureOfInterest> featuresOfInterest = new ArrayList<>();
                    missions.forEach((k, v) -> {
                        FeatureOfInterest foi = new FeatureOfInterest();
                        foi.setId(k.getLabel().toLowerCase().replaceAll("\\s", "_"));
                        foi.setName(k.getLabel());
                        foi.setDescription("Mission Description:" + k.getDescription());
                        foi.setEncodingType("application/vnd.geo+json");
                        Map<String, Object> properties = new HashMap<>();
                        HashSet<String> validDeployments = new HashSet<>();
                        properties.put("validDeployments", validDeployments);
                        HashSet<String> invalidDeployments = new HashSet<>();
                        properties.put("invalidDeployments", invalidDeployments);
                        properties.put("startDate", k.getStartDate());
                        properties.put("endDate", k.getEndDate());
                        foi.setProperties(properties);
                        Geometry polygon = geometryFactory.createPolygon();

                        // Create linestring from deployments
                        for (Event deployment : v) {// Check if event is a deployment
                            if (deployment.getEventType()
                                .getVocableValue()
                                .equals("http://vocab.nerc.ac.uk/collection/W03/current/W030002/")
                            ) {
                                // Check if there are valid coordinates
                                if (!Objects.equals(deployment.getLatitude(), 0d)
                                    && !Objects.equals(deployment.getLongitude(), 0d)) {
                                    Point point =
                                        geometryFactory.createPoint(new Coordinate(deployment.getLongitude(),
                                                                                   deployment.getLatitude()));
                                    polygon = polygon.union(point);
                                    validDeployments.add(deployment.getLabel());
                                } else {
                                    invalidDeployments.add(deployment.getLabel());
                                }
                            }
                        }
                        Feature feature = new Feature();
                        feature.setGeometry(polygon.convexHull());
                        foi.setFeature(feature);

                        featuresOfInterest.add(foi);
                    });

                    featuresOfInterest.forEach(featureOfInterest -> {
                        // Post Feature
                        staAPI.postFeatureOfInterest(featureOfInterest);

                        // Update datastreams observedArea
                        datastreams.forEach(
                            ds -> {
                                if (ds.getObservedArea() == null) {
                                    ds.setObservedArea(geometryFactory.createPolygon());
                                }
                                ds.setObservedArea(
                                    ds.getObservedArea()
                                        .union(featureOfInterest.getFeature().getGeometry()));
                            }
                        );
                    });

                    // Sort Events by startDate
                    //featuresOfInterest.sort(
                    //    Comparator.comparing((a) -> ((LocalDateTime) a.getProperties().get("startDate"))));

                    // Post Datastream
                    datastreams.forEach(ds -> {
                        ds.setObservedArea(ds.getObservedArea().convexHull());
                        //TODO: actually find correct uom
                        ds.setUnitOfMeasurement(new UnitOfMeasurement(o2aSensor.getUnit(),
                                                                      o2aSensor.getUnit(),
                                                                      o2aSensor.getUnit()));
                        staAPI.postDatastream(ds);
                    });

                    // Get Data
                    datastreams.forEach(
                        ds -> {
                            O2AData data = dataAPI.getData(o2aSensor.getSensor(), ds.getName());
                            data.getData().forEach(elem ->
                                                       staAPI.postObservation(
                                                           STATransformer.toSTAObservation(elem,
                                                                                           ds,
                                                                                           featuresOfInterest))
                            );
                        }
                    );
                } catch (Exception e) {
                    LOGGER.error("error while handling:" + e.getMessage());
                    e.printStackTrace();
                    // throw new JobExecutionException(e);
                }

            }
            LOGGER.info("finished harvesting");
        } catch (Exception e) {
            throw new JobExecutionException(e);
        }

    }

    private void createDummyFOI() {
        FeatureOfInterest dummy = new FeatureOfInterest();
        dummy.setId("unknown");
        dummy.setEncodingType("application/vnd.geo+json");
        dummy.setName("unknown");
        dummy.setDescription("dummy feature to guarantee model consistency");

        Point point = geometryFactory.createPoint(new Coordinate(0, 0));
        Feature feature = new Feature();
        feature.setGeometry(point);
        dummy.setFeature(feature);

        staAPI.postFeatureOfInterest(dummy);
    }
}
