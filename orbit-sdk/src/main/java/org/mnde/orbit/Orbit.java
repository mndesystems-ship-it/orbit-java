package org.mnde.orbit;

/**
 * Orbit Java SDK — thin convenience wrapper over orbit-core.
 */
public final class Orbit {

    private Orbit() {}

    public static OrbitMessage parseAndValidate(String json) {
        OrbitMessage msg = OrbitParser.parse(json);
        OrbitValidator.validate(msg);
        return msg;
    }
}
