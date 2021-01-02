package org.n52.sta.awiingestor.model.sta;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.n52.sta.awiingestor.JsonConstants;

public class Sensor extends EncodedEntity {

    private String metadata;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    @JsonGetter(JsonConstants.METADATA)
    public String getMetadata() {
        return metadata;
    }

    @JsonSetter(JsonConstants.METADATA)
    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }
}
