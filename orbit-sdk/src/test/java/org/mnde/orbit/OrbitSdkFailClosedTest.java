package org.mnde.orbit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrbitSdkFailClosedTest {

    @Test
    void parseAndValidateThrowsParseExceptionOnInvalidJson() {
        assertThrows(OrbitParseException.class, () -> Orbit.parseAndValidate("{"));
    }

    @Test
    void parseAndValidateThrowsValidationExceptionOnSemanticViolation() {
        String json = "{\"orbit\":\"2.0\",\"id\":\"abc\",\"action\":\"open\",\"payload\":{\"url\":\"https://example.com\"}}";
        assertThrows(OrbitValidationException.class, () -> Orbit.parseAndValidate(json));
    }
}
