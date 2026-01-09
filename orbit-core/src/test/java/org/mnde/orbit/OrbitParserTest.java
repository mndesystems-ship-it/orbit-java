package org.mnde.orbit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrbitParserTest {

    private static String validJson() {
        return "{\"orbit\":\"1.0\",\"id\":\"abc\",\"action\":\"open\",\"payload\":{\"url\":\"https://example.com\"}}";
    }

    @Test
    void parseRejectsNullInput() {
        assertThrows(OrbitParseException.class, () -> OrbitParser.parse(null));
    }

    @Test
    void parseAcceptsNonNullJson() {
        OrbitMessage msg = OrbitParser.parse(validJson());
        assertEquals("1.0", msg.orbit());
    }

    @Test
    void parseRejectsInvalidJson() {
        assertThrows(OrbitParseException.class, () -> OrbitParser.parse("{"));
    }

    @Test
    void parseAcceptsValidJson() {
        assertDoesNotThrow(() -> OrbitParser.parse(validJson()));
    }

    @Test
    void parseRejectsNonObjectRoot() {
        assertThrows(OrbitParseException.class, () -> OrbitParser.parse("[]"));
    }

    @Test
    void parseAcceptsObjectRoot() {
        assertDoesNotThrow(() -> OrbitParser.parse(validJson()));
    }

    @Test
    void parseRejectsUnknownTopLevelField() {
        String json = "{\"orbit\":\"1.0\",\"id\":\"abc\",\"action\":\"open\",\"payload\":{\"url\":\"https://example.com\"},\"extra\":1}";
        assertThrows(OrbitParseException.class, () -> OrbitParser.parse(json));
    }

    @Test
    void parseAcceptsOnlyAllowedTopLevelFields() {
        assertDoesNotThrow(() -> OrbitParser.parse(validJson()));
    }

    @Test
    void parseRejectsMissingOrbit() {
        String json = "{\"id\":\"abc\",\"action\":\"open\",\"payload\":{\"url\":\"https://example.com\"}}";
        assertThrows(OrbitParseException.class, () -> OrbitParser.parse(json));
    }

    @Test
    void parseAcceptsOrbitString() {
        OrbitMessage msg = OrbitParser.parse(validJson());
        assertEquals("1.0", msg.orbit());
    }

    @Test
    void parseRejectsNonStringId() {
        String json = "{\"orbit\":\"1.0\",\"id\":1,\"action\":\"open\",\"payload\":{\"url\":\"https://example.com\"}}";
        assertThrows(OrbitParseException.class, () -> OrbitParser.parse(json));
    }

    @Test
    void parseAcceptsIdString() {
        OrbitMessage msg = OrbitParser.parse(validJson());
        assertEquals("abc", msg.id());
    }

    @Test
    void parseRejectsNonStringAction() {
        String json = "{\"orbit\":\"1.0\",\"id\":\"abc\",\"action\":1,\"payload\":{\"url\":\"https://example.com\"}}";
        assertThrows(OrbitParseException.class, () -> OrbitParser.parse(json));
    }

    @Test
    void parseAcceptsActionString() {
        OrbitMessage msg = OrbitParser.parse(validJson());
        assertEquals("open", msg.action());
    }

    @Test
    void parseRejectsNonObjectPayload() {
        String json = "{\"orbit\":\"1.0\",\"id\":\"abc\",\"action\":\"open\",\"payload\":\"x\"}";
        assertThrows(OrbitParseException.class, () -> OrbitParser.parse(json));
    }

    @Test
    void parseAcceptsPayloadObject() {
        OrbitMessage msg = OrbitParser.parse(validJson());
        assertNotNull(msg.payload());
        assertTrue(msg.payload().isObject());
    }
}
