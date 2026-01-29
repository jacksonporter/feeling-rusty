# feeling-rusty (Swift)

Swift bindings for the feeling-rusty Rust library (UniFFI).

**In CI:** The bindings workflow generates Swift code and assembles a single Swift
package from the `bindings-swift` artifact. The package is source-only; you supply
the native library for your platform.

**Using the package:** Download the `package-swift` artifact (or clone and copy
generated sources into `languages/swift`). Create `Libraries/` and place your
platform’s native library in it: `libfeeling_rusty.dylib` (macOS), `libfeeling_rusty.so`
(Linux), or `feeling_rusty.dll` (Windows). Obtain the lib from the workflow
`lib-*` artifacts or build from source. Then run `swift build` in this directory.

**Local:** Generate bindings with `cargo run --features cli --bin uniffi-bindgen generate --library target/<target>/release/libfeeling_rusty.dylib --language swift --out-dir out`. Copy `out/*.swift` to `Sources/FeelingRusty/`, `out/*.h` and `out/*.modulemap` (as `module.modulemap`) to `Sources/FeelingRustyFFI/`, and the matching `.dylib`/`.so`/`.dll` into `Libraries/`. Run `swift build` in this directory.
