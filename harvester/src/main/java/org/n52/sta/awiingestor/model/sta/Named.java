package org.n52.sta.awiingestor.model.sta;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.n52.sta.awiingestor.JsonConstants;

public interface Named {
    @JsonGetter(JsonConstants.NAME)
    String getName();

    @JsonSetter(JsonConstants.NAME)
    void setName(String name);
}
