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
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.github.resilience4j.reactor.ratelimiter.operator.RateLimiterOperator;
import org.n52.sta.awiingestor.model.awi.AWISensorDetailedItem;
import org.n52.sta.awiingestor.model.awi.AWISensorOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.tcp.TcpClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Stateful!
 *
 * @author <a href="mailto:j.speckamp@52north.org">Jan Speckamp</a>
 */
@Component
public class SensorsAPI {

    private static final Logger LOGGER = LoggerFactory.getLogger(SensorsAPI.class);

    private final RateLimiterRegistry rateLimiterRegistry;
    private WebClient client;

    private HashMap<String, Set<String>> sensorOutputURNCache = new HashMap<>();
    private HashMap<String, AWISensorOutput> sensorOutputCache = new HashMap<>();
    private HashMap<String, AWISensorDetailedItem> sensorDetailedItemCache = new HashMap<>();

    public SensorsAPI(ObjectMapper mapper, RateLimiterRegistry rateLimiterRegistry) {
        this.rateLimiterRegistry = rateLimiterRegistry;
        ConnectionProvider provider = ConnectionProvider.builder("builder")
            .maxConnections(1)
            .build();
        TcpClient tcpClient = TcpClient.create(provider);

        client = WebClient.builder()
            .baseUrl(APIConfig.SENSORS_API_ROOT)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs()
                .maxInMemorySize(16 * 1024 * 1024))
            .codecs(clientCodecConfigurer -> {
                clientCodecConfigurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024);
                clientCodecConfigurer.defaultCodecs().jackson2JsonDecoder(
                    new Jackson2JsonDecoder(mapper, MediaType.APPLICATION_JSON));
                clientCodecConfigurer.defaultCodecs().jackson2JsonEncoder(
                    new Jackson2JsonEncoder(mapper, MediaType.APPLICATION_JSON));

            })
            .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
            .build();
    }

    public Set<String> getSensorOutputURNs(String sensorLinkId) {
        return sensorOutputURNCache.computeIfAbsent(sensorLinkId, (e) -> {
            HashSet<String> urnSet = new HashSet<>();
            client.get()
                .uri("/sensorOutputs/getSensorOutputUrns/{id}", sensorLinkId)
                .retrieve()
                .bodyToMono(ArrayNode.class)
                .transformDeferred(RateLimiterOperator.of(
                    rateLimiterRegistry.rateLimiter(APIConfig.SENSORS_API_RATELIMIT_NAME)))
                .retryWhen(Retry.backoff(5, Duration.ofSeconds(2)))
                .block()
                .forEach(m -> urnSet.add(m.asText()));
            return urnSet;
        });
    }

    public AWISensorOutput getSensorOutputByURN(String urn) {
        return sensorOutputCache.computeIfAbsent(urn, e -> client
            .get()
            .uri("/sensorOutputs/getSensorOutputByUrn/{urn}", urn)
            .retrieve()
            .bodyToMono(AWISensorOutput.class)
            .transformDeferred(RateLimiterOperator.of(
                rateLimiterRegistry.rateLimiter(APIConfig.SENSORS_API_RATELIMIT_NAME)))
            .retryWhen(Retry.backoff(5, Duration.ofSeconds(2)))
            .block()
            .setSourceUrl("/sensorOutputs/getSensorOutputByUrn/" + urn));
    }

    public AWISensorDetailedItem getSensorDetailedItem(String sensorLinkId) {
        return sensorDetailedItemCache.computeIfAbsent(sensorLinkId, e -> client
            .get()
            .uri("/item/getDetailedItem/{id}?includeChildren=true", sensorLinkId)
            .retrieve()
            .onStatus(status -> !status.is2xxSuccessful(), s -> {
                System.out.println(s.rawStatusCode());
                return Mono.empty();
            })
            .bodyToMono(AWISensorDetailedItem.class)
            .onErrorContinue((err, x) -> LOGGER.error("Could not decode AWISensorDetailedItem!"))
            .transformDeferred(RateLimiterOperator.of(
                rateLimiterRegistry.rateLimiter(APIConfig.SENSORS_API_RATELIMIT_NAME)))
            // .retryWhen(Retry.backoff(5, Duration.ofSeconds(2)))
            .block());
    }

}
