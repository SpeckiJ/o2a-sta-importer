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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.n52.sta.awiingestor.model.sta.Datastream;
import org.n52.sta.awiingestor.model.sta.FeatureOfInterest;
import org.n52.sta.awiingestor.model.sta.IdentifiedEntity;
import org.n52.sta.awiingestor.model.sta.Observation;
import org.n52.sta.awiingestor.model.sta.ObservedProperty;
import org.n52.sta.awiingestor.model.sta.Sensor;
import org.n52.sta.awiingestor.model.sta.Thing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author <a href="mailto:j.speckamp@52north.org">Jan Speckamp</a>
 */
@Component
public class StaAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(StaAPI.class);

    private WebClient client;

    public StaAPI(ObjectMapper mapper, @Value("${server.config.staURL}") String staURL) {
        client = WebClient.builder()
            .baseUrl(staURL)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .codecs(clientCodecConfigurer -> {
                clientCodecConfigurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024);
                clientCodecConfigurer.defaultCodecs().jackson2JsonDecoder(
                    new Jackson2JsonDecoder(mapper, MediaType.APPLICATION_JSON));
                clientCodecConfigurer.defaultCodecs().jackson2JsonEncoder(
                    new Jackson2JsonEncoder(mapper, MediaType.APPLICATION_JSON));

            })
            .build();
    }

    private void postEntity(String path, Class clazz, IdentifiedEntity entity) {
        LOGGER.trace(
            client.post()
                .uri(path)
                .body(BodyInserters.fromPublisher(Mono.just(entity), clazz))
                .retrieve()
                .onStatus(e -> e.value() == 409, e -> {
                    LOGGER.trace("skipping {} with id {} due to conflict!",
                                 clazz.getSimpleName(),
                                 entity.getId());
                    return Mono.empty();
                })
                .onStatus(HttpStatus::is5xxServerError, e -> {
                    LOGGER.trace("error occured while persisting {} with id {}",
                                 clazz.getSimpleName(),
                                 entity.getId());
                    return Mono.empty();
                })
                .bodyToMono(String.class)
                .block()
        );
    }

    void postThing(Thing thing) {
        postEntity("/Things", Thing.class, thing);
    }

    void postDatastream(Datastream datastream) {
        postEntity("/Datastreams", Datastream.class, datastream);
    }

    void postSensor(Sensor sensor) {
        postEntity("/Sensors", Sensor.class, sensor);
    }

    void postObservedProperty(ObservedProperty obsProp) {
        postEntity("/ObservedProperties", ObservedProperty.class, obsProp);
    }

    void postFeatureOfInterest(FeatureOfInterest foi) {
        postEntity("/FeaturesOfInterest", FeatureOfInterest.class, foi);
    }

    void postObservation(Observation observation) {
        postEntity("/Observations", Observation.class, observation);
    }
}
