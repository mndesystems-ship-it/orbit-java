package org.mnde.orbit;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Map;
import java.util.Set;

public final class OrbitValidator {

    private static final Set<String> ALLOWED_ACTIONS = Set.of("open");

    private OrbitValidator() {}

    public static OrbitValidationResult validate(Object input) {

        // 1. Root must be an object
        if (!(input instanceof Map)) {
            return OrbitValidationResult.invalid(
                OrbitErrorCode.ERR_NOT_OBJECT
            );
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> obj = (Map<String, Object>) input;

        // 2. Orbit version
        Object orbit = obj.get("orbit");
        if (!"1.0".equals(orbit)) {
            return OrbitValidationResult.invalid(
                OrbitErrorCode.ERR_ORBIT_VERSION
            );
        }

        // 3. ID
        Object id = obj.get("id");
        if (!(id instanceof String) || ((String) id).isEmpty()) {
            return OrbitValidationResult.invalid(
                OrbitErrorCode.ERR_ID_INVALID
            );
        }

        // 4. Action (basic)
        Object action = obj.get("action");
        if (!(action instanceof String) || ((String) action).isEmpty()) {
            return OrbitValidationResult.invalid(
                OrbitErrorCode.ERR_ACTION_INVALID
            );
        }

        String actionStr = (String) action;

        // 5. Action vocabulary
        if (!ALLOWED_ACTIONS.contains(actionStr)) {
            return OrbitValidationResult.invalid(
                OrbitErrorCode.ERR_ACTION_UNKNOWN
            );
        }

        // 6. Payload
        Object payload = obj.get("payload");
        if (!(payload instanceof Map)) {
            return OrbitValidationResult.invalid(
                OrbitErrorCode.ERR_PAYLOAD_INVALID
            );
        }

        @SuppressWarnings("unchecked")
        Map<String, Object> payloadMap = (Map<String, Object>) payload;

        // 7. Action-specific validation
        if ("open".equals(actionStr)) {
            Object url = payloadMap.get("url");
            if (!(url instanceof String) || ((String) url).isEmpty()) {
                return OrbitValidationResult.invalid(
                    OrbitErrorCode.ERR_OPEN_URL_REQUIRED
                );
            }
        }

        return OrbitValidationResult.valid();
    }
}
