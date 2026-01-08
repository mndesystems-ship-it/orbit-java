package org.mnde.orbit;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Set;

public final class OrbitValidator {

    private static final String VERSION = "1.0";
    private static final Set<String> ACTIONS = Set.of("open");

    private OrbitValidator() {}

    public static void validate(OrbitMessage msg) {
        if (!VERSION.equals(msg.orbit())) {
            throw new OrbitValidationException("Unsupported orbit version.");
        }

        if (msg.id().isBlank()) {
            throw new OrbitValidationException("id is empty.");
        }

        if (!ACTIONS.contains(msg.action())) {
            throw new OrbitValidationException("Unknown action.");
        }

        if (msg.action().equals("open")) {
            JsonNode url = msg.payload().get("url");
            if (url == null || !url.isTextual() || url.asText().isBlank()) {
                throw new OrbitValidationException("open requires payload.url");
            }
        }
    }
}
