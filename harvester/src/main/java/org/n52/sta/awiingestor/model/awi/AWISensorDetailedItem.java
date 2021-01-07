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
                       "uuid",
                       "lastModified",
                       "shortName",
                       "longName",
                       "description",
                       "serialNumber",
                       "manufacturer",
                       "operationInstructions",
                       "model",
                       "inventoryNumber",
                       "urn",
                       "citation",
                       "itemStatus",
                       "childItem",
                       "customFieldRoleItem",
                       "eventItem",
                       "contactRoleItem",
                       "measurementPropertyItem",
                       //"sensorOutput_Item",
                       "referenceFrame",
                       "itemCollection_item",
                       "onlineResourceRoleItem",
                       "ID",
                       "version",
                       "subItemType",
                       "id",
                       "parentItemID",
                       "parentItemLongName"
                   })
public class AWISensorDetailedItem {

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("shortName")
    private String shortName;

    @JsonProperty("longName")
    private String longName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("eventItem")
    private List<EventItem> eventItem = null;

    @JsonProperty("childItem")
    private List<AWISensorDetailedItem> childItem = null;

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

    @JsonProperty("shortName")
    public String getShortName() {
        return shortName;
    }

    @JsonProperty("shortName")
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @JsonProperty("longName")
    public String getLongName() {
        return longName;
    }

    @JsonProperty("longName")
    public void setLongName(String longName) {
        this.longName = longName;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("eventItem")
    public List<EventItem> getEventItem() {
        return eventItem;
    }

    @JsonProperty("eventItem")
    public void setEventItem(List<EventItem> eventItem) {
        this.eventItem = eventItem;
    }

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("childItem")
    public List<AWISensorDetailedItem> getChildItem() {
        return childItem;
    }

    @JsonProperty("childItem")
    public void setChildItem(List<AWISensorDetailedItem> childItem) {
        this.childItem = childItem;
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
