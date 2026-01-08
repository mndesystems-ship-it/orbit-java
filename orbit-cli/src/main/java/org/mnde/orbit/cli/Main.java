package org.mnde.orbit.cli;

import org.mnde.orbit.*;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Orbit CLI — reference validator.
 */
public final class Main {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: orbit <file.json>");
            System.exit(1);
        }

        String json = Files.readString(Path.of(args[0]));
        OrbitValidator.validate(OrbitParser.parse(json));
        System.out.println("VALID");
    }
}
