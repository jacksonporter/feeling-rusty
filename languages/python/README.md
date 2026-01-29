# feeling-rusty (Python)

Python bindings for the feeling-rusty Rust library (UniFFI).

**In CI:** The bindings workflow generates Python code and builds a wheel in
`languages/python`. The package is assembled from the `bindings-python` and
`lib-*` artifacts.

**Local:** Generate bindings with `mise run bindings-darwin-arm64` (or your
platform), then copy `out/darwin-arm64/*` into `src/feeling_rusty/` and the
matching `.so`/`.dylib`/`.dll` into `src/feeling_rusty/`. Run `uv build` in this
directory.
