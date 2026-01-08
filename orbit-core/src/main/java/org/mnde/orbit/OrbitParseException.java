package org.mnde.orbit;

public final class OrbitParseException extends RuntimeException {

    public OrbitParseException(String message) {
        super(message);
    }

    public OrbitParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
