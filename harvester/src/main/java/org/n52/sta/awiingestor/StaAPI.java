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
import org.n52.sta.awiingestor.model.sta.ObservedProperty;
import org.n52.sta.awiingestor.model.sta.Sensor;
import org.n52.sta.awiingestor.model.sta.Thing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
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

    private final ObjectMapper mapper;
    private WebClient client;

    public StaAPI(ObjectMapper mapper) {
        client = WebClient.builder()
            .baseUrl(APIConfig.STA_API_ROOT)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .codecs(clientCodecConfigurer -> {
                clientCodecConfigurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024);
                clientCodecConfigurer.defaultCodecs().jackson2JsonDecoder(
                    new Jackson2JsonDecoder(mapper, MediaType.APPLICATION_JSON));
                clientCodecConfigurer.defaultCodecs().jackson2JsonEncoder(
                    new Jackson2JsonEncoder(mapper, MediaType.APPLICATION_JSON));

            })
            .build();
        this.mapper = mapper;
    }

    public void postThing(Thing thing) {
        client.post()
            .uri("/Things")
            .body(BodyInserters.fromPublisher(Mono.just(thing), Thing.class))
            .retrieve()
            .onStatus(e -> e.value() == 409, e -> {
                LOGGER.trace("skipping thing {} due to conflict!", thing.getName());
                return Mono.empty();
            })
            .bodyToMono(String.class)
            .block();
    }

    public void postDatastream(Datastream datastream) {
        client.post()
            .uri("/Datastreams")
            .body(BodyInserters.fromPublisher(Mono.just(datastream), Datastream.class))
            .retrieve()
            .onStatus(e -> e.value() == 409, e -> {
                LOGGER.trace("skipping datastream {} due to conflict!", datastream.getName());
                return Mono.empty();
            })
            .bodyToMono(String.class)
            .block();
    }

    public void postSensor(Sensor sensor) {
        client.post()
            .uri("/Sensors")
            .body(BodyInserters.fromPublisher(Mono.just(sensor), Sensor.class))
            .retrieve()
            .onStatus(e -> e.value() == 409, e -> {
                LOGGER.trace("skipping sensor {} due to conflict!", sensor.getName());
                return Mono.empty();
            })
            .bodyToMono(String.class)
            .block();
    }

    public void postObservedProperty(ObservedProperty obsProp) {
        String response = client.post()
            .uri("/ObservedProperties")
            .body(BodyInserters.fromPublisher(Mono.just(obsProp), ObservedProperty.class))
            .retrieve()
            .onStatus(e -> e.value() == 409, e -> {
                LOGGER.trace("skipping obsprop {} due to conflict!", obsProp.getName());
                return Mono.empty();
            })
            .bodyToMono(String.class)
            .block();
    }

    public void postFeatureOfInterest(FeatureOfInterest foi) {
        String response = client.post()
            .uri("/FeaturesOfInterest")
            .body(BodyInserters.fromPublisher(Mono.just(foi), FeatureOfInterest.class))
            .retrieve()
            .onStatus(e -> e.value() == 409, e -> {
                LOGGER.trace("skipping foi {} due to conflict!", foi.getName());
                return Mono.empty();
            })
            .bodyToMono(String.class)
            .block();
    }
}
