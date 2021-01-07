package org.n52.sta.awiingestor.model.awi;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.n52.sta.awiingestor.model.awi.deserializers.LDTDeserialiser;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
                       "@uuid",
                       "startDate",
                       "endDate",
                       "label",
                       "description",
                       "eventType",
                       "eventVocableOnlineResource",
                       "latitude",
                       "longitude",
                       "elevationInMeter",
                       "additionalEventItemRelation",
                       "id"
                   })
@JsonDeserialize
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "@uuid")
public class EventImpl implements Event {

    @JsonProperty("@uuid")
    private String uuid;

    @JsonDeserialize(using = LDTDeserialiser.class)
    @JsonProperty("startDate")
    private LocalDateTime startDate;

    @JsonDeserialize(using = LDTDeserialiser.class)
    @JsonProperty("endDate")
    private LocalDateTime endDate;

    @JsonProperty("label")
    private String label;

    @JsonProperty("description")
    private String description;
    @JsonProperty("eventType")
    private EventType eventType;
    @JsonProperty("eventVocableOnlineResource")
    private List<Object> eventVocableOnlineResource = null;
    @JsonProperty("latitude")
    private Double latitude = 0d;
    @JsonProperty("longitude")
    private Double longitude = 0d;
    @JsonProperty("elevationInMeter")
    private Object elevationInMeter;
    @JsonProperty("additionalEventItemRelation")
    private List<Object> additionalEventItemRelation = null;
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

    @Override @JsonProperty("startDate")
    public LocalDateTime getStartDate() {
        return startDate;
    }

    @Override @JsonProperty("startDate")
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    @Override @JsonProperty("endDate")
    public LocalDateTime getEndDate() {
        return endDate;
    }

    @Override @JsonProperty("endDate")
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    @Override @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    @Override @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @Override @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @Override @JsonProperty("eventType")
    public EventType getEventType() {
        return eventType;
    }

    @Override @JsonProperty("eventType")
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Override @JsonProperty("eventVocableOnlineResource")
    public List<Object> getEventVocableOnlineResource() {
        return eventVocableOnlineResource;
    }

    @Override @JsonProperty("eventVocableOnlineResource")
    public void setEventVocableOnlineResource(List<Object> eventVocableOnlineResource) {
        this.eventVocableOnlineResource = eventVocableOnlineResource;
    }

    @Override @JsonProperty("latitude")
    public Double getLatitude() {
        return latitude;
    }

    @Override @JsonProperty("latitude")
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override @JsonProperty("longitude")
    public Double getLongitude() {
        return longitude;
    }

    @Override @JsonProperty("longitude")
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override @JsonProperty("elevationInMeter")
    public Object getElevationInMeter() {
        return elevationInMeter;
    }

    @Override @JsonProperty("elevationInMeter")
    public void setElevationInMeter(Object elevationInMeter) {
        this.elevationInMeter = elevationInMeter;
    }

    @Override @JsonProperty("additionalEventItemRelation")
    public List<Object> getAdditionalEventItemRelation() {
        return additionalEventItemRelation;
    }

    @Override @JsonProperty("additionalEventItemRelation")
    public void setAdditionalEventItemRelation(List<Object> additionalEventItemRelation) {
        this.additionalEventItemRelation = additionalEventItemRelation;
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
