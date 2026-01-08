package org.mnde.orbit;

import com.fasterxml.jackson.databind.JsonNode;

public record OrbitMessage(
        String orbit,
        String id,
        String action,
        JsonNode payload
) {}

