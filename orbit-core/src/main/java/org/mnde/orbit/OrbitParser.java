package org.mnde.orbit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Iterator;
import java.util.Set;

public final class OrbitParser {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final Set<String> ALLOWED_KEYS =
            Set.of("orbit", "id", "action", "payload");

    private OrbitParser() {}

    public static OrbitMessage parse(String json) {
        if (json == null) {
            throw new OrbitParseException("JSON input is null.");
        }

        JsonNode root;
        try {
            root = MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            throw new OrbitParseException("Invalid JSON.", e);
        }

        if (!root.isObject()) {
            throw new OrbitParseException("Root must be a JSON object.");
        }

        Iterator<String> names = root.fieldNames();
        while (names.hasNext()) {
            String name = names.next();
            if (!ALLOWED_KEYS.contains(name)) {
                throw new OrbitParseException("Unknown top-level field: " + name);
            }
        }

        JsonNode orbit = root.get("orbit");
        JsonNode id = root.get("id");
        JsonNode action = root.get("action");
        JsonNode payload = root.get("payload");

        if (orbit == null || !orbit.isTextual())
            throw new OrbitParseException("'orbit' must be a string.");

        if (id == null || !id.isTextual())
            throw new OrbitParseException("'id' must be a string.");

        if (action == null || !action.isTextual())
            throw new OrbitParseException("'action' must be a string.");

        if (payload == null || !payload.isObject())
            throw new OrbitParseException("'payload' must be an object.");

        return new OrbitMessage(
                orbit.asText(),
                id.asText(),
                action.asText(),
                payload
        );
    }
}
