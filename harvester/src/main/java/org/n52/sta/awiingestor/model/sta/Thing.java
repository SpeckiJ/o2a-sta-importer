package org.n52.sta.awiingestor.model.sta;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Thing extends DescribedEntity {

    @JsonIgnore
    public Thing getReference() {
        Thing thing = new Thing();
        thing.setId(this.getId());
        return thing;
    }

}
