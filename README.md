# orbit-sdk-java-

This document describes only the behaviors enforced by tests in this repository.

## Invariants (v1.0)
- Input JSON must be non-null and parseable.
- Root must be a JSON object.
- Top-level fields must be only: `orbit`, `id`, `action`, `payload`.
- `orbit` must exist and be a string.
- `id` must exist and be a string.
- `action` must exist and be a string.
- `payload` must exist and be a JSON object.
- `orbit` must equal `"1.0"`.
- `id` must be non-blank.
- `action` must be `"open"`.
- If `action` is `"open"`, then `payload.url` must exist, be a string, and be non-blank.

## Fail-closed guarantees
- Invalid JSON or invalid structure throws `OrbitParseException`.
- Semantic violations throw `OrbitValidationException`.
- Unknown top-level fields are rejected.

## Valid JSON example
```json
{
  "orbit": "1.0",
  "id": "abc",
  "action": "open",
  "payload": {
    "url": "https://example.com"
  }
}
```

## Invalid JSON examples
Unknown field:
```json
{
  "orbit": "1.0",
  "id": "abc",
  "action": "open",
  "payload": {
    "url": "https://example.com"
  },
  "extra": 1
}
```

Wrong version:
```json
{
  "orbit": "2.0",
  "id": "abc",
  "action": "open",
  "payload": {
    "url": "https://example.com"
  }
}
```

Missing url:
```json
{
  "orbit": "1.0",
  "id": "abc",
  "action": "open",
  "payload": {}
}
```
