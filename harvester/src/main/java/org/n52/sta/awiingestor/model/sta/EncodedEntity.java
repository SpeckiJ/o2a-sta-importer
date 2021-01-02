package org.n52.sta.awiingestor.model.sta;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.n52.sta.awiingestor.JsonConstants;

public class EncodedEntity extends DescribedEntity {

    private String encodingType;

    @JsonGetter(JsonConstants.ENCODING_TYPE)
    public String getEncodingType() {
        return this.encodingType;
    }

    @JsonSetter(JsonConstants.ENCODING_TYPE)
    public void setEncodingType(String encodingType) {
        this.encodingType = encodingType;
    }
}
