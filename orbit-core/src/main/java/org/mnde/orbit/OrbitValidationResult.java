package org.mnde.orbit;

public final class OrbitValidationResult {

    private final boolean valid;
    private final OrbitErrorCode code;

    private OrbitValidationResult(boolean valid, OrbitErrorCode code) {
        this.valid = valid;
        this.code = code;
    }

    public static OrbitValidationResult valid() {
        return new OrbitValidationResult(true, null);
    }

    public static OrbitValidationResult invalid(OrbitErrorCode code) {
        return new OrbitValidationResult(false, code);
    }

    public boolean isValid() {
        return valid;
    }

    public OrbitErrorCode getCode() {
        return code;
    }
}
