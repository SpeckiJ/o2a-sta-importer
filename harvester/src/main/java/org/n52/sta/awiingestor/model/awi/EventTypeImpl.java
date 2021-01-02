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

package org.n52.sta.awiingestor.model.awi;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
                       "@uuid",
                       "lastModified",
                       "vocabularyID",
                       "vocableValue",
                       "generalName",
                       "description",
                       "systemName",
                       "vocableGroup",
                       "id"
                   })
@JsonDeserialize
public class EventTypeImpl implements EventType {

    @JsonProperty("@uuid")
    private String uuid;
    @JsonProperty("lastModified")
    private String lastModified;
    @JsonProperty("vocabularyID")
    private String vocabularyID;
    @JsonProperty("vocableValue")
    private String vocableValue;
    @JsonProperty("generalName")
    private String generalName;
    @JsonProperty("description")
    private String description;
    @JsonProperty("systemName")
    private String systemName;
    @JsonProperty("vocableGroup")
    private VocableGroup vocableGroup;
    @JsonProperty("id")
    private Integer id;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @Override @JsonProperty("@uuid")
    public String getUuid() {
        return uuid;
    }

    @Override @JsonProperty("@uuid")
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override @JsonProperty("lastModified")
    public String getLastModified() {
        return lastModified;
    }

    @Override @JsonProperty("lastModified")
    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    @Override @JsonProperty("vocabularyID")
    public String getVocabularyID() {
        return vocabularyID;
    }

    @Override @JsonProperty("vocabularyID")
    public void setVocabularyID(String vocabularyID) {
        this.vocabularyID = vocabularyID;
    }

    @Override @JsonProperty("vocableValue")
    public String getVocableValue() {
        return vocableValue;
    }

    @Override @JsonProperty("vocableValue")
    public void setVocableValue(String vocableValue) {
        this.vocableValue = vocableValue;
    }

    @Override @JsonProperty("generalName")
    public String getGeneralName() {
        return generalName;
    }

    @Override @JsonProperty("generalName")
    public void setGeneralName(String generalName) {
        this.generalName = generalName;
    }

    @Override @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @Override @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Override @JsonProperty("systemName")
    public String getSystemName() {
        return systemName;
    }

    @Override @JsonProperty("systemName")
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    @Override @JsonProperty("vocableGroup")
    public VocableGroup getVocableGroup() {
        return vocableGroup;
    }

    @Override @JsonProperty("vocableGroup")
    public void setVocableGroup(VocableGroup vocableGroup) {
        this.vocableGroup = vocableGroup;
    }

    @Override @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @Override @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @Override @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @Override @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
