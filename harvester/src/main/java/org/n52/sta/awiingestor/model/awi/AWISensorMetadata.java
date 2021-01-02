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
                       "uniqueId",
                       "id",
                       "title",
                       "keywords",
                       "expeditions",
                       "parameters",
                       "persons",
                       "organisations",
                       "resources",
                       "metadata"
                   })
public class AWISensorMetadata {

    @JsonProperty("uniqueId")
    private String uniqueId;
    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("keywords")
    private Keywords keywords;
    @JsonProperty("expeditions")
    private List<Object> expeditions = null;
    @JsonProperty("parameters")
    private List<Parameter> parameters = null;
    @JsonProperty("persons")
    private Persons persons;
    @JsonProperty("organisations")
    private Organisations organisations;
    @JsonProperty("resources")
    private List<Resource> resources = null;
    @JsonProperty("metadata")
    private Metadata metadata;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("uniqueId")
    public String getUniqueId() {
        return uniqueId;
    }

    @JsonProperty("uniqueId")
    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("keywords")
    public Keywords getKeywords() {
        return keywords;
    }

    @JsonProperty("keywords")
    public void setKeywords(Keywords keywords) {
        this.keywords = keywords;
    }

    @JsonProperty("expeditions")
    public List<Object> getExpeditions() {
        return expeditions;
    }

    @JsonProperty("expeditions")
    public void setExpeditions(List<Object> expeditions) {
        this.expeditions = expeditions;
    }

    @JsonProperty("parameters")
    public List<Parameter> getParameters() {
        return parameters;
    }

    @JsonProperty("parameters")
    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    @JsonProperty("persons")
    public Persons getPersons() {
        return persons;
    }

    @JsonProperty("persons")
    public void setPersons(Persons persons) {
        this.persons = persons;
    }

    @JsonProperty("organisations")
    public Organisations getOrganisations() {
        return organisations;
    }

    @JsonProperty("organisations")
    public void setOrganisations(Organisations organisations) {
        this.organisations = organisations;
    }

    @JsonProperty("resources")
    public List<Resource> getResources() {
        return resources;
    }

    @JsonProperty("resources")
    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    @JsonProperty("metadata")
    public Metadata getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
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
