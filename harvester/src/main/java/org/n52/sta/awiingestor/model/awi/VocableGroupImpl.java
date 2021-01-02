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
                       "uuid",
                       "name",
                       "id"
                   })
@JsonDeserialize
public class VocableGroupImpl implements VocableGroup {

    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("name")
    private String name;
    @JsonProperty("id")
    private Integer id;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @Override @JsonProperty("uuid")
    public String getUuid() {
        return uuid;
    }

    @Override @JsonProperty("uuid")
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override @JsonProperty("name")
    public String getName() {
        return name;
    }

    @Override @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
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
