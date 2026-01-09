package org.mnde.orbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrbitValidatorTest {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static OrbitMessage message(String orbit, String id, String action, String url) {
        ObjectNode payload = MAPPER.createObjectNode();
        if (url != null) {
            payload.put("url", url);
        }
        return new OrbitMessage(orbit, id, action, payload);
    }

    @Test
    void validateAcceptsVersion1_0() {
        assertDoesNotThrow(() -> OrbitValidator.validate(message("1.0", "abc", "open", "https://example.com")));
    }

    @Test
    void validateRejectsUnsupportedVersion() {
        assertThrows(OrbitValidationException.class, () -> OrbitValidator.validate(message("2.0", "abc", "open", "https://example.com")));
    }

    @Test
    void validateAcceptsNonBlankId() {
        assertDoesNotThrow(() -> OrbitValidator.validate(message("1.0", "abc", "open", "https://example.com")));
    }

    @Test
    void validateRejectsBlankId() {
        assertThrows(OrbitValidationException.class, () -> OrbitValidator.validate(message("1.0", " ", "open", "https://example.com")));
    }

    @Test
    void validateAcceptsOpenAction() {
        assertDoesNotThrow(() -> OrbitValidator.validate(message("1.0", "abc", "open", "https://example.com")));
    }

    @Test
    void validateRejectsUnknownAction() {
        assertThrows(OrbitValidationException.class, () -> OrbitValidator.validate(message("1.0", "abc", "close", "https://example.com")));
    }

    @Test
    void validateAcceptsOpenWithUrl() {
        assertDoesNotThrow(() -> OrbitValidator.validate(message("1.0", "abc", "open", "https://example.com")));
    }

    @Test
    void validateRejectsOpenMissingUrl() {
        assertThrows(OrbitValidationException.class, () -> OrbitValidator.validate(message("1.0", "abc", "open", null)));
    }

    @Test
    void validateRejectsOpenNonStringUrl() {
        ObjectNode payload = MAPPER.createObjectNode();
        payload.put("url", 123);
        OrbitMessage msg = new OrbitMessage("1.0", "abc", "open", payload);
        assertThrows(OrbitValidationException.class, () -> OrbitValidator.validate(msg));
    }

    @Test
    void validateRejectsOpenBlankUrl() {
        assertThrows(OrbitValidationException.class, () -> OrbitValidator.validate(message("1.0", "abc", "open", " ")));
    }

    @Test
    void validateThrowsOnSemanticViolation() {
        assertThrows(OrbitValidationException.class, () -> OrbitValidator.validate(message("2.0", "abc", "open", "https://example.com")));
    }

    @Test
    void validateDoesNotThrowOnValidMessage() {
        assertDoesNotThrow(() -> OrbitValidator.validate(message("1.0", "abc", "open", "https://example.com")));
    }
}
