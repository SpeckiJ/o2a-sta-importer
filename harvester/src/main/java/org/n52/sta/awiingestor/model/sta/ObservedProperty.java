package org.n52.sta.awiingestor.model.sta;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.n52.sta.awiingestor.JsonConstants;

public class ObservedProperty extends DescribedEntity {

    private String definition;

    public ObservedProperty(String id, String name, String definition) {
        this(id, name, name, definition);
    }

    public ObservedProperty(String id, String name, String description, String definition) {
        super(id, name, description);
        this.definition = definition;
    }

    public ObservedProperty() {
    }

    @JsonGetter(JsonConstants.DEFINITION)
    public String getDefinition() {
        return definition;
    }

    @JsonSetter(JsonConstants.DEFINITION)
    public void setDefinition(String definition) {
        this.definition = definition;
    }

    @JsonIgnore
    public ObservedProperty getReference() {
        ObservedProperty obsProp = new ObservedProperty();
        obsProp.setId(this.getId());
        return obsProp;
    }

}
