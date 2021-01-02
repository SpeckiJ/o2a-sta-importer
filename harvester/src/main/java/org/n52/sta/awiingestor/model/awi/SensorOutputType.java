
package org.n52.sta.awiingestor.model.awi;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.node.ObjectNode;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "uuid",
    "lastModified",
    "vocabularyID",
    "vocableValue",
    "generalName",
    "description",
    "systemName",
    "vocableGroup",
    "id"
})
public class SensorOutputType {

    @JsonProperty("uuid")
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
    private ObjectNode vocableGroup;
    @JsonProperty("id")
    private Integer id;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("uuid")
    public String getUuid() {
        return uuid;
    }

    @JsonProperty("uuid")
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @JsonProperty("lastModified")
    public String getLastModified() {
        return lastModified;
    }

    @JsonProperty("lastModified")
    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    @JsonProperty("vocabularyID")
    public String getVocabularyID() {
        return vocabularyID;
    }

    @JsonProperty("vocabularyID")
    public void setVocabularyID(String vocabularyID) {
        this.vocabularyID = vocabularyID;
    }

    @JsonProperty("vocableValue")
    public String getVocableValue() {
        return vocableValue;
    }

    @JsonProperty("vocableValue")
    public void setVocableValue(String vocableValue) {
        this.vocableValue = vocableValue;
    }

    @JsonProperty("generalName")
    public String getGeneralName() {
        return generalName;
    }

    @JsonProperty("generalName")
    public void setGeneralName(String generalName) {
        this.generalName = generalName;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("systemName")
    public String getSystemName() {
        return systemName;
    }

    @JsonProperty("systemName")
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    @JsonProperty("vocableGroup")
    public ObjectNode getVocableGroup() {
        return vocableGroup;
    }

    @JsonProperty("vocableGroup")
    public void setVocableGroup(ObjectNode vocableGroup) {
        this.vocableGroup = vocableGroup;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
