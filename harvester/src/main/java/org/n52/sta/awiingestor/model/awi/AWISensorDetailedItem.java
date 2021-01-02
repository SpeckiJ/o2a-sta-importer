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
    @JsonProperty("lastModified")
    private String lastModified;
    @JsonProperty("shortName")
    private String shortName;
    @JsonProperty("longName")
    private String longName;
    @JsonProperty("description")
    private String description;
    @JsonProperty("serialNumber")
    private String serialNumber;
    @JsonProperty("manufacturer")
    private String manufacturer;
    @JsonProperty("operationInstructions")
    private Object operationInstructions;
    @JsonProperty("model")
    private String model;
    @JsonProperty("inventoryNumber")
    private String inventoryNumber;
    @JsonProperty("urn")
    private String urn;
    @JsonProperty("citation")
    private String citation;
    @JsonProperty("itemStatus")
    private ItemStatus itemStatus;
    @JsonProperty("childItem")
    private List<Object> childItem = null;
    @JsonProperty("customFieldRoleItem")
    private List<Object> customFieldRoleItem = null;
    @JsonProperty("eventItem")
    private List<EventItem> eventItem = null;
    @JsonProperty("contactRoleItem")
    private List<Object> contactRoleItem = null;
    @JsonProperty("measurementPropertyItem")
    private List<Object> measurementPropertyItem = null;

    //@JsonProperty("sensorOutput_Item")
    private List<SensorOutputItem> sensorOutputItem = null;
    @JsonProperty("referenceFrame")
    private Object referenceFrame;
    @JsonProperty("itemCollection_item")
    private List<ItemCollectionItem> itemCollectionItem = null;
    @JsonProperty("onlineResourceRoleItem")
    private List<Object> onlineResourceRoleItem = null;
    @JsonProperty("ID")
    private Integer iD;
    @JsonProperty("version")
    private Integer version;
    //@JsonProperty("subItemType")
    //private SubItemType subItemType;
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("parentItemID")
    private Integer parentItemID;
    @JsonProperty("parentItemLongName")
    private String parentItemLongName;
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

    @JsonProperty("serialNumber")
    public String getSerialNumber() {
        return serialNumber;
    }

    @JsonProperty("serialNumber")
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @JsonProperty("manufacturer")
    public String getManufacturer() {
        return manufacturer;
    }

    @JsonProperty("manufacturer")
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @JsonProperty("operationInstructions")
    public Object getOperationInstructions() {
        return operationInstructions;
    }

    @JsonProperty("operationInstructions")
    public void setOperationInstructions(Object operationInstructions) {
        this.operationInstructions = operationInstructions;
    }

    @JsonProperty("model")
    public String getModel() {
        return model;
    }

    @JsonProperty("model")
    public void setModel(String model) {
        this.model = model;
    }

    @JsonProperty("inventoryNumber")
    public String getInventoryNumber() {
        return inventoryNumber;
    }

    @JsonProperty("inventoryNumber")
    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    @JsonProperty("urn")
    public String getUrn() {
        return urn;
    }

    @JsonProperty("urn")
    public void setUrn(String urn) {
        this.urn = urn;
    }

    @JsonProperty("citation")
    public String getCitation() {
        return citation;
    }

    @JsonProperty("citation")
    public void setCitation(String citation) {
        this.citation = citation;
    }

    @JsonProperty("itemStatus")
    public ItemStatus getItemStatus() {
        return itemStatus;
    }

    @JsonProperty("itemStatus")
    public void setItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

    @JsonProperty("childItem")
    public List<Object> getChildItem() {
        return childItem;
    }

    @JsonProperty("childItem")
    public void setChildItem(List<Object> childItem) {
        this.childItem = childItem;
    }

    @JsonProperty("customFieldRoleItem")
    public List<Object> getCustomFieldRoleItem() {
        return customFieldRoleItem;
    }

    @JsonProperty("customFieldRoleItem")
    public void setCustomFieldRoleItem(List<Object> customFieldRoleItem) {
        this.customFieldRoleItem = customFieldRoleItem;
    }

    @JsonProperty("eventItem")
    public List<EventItem> getEventItem() {
        return eventItem;
    }

    @JsonProperty("eventItem")
    public void setEventItem(List<EventItem> eventItem) {
        this.eventItem = eventItem;
    }

    @JsonProperty("contactRoleItem")
    public List<Object> getContactRoleItem() {
        return contactRoleItem;
    }

    @JsonProperty("contactRoleItem")
    public void setContactRoleItem(List<Object> contactRoleItem) {
        this.contactRoleItem = contactRoleItem;
    }

    @JsonProperty("measurementPropertyItem")
    public List<Object> getMeasurementPropertyItem() {
        return measurementPropertyItem;
    }

    @JsonProperty("measurementPropertyItem")
    public void setMeasurementPropertyItem(List<Object> measurementPropertyItem) {
        this.measurementPropertyItem = measurementPropertyItem;
    }

    //@JsonProperty("sensorOutput_Item")
    public List<SensorOutputItem> getSensorOutputItem() {
        return sensorOutputItem;
    }

    //@JsonProperty("sensorOutput_Item")
    public void setSensorOutputItem(List<SensorOutputItem> sensorOutputItem) {
        this.sensorOutputItem = sensorOutputItem;
    }

    @JsonProperty("referenceFrame")
    public Object getReferenceFrame() {
        return referenceFrame;
    }

    @JsonProperty("referenceFrame")
    public void setReferenceFrame(Object referenceFrame) {
        this.referenceFrame = referenceFrame;
    }

    @JsonProperty("itemCollection_item")
    public List<ItemCollectionItem> getItemCollectionItem() {
        return itemCollectionItem;
    }

    @JsonProperty("itemCollection_item")
    public void setItemCollectionItem(List<ItemCollectionItem> itemCollectionItem) {
        this.itemCollectionItem = itemCollectionItem;
    }

    @JsonProperty("onlineResourceRoleItem")
    public List<Object> getOnlineResourceRoleItem() {
        return onlineResourceRoleItem;
    }

    @JsonProperty("onlineResourceRoleItem")
    public void setOnlineResourceRoleItem(List<Object> onlineResourceRoleItem) {
        this.onlineResourceRoleItem = onlineResourceRoleItem;
    }

    @JsonProperty("ID")
    public Integer getID() {
        return iD;
    }

    @JsonProperty("ID")
    public void setID(Integer iD) {
        this.iD = iD;
    }

    @JsonProperty("version")
    public Integer getVersion() {
        return version;
    }

    @JsonProperty("version")
    public void setVersion(Integer version) {
        this.version = version;
    }

    //@JsonProperty("subItemType")
    //public SubItemType getSubItemType() {
    //    return subItemType;
    //}

    //@JsonProperty("subItemType")
    //public void setSubItemType(SubItemType subItemType) {
    //    this.subItemType = subItemType;
    //}

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("parentItemID")
    public Integer getParentItemID() {
        return parentItemID;
    }

    @JsonProperty("parentItemID")
    public void setParentItemID(Integer parentItemID) {
        this.parentItemID = parentItemID;
    }

    @JsonProperty("parentItemLongName")
    public String getParentItemLongName() {
        return parentItemLongName;
    }

    @JsonProperty("parentItemLongName")
    public void setParentItemLongName(String parentItemLongName) {
        this.parentItemLongName = parentItemLongName;
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
