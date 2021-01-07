/*
 * Copyright (C) 2018-2021 52°North Initiative for Geospatial Open Source
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

package org.n52.sta.awiingestor.model.awi;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.n52.sta.awiingestor.model.awi.deserializers.EventDes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:j.speckamp@52north.org">Jan Speckamp</a>
 */
@JsonDeserialize(using = EventDes.class)
public interface Event {

    @JsonProperty("@uuid") String getUuid();

    @JsonProperty("@uuid") void setUuid(String uuid);

    @JsonProperty("startDate") LocalDateTime getStartDate();

    @JsonProperty("startDate") void setStartDate(LocalDateTime startDate);

    @JsonProperty("endDate") LocalDateTime getEndDate();

    @JsonProperty("endDate") void setEndDate(LocalDateTime endDate);

    @JsonProperty("label") String getLabel();

    @JsonProperty("label") void setLabel(String label);

    @JsonProperty("description") String getDescription();

    @JsonProperty("description") void setDescription(String description);

    @JsonProperty("eventType") EventType getEventType();

    @JsonProperty("eventType") void setEventType(EventType eventType);

    @JsonProperty("eventVocableOnlineResource") List<Object> getEventVocableOnlineResource();

    @JsonProperty("eventVocableOnlineResource")
    void setEventVocableOnlineResource(List<Object> eventVocableOnlineResource);

    @JsonProperty("latitude") Double getLatitude();

    @JsonProperty("latitude") void setLatitude(Double latitude);

    @JsonProperty("longitude") Double getLongitude();

    @JsonProperty("longitude") void setLongitude(Double longitude);

    @JsonProperty("elevationInMeter") Object getElevationInMeter();

    @JsonProperty("elevationInMeter") void setElevationInMeter(Object elevationInMeter);

    @JsonProperty("additionalEventItemRelation") List<Object> getAdditionalEventItemRelation();

    @JsonProperty("additionalEventItemRelation")
    void setAdditionalEventItemRelation(List<Object> additionalEventItemRelation);

    @JsonProperty("id") Integer getId();

    @JsonProperty("id") void setId(Integer id);

    @JsonAnyGetter Map<String, Object> getAdditionalProperties();

    @JsonAnySetter void setAdditionalProperty(String name, Object value);
}
