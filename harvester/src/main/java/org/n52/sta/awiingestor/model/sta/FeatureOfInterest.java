package org.n52.sta.awiingestor.model.sta;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.n52.sta.awiingestor.JsonConstants;

public class FeatureOfInterest extends EncodedEntity {

    private Feature feature;

    @JsonGetter(JsonConstants.FEATURE)
    public Feature getFeature() {
        return this.feature;
    }

    @JsonSetter(JsonConstants.FEATURE)
    public void setFeature(Feature feature) {
        this.feature = feature;
    }
}
