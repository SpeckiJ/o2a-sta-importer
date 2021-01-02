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
                       "code",
                       "logicalCode",
                       "sensor",
                       "unit",
                       "platformId",
                       "firstDate",
                       "lastDate",
                       "lastValue",
                       "lastQualityFlag",
                       "source"
                   })
public class O2ASensor {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("code")
    private String code;
    @JsonProperty("logicalCode")
    private String logicalCode;
    @JsonProperty("sensor")
    private String sensor;
    @JsonProperty("unit")
    private String unit;
    @JsonProperty("platformId")
    private Integer platformId;
    @JsonProperty("firstDate")
    private String firstDate;
    @JsonProperty("lastDate")
    private String lastDate;
    @JsonProperty("lastValue")
    private Double lastValue;
    @JsonProperty("lastQualityFlag")
    private Integer lastQualityFlag;
    @JsonProperty("source")
    private String source;
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

    @JsonProperty("logicalCode")
    public String getLogicalCode() {
        return logicalCode;
    }

    @JsonProperty("logicalCode")
    public void setLogicalCode(String logicalCode) {
        this.logicalCode = logicalCode;
    }

    @JsonProperty("sensor")
    public String getSensor() {
        return sensor;
    }

    @JsonProperty("sensor")
    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    @JsonProperty("unit")
    public String getUnit() {
        return unit;
    }

    @JsonProperty("unit")
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @JsonProperty("platformId")
    public Integer getPlatformId() {
        return platformId;
    }

    @JsonProperty("platformId")
    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    @JsonProperty("firstDate")
    public String getFirstDate() {
        return firstDate;
    }

    @JsonProperty("firstDate")
    public void setFirstDate(String firstDate) {
        this.firstDate = firstDate;
    }

    @JsonProperty("lastDate")
    public String getLastDate() {
        return lastDate;
    }

    @JsonProperty("lastDate")
    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    @JsonProperty("lastValue")
    public Double getLastValue() {
        return lastValue;
    }

    @JsonProperty("lastValue")
    public void setLastValue(Double lastValue) {
        this.lastValue = lastValue;
    }

    @JsonProperty("lastQualityFlag")
    public Integer getLastQualityFlag() {
        return lastQualityFlag;
    }

    @JsonProperty("lastQualityFlag")
    public void setLastQualityFlag(Integer lastQualityFlag) {
        this.lastQualityFlag = lastQualityFlag;
    }

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
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
