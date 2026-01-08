package org.mnde.orbit.cli;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.mnde.orbit.OrbitValidationResult;
import org.mnde.orbit.OrbitValidator;

import java.io.File;
import java.util.Map;

public final class Main {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) {
        // Usage check
        if (args.length != 2 || !"validate".equals(args[0])) {
            usage();
        }

        String filePath = args[1];

        // Read + parse JSON (fail-fast on error)
        final Object input;
        try {
            input = MAPPER.readValue(new File(filePath), Map.class);
        } catch (Exception e) {
            error("Invalid JSON or unreadable file");
            return; // unreachable, but satisfies compiler
        }

        // Validate
        OrbitValidationResult result = OrbitValidator.validate(input);

        if (result.isValid()) {
            System.out.println("VALID");
            System.exit(0);
        } else {
            System.err.println("INVALID: " + result.getCode());
            System.exit(1);
        }
    }

    private static void usage() {
        System.err.println("Usage: orbit validate <file.json>");
        System.exit(2);
    }

    private static void error(String message) {
        System.err.println(message);
        System.exit(2);
    }
}
