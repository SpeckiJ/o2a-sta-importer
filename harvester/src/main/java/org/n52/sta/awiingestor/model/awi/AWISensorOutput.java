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
                       "uuid",
                       "unitOfMeasurement",
                       "name",
                       "sensorOutputType",
                       "shortname",
                       "comment",
                       "id"
                   })
public class AWISensorOutput {

    @JsonProperty("uuid")
    private String uuid;
    @JsonProperty("unitOfMeasurement")
    private UnitOfMeasurement unitOfMeasurement;
    @JsonProperty("name")
    private String name;
    @JsonProperty("sensorOutputType")
    private SensorOutputType sensorOutputType;
    @JsonProperty("shortname")
    private String shortname;
    @JsonProperty("comment")
    private Object comment;
    @JsonProperty("id")
    private Integer id;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    private String sourceUrl;

    @JsonProperty("uuid")
    public String getUuid() {
        return uuid;
    }

    @JsonProperty("uuid")
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @JsonProperty("unitOfMeasurement")
    public UnitOfMeasurement getUnitOfMeasurement() {
        return unitOfMeasurement;
    }

    @JsonProperty("unitOfMeasurement")
    public void setUnitOfMeasurement(UnitOfMeasurement unitOfMeasurement) {
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
    public SensorOutputType getSensorOutputType() {
        return sensorOutputType;
    }

    @JsonProperty("sensorOutputType")
    public void setSensorOutputType(SensorOutputType sensorOutputType) {
        this.sensorOutputType = sensorOutputType;
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
    public Object getComment() {
        return comment;
    }

    @JsonProperty("comment")
    public void setComment(Object comment) {
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

    public String getSourceUrl() {
        return sourceUrl;
    }

    public AWISensorOutput setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
        return this;
    }
}
