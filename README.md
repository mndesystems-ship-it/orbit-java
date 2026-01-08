# Orbit Protocol – Java Reference Validator & CLI

This repository contains the **official Java reference implementation** of **Orbit Protocol v1.0**.

It is intentionally minimal and strict. The Java implementation is **behavior‑locked** to the JavaScript reference implementation published as `@mnde/orbit-validator`.

If Java and JavaScript ever disagree, that is a bug.

---

## What this repository contains

This is a multi‑module Maven project:

```
orbit-java/
├── orbit-core/   # Protocol validator (authoritative logic)
├── orbit-sdk/    # Java SDK wrapper
├── orbit-cli/    # Command-line interface
└── pom.xml
```

### orbit-core
- Pure validator
- Fail‑closed
- Deterministic
- No execution
- No side effects

### orbit-cli
- Thin wrapper around the validator
- Mirrors the JS CLI exactly

---

## Protocol version

- **Orbit Protocol v1.0**
- Version is locked
- Behavior is frozen

Future protocol versions will be released explicitly.

---

## CLI Usage

After building, run:

```bash
orbit validate <file.json>
```

### Output

Valid intent:
```
VALID
```

Invalid intent:
```
INVALID: ERR_OPEN_URL_REQUIRED
```

### Exit codes

| Code | Meaning |
|----|--------|
| 0 | Valid Orbit v1.0 intent |
| 1 | Invalid intent |
| 2 | Usage, file, or JSON error |

No extra output. No stack traces.

---

## Programmatic Usage (Java)

```java
import org.mnde.orbit.OrbitValidator;
import org.mnde.orbit.OrbitValidationResult;

import java.util.Map;

Map<String, Object> intent = Map.of(
    "orbit", "1.0",
    "id", "example",
    "action", "open",
    "payload", Map.of("url", "https://example.com")
);

OrbitValidationResult result = OrbitValidator.validate(intent);

if (result.isValid()) {
    System.out.println("Valid intent");
} else {
    System.out.println(result.getCode());
}
```

---

## Validation Rules (v1.0)

Required fields:
- `orbit` → must be exactly `"1.0"`
- `id` → non‑empty string
- `action` → allowed vocabulary
- `payload` → object

### Allowed actions

| Action | Requirements |
|------|-------------|
| `open` | `payload.url` must be a non‑empty string |

Unknown actions are rejected.

---

## Error Codes (locked)

These error codes are **stable** and shared across all Orbit SDKs.

| Code | Meaning |
|----|--------|
| ERR_NOT_OBJECT | Root value is not an object |
| ERR_ORBIT_VERSION | Invalid or missing protocol version |
| ERR_ID_INVALID | Missing or invalid `id` |
| ERR_ACTION_INVALID | Missing or invalid `action` |
| ERR_ACTION_UNKNOWN | Action not in vocabulary |
| ERR_PAYLOAD_INVALID | Missing or invalid `payload` |
| ERR_OPEN_URL_REQUIRED | `open` requires `payload.url` |

---

## Design guarantees

- Fail‑closed validation
- No coercion
- No guessing
- No execution
- Deterministic results

If input is invalid, it is rejected. Always.

---

## Parity statement

This Java implementation is **behavior‑identical** to the JavaScript reference:

```
@mnde/orbit-validator
```

The JS implementation is the **canonical reference** for v1.0.

---

## License

MIT

---

## Philosophy

Orbit is infrastructure.

Infrastructure should be:
- boring
- predictable
- hard to misuse

This code exists to make incorrect behavior impossible.
