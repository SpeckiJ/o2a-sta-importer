package org.n52.sta.awiingestor.model.awi;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
                       "qualityFlags",
                       "withQualityFlags",
                       "sensors",
                       "data"
                   })
public class O2AData {

    @JsonProperty("qualityFlags")
    private List<Object> qualityFlags = null;
    @JsonProperty("withQualityFlags")
    private Boolean withQualityFlags;
    @JsonProperty("sensors")
    private List<String> sensors = null;
    @JsonProperty("data")
    private List<List<String>> data = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("qualityFlags")
    public List<Object> getQualityFlags() {
        return qualityFlags;
    }

    @JsonProperty("qualityFlags")
    public void setQualityFlags(List<Object> qualityFlags) {
        this.qualityFlags = qualityFlags;
    }

    @JsonProperty("withQualityFlags")
    public Boolean getWithQualityFlags() {
        return withQualityFlags;
    }

    @JsonProperty("withQualityFlags")
    public void setWithQualityFlags(Boolean withQualityFlags) {
        this.withQualityFlags = withQualityFlags;
    }

    @JsonProperty("sensors")
    public List<String> getSensors() {
        return sensors;
    }

    @JsonProperty("sensors")
    public void setSensors(List<String> sensors) {
        this.sensors = sensors;
    }

    @JsonProperty("data")
    public List<List<String>> getData() {
        return data;
    }

    @JsonProperty("data")
    public void setData(List<List<String>> data) {
        this.data = data;
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
