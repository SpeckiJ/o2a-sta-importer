
package org.n52.sta.awiingestor.model.awi;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "lastValue",
    "code",
    "sensorHandle",
    "sensorLinkId",
    "platformId",
    "source",
    "parentId",
    "logicalCode",
    "sensorId",
    "lastQualityFlag",
    "unit",
    "sensorLinkCode",
    "firstDate",
    "sensor",
    "lastDate"
})
public class Metadata {

    @JsonProperty("lastValue")
    private Double lastValue;
    @JsonProperty("code")
    private String code;
    @JsonProperty("sensorHandle")
    private String sensorHandle;
    @JsonProperty("sensorLinkId")
    private String sensorLinkId;
    @JsonProperty("platformId")
    private Integer platformId;
    @JsonProperty("source")
    private String source;
    @JsonProperty("parentId")
    private String parentId;
    @JsonProperty("logicalCode")
    private String logicalCode;
    @JsonProperty("sensorId")
    private Integer sensorId;
    @JsonProperty("lastQualityFlag")
    private Integer lastQualityFlag;
    @JsonProperty("unit")
    private String unit;
    @JsonProperty("sensorLinkCode")
    private String sensorLinkCode;
    @JsonProperty("firstDate")
    private String firstDate;
    @JsonProperty("sensor")
    private String sensor;
    @JsonProperty("lastDate")
    private String lastDate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("lastValue")
    public Double getLastValue() {
        return lastValue;
    }

    @JsonProperty("lastValue")
    public void setLastValue(Double lastValue) {
        this.lastValue = lastValue;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("sensorHandle")
    public String getSensorHandle() {
        return sensorHandle;
    }

    @JsonProperty("sensorHandle")
    public void setSensorHandle(String sensorHandle) {
        this.sensorHandle = sensorHandle;
    }

    @JsonProperty("sensorLinkId")
    public String getSensorLinkId() {
        return sensorLinkId;
    }

    @JsonProperty("sensorLinkId")
    public void setSensorLinkId(String sensorLinkId) {
        this.sensorLinkId = sensorLinkId;
    }

    @JsonProperty("platformId")
    public Integer getPlatformId() {
        return platformId;
    }

    @JsonProperty("platformId")
    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("parentId")
    public String getParentId() {
        return parentId;
    }

    @JsonProperty("parentId")
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @JsonProperty("logicalCode")
    public String getLogicalCode() {
        return logicalCode;
    }

    @JsonProperty("logicalCode")
    public void setLogicalCode(String logicalCode) {
        this.logicalCode = logicalCode;
    }

    @JsonProperty("sensorId")
    public Integer getSensorId() {
        return sensorId;
    }

    @JsonProperty("sensorId")
    public void setSensorId(Integer sensorId) {
        this.sensorId = sensorId;
    }

    @JsonProperty("lastQualityFlag")
    public Integer getLastQualityFlag() {
        return lastQualityFlag;
    }

    @JsonProperty("lastQualityFlag")
    public void setLastQualityFlag(Integer lastQualityFlag) {
        this.lastQualityFlag = lastQualityFlag;
    }

    @JsonProperty("unit")
    public String getUnit() {
        return unit;
    }

    @JsonProperty("unit")
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @JsonProperty("sensorLinkCode")
    public String getSensorLinkCode() {
        return sensorLinkCode;
    }

    @JsonProperty("sensorLinkCode")
    public void setSensorLinkCode(String sensorLinkCode) {
        this.sensorLinkCode = sensorLinkCode;
    }

    @JsonProperty("firstDate")
    public String getFirstDate() {
        return firstDate;
    }

    @JsonProperty("firstDate")
    public void setFirstDate(String firstDate) {
        this.firstDate = firstDate;
    }

    @JsonProperty("sensor")
    public String getSensor() {
        return sensor;
    }

    @JsonProperty("sensor")
    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    @JsonProperty("lastDate")
    public String getLastDate() {
        return lastDate;
    }

    @JsonProperty("lastDate")
    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
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
