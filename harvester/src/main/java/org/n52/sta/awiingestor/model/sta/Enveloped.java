package org.n52.sta.awiingestor.model.sta;

import org.locationtech.jts.geom.Envelope;

public interface Enveloped {
    Envelope getEnvelope();
}
