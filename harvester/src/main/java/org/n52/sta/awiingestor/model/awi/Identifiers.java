
package org.n52.sta.awiingestor.model.awi;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "SENSORWEB_CONTACT_ID"
})
public class Identifiers {

    @JsonProperty("SENSORWEB_CONTACT_ID")
    private String sENSORWEBCONTACTID;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("SENSORWEB_CONTACT_ID")
    public String getSENSORWEBCONTACTID() {
        return sENSORWEBCONTACTID;
    }

    @JsonProperty("SENSORWEB_CONTACT_ID")
    public void setSENSORWEBCONTACTID(String sENSORWEBCONTACTID) {
        this.sENSORWEBCONTACTID = sENSORWEBCONTACTID;
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
