package org.n52.sta.awiingestor.model.sta;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.n52.sta.awiingestor.JsonConstants;

public interface Defined {
    @JsonGetter(JsonConstants.DEFINITION)
    String getDefinition();

    @JsonSetter(JsonConstants.DEFINITION)
    void setDefinition(String definition);
}
