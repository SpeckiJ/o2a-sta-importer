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

import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.reactor.ratelimiter.operator.RateLimiterOperator;
import org.n52.sta.awiingestor.model.awi.AWIDataset;
import org.n52.sta.awiingestor.model.awi.AWISensorMetadata;
import org.n52.sta.awiingestor.model.awi.O2AData;
import org.n52.sta.awiingestor.model.awi.O2APlatform;
import org.n52.sta.awiingestor.model.awi.O2ASensor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:j.speckamp@52north.org">Jan Speckamp</a>
 */
@Component
public class DataAPI {

    private final RateLimiterRegistry rateLimiterRegistry;
    private WebClient client;
    private HashMap<String, AWISensorMetadata> metadataMap = new HashMap<>();
    private Pattern keyPattern = Pattern.compile("([\\w-]+:[\\w-]+:[\\w-]+)(:[\\w-]+)+");

    public DataAPI(RateLimiterRegistry rateLimiterRegistry) {
        client = WebClient.builder()
            .baseUrl(APIConfig.DATA_API_ROOT)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .defaultHeader(HttpHeaders.ACCEPT, "application/json;charset=UTF-8")
            .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs()
                .maxInMemorySize(16 * 1024 * 1024))
            .build();
        this.rateLimiterRegistry = rateLimiterRegistry;
    }

    public O2ASensor[] getSensors(String pattern) {
        return client.get()
            .uri(uriBuilder -> uriBuilder
                .path("/sensors")
                .queryParam("pattern", pattern)
                .queryParam("withLogicalCode", false)
                .queryParam("withMetadata", false)
                .build()
            )
            .retrieve()
            .bodyToMono(O2ASensor[].class)
            .transformDeferred(RateLimiterOperator.of(
                rateLimiterRegistry.rateLimiter(APIConfig.DATA_API_RATELIMIT_NAME)))
            .block();
    }

    public O2APlatform[] getPlatforms() {
        return client.get()
            .uri(uriBuilder -> uriBuilder
                .path("/platforms")
                .build()
            )
            .retrieve()
            .bodyToMono(O2APlatform[].class)
            .transformDeferred(RateLimiterOperator.of(
                rateLimiterRegistry.rateLimiter(APIConfig.DATA_API_RATELIMIT_NAME)))
            .block();
    }

    public void augmentWithMetadata(O2ASensor sensor) throws InterruptedException {
        Matcher m = keyPattern.matcher(sensor.getCode());
        if (!m.matches() || m.groupCount() < 2) {
            throw new RuntimeException("invalid sensorCode:" + sensor.getCode());
        }
        sensor.setMetadata(metadataMap.computeIfAbsent(m.group(1), a ->
            Objects.requireNonNull(client.get()
                                       .uri(uriBuilder -> uriBuilder
                                           .path("/metadata")
                                           .queryParam("sensors", sensor.getCode())
                                           .build())
                                       .accept(MediaType.APPLICATION_JSON)
                                       .retrieve()
                                       .bodyToMono(AWISensorMetadata[].class)
                                       .onErrorContinue((err, x) -> System.out.println(err.getMessage()))
                                       .transformDeferred(RateLimiterOperator.of(rateLimiterRegistry.rateLimiter(
                                           APIConfig.DATA_API_RATELIMIT_NAME)))
                                       .block())[0])
        );
    }

    public AWIDataset[] getDataset(String sensor) {
        return client.get()
            .uri(uriBuilder -> uriBuilder
                .path("/datasets")
                .queryParam("sensors", sensor)
                .build())
            .retrieve()
            .bodyToMono(AWIDataset[].class)
            .transformDeferred(RateLimiterOperator.of(
                rateLimiterRegistry.rateLimiter(APIConfig.DATA_API_RATELIMIT_NAME)))
            .block();
    }

    public O2AData getData(String sensor, String dataset) {
        return client.get()
            .uri(uriBuilder -> uriBuilder
                .path("/data")
                .queryParam("sensors", sensor)
                .queryParam("format", MediaType.APPLICATION_JSON_VALUE)
                .queryParam("streamit", false)
                .queryParam("withQualityFlags", false)
                .queryParam("datasetIds", dataset)
                .queryParam("withLogicalCode", false)
                .build())
            .retrieve()
            .bodyToMono(O2AData.class)
            .transformDeferred(RateLimiterOperator.of(
                rateLimiterRegistry.rateLimiter(APIConfig.DATA_API_RATELIMIT_NAME)))
            .block();
    }

}
