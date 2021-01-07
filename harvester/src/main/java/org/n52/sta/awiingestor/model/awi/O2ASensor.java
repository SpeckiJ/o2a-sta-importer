package org.n52.sta.awiingestor.model.awi;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
                       "id",
                       "sensor",
                       "platformId",
                   })
public class O2ASensor {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("code")
    private String code;

    @JsonProperty("sensor")
    private String sensor;

    @JsonProperty("platformId")
    private Integer platformId;

    @JsonProperty("unit")
    private String unit;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonIgnore
    private AWISensorMetadata metadata;

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("sensor")
    public String getSensor() {
        return sensor;
    }

    @JsonProperty("sensor")
    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    @JsonProperty("platformId")
    public Integer getPlatformId() {
        return platformId;
    }

    @JsonProperty("platformId")
    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    @JsonProperty("unit")
    public String getUnit() {
        return unit;
    }

    @JsonProperty("unit")
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public AWISensorMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(AWISensorMetadata metadata) {
        this.metadata = metadata;
    }
}
