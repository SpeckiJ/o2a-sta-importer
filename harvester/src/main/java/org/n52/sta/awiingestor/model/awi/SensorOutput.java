
package org.n52.sta.awiingestor.model.awi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "uuid",
    "unitOfMeasurement",
    "name",
    "sensorOutputType",
    "measurementPropertySensorOutputs",
    "shortname",
    "comment",
    "id"
})
public class SensorOutput {

    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("unitOfMeasurement")
    private String unitOfMeasurement;
    @JsonProperty("name")
    private String name;
    @JsonProperty("sensorOutputType")
    private String sensorOutputType;
    @JsonProperty("measurementPropertySensorOutputs")
    private List<Object> measurementPropertySensorOutputs = null;
    @JsonProperty("shortname")
    private String shortname;
    @JsonProperty("comment")
    private String comment;
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

    @JsonProperty("unitOfMeasurement")
    public String getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    @JsonProperty("unitOfMeasurement")
    public void setUnitOfMeasurement(String unitOfMeasurement) {
        this.unitOfMeasurement = unitOfMeasurement;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("sensorOutputType")
    public String getSensorOutputType() {
        return sensorOutputType;
    }

    @JsonProperty("sensorOutputType")
    public void setSensorOutputType(String sensorOutputType) {
        this.sensorOutputType = sensorOutputType;
    }

    @JsonProperty("measurementPropertySensorOutputs")
    public List<Object> getMeasurementPropertySensorOutputs() {
        return measurementPropertySensorOutputs;
    }

    @JsonProperty("measurementPropertySensorOutputs")
    public void setMeasurementPropertySensorOutputs(List<Object> measurementPropertySensorOutputs) {
        this.measurementPropertySensorOutputs = measurementPropertySensorOutputs;
    }

    @JsonProperty("shortname")
    public String getShortname() {
        return shortname;
    }

    @JsonProperty("shortname")
    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    @JsonProperty("comment")
    public void setComment(String comment) {
        this.comment = comment;
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
