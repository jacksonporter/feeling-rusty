# feeling-rusty

Rust library with [UniFFI](https://mozilla.github.io/uniffi-rs/) for cross-language
bindings.

## Generating bindings

1. **Build the library** (release recommended):

    ```bash
    cargo build --release
    ```

2. **Run the bindgen binary** with the `cli` feature (first time will compile the
   CLI tool). Use the cdylib that was built:
    - **macOS:** `libfeeling_rusty.dylib`
    - **Linux:** `libfeeling_rusty.so`
    - **Windows:** `feeling_rusty.dll`

    ```bash
    cargo run --features=cli --bin uniffi-bindgen generate \
      --library target/release/libfeeling_rusty.dylib \
      --language <LANGUAGE> \
      --out-dir out
    ```

    Replace `<LANGUAGE>` with one of: **kotlin**, **swift**, **python**, **ruby**.

### Examples

**Kotlin:**

```bash
cargo run --features=cli --bin uniffi-bindgen generate \
  --library target/release/libfeeling_rusty.dylib \
  --language kotlin \
  --out-dir out
```

**Swift:**

```bash
cargo run --features=cli --bin uniffi-bindgen generate \
  --library target/release/libfeeling_rusty.dylib \
  --language swift \
  --out-dir out
```

**Python:**

```bash
cargo run --features=cli --bin uniffi-bindgen generate \
  --library target/release/libfeeling_rusty.dylib \
  --language python \
  --out-dir out
```

Generated code appears under `out/`. Use the correct library extension for your
OS (`.dylib` on macOS, `.so` on Linux, `.dll` on Windows).

### All OSes and chipsets (mise tasks)

[mise](https://mise.jdx.dev/) tasks are defined in `.mise/config.toml`. You can
**build the native libraries first** (`.dylib` / `.so` / `.dll`), then **generate
language bindings later**.

**1. Build the libraries** (no bindgen):

| Task                            | Platform            |
| ------------------------------- | ------------------- |
| `mise run build-darwin-x86_64`  | macOS Intel         |
| `mise run build-darwin-arm64`   | macOS Apple Silicon |
| `mise run build-linux-x86_64`   | Linux x86_64        |
| `mise run build-linux-arm64`    | Linux arm64         |
| `mise run build-windows-x86_64` | Windows x86_64      |
| `mise run build-windows-arm64`  | Windows arm64       |
| `mise run build-all`            | All of the above    |

Libraries go under `target/<target>/release/` (e.g.
`target/aarch64-apple-darwin/release/libfeeling_rusty.dylib`).

**2. Generate bindings later** (run bindgen against an already-built library):

| Task                              | Platform            |
| --------------------------------- | ------------------- |
| `mise run bindings-darwin-x86_64` | macOS Intel         |
| `mise run bindings-darwin-arm64`  | macOS Apple Silicon |
| …                                 | …                   |
| `mise run bindings-all`           | All targets         |

Bindings go to `out/<platform>/`. Tasks use Kotlin by default; edit the task in
`.mise/config.toml` to use `--language swift`, `python`, or `ruby`.

Cross-compiling to another OS (e.g. Linux from macOS) may require a target
linker or CI runners; running each task on its native OS is most reliable.

### CI: GitHub Actions

The [UniFFI bindings](.github/workflows/bindings.yml) workflow builds libraries
and bindings for all OS/chipset/language combinations. After a run completes:

- **Artifacts:** Actions → your run → **Artifacts**. You get:
  - `lib-<platform>` — the native library per OS/arch (e.g. `lib-darwin-arm64`)
  - `bindings-<language>` — generated wrapper code (e.g. `bindings-kotlin`);
      the code is the same for all platforms; only the native lib changes

Download the bindings artifact for your language (one per language), and the
library artifact(s) for each platform you ship (e.g. macOS + Windows).

## Using the bindings

1. **Add the generated code** to your app (Kotlin/Swift/Python/Ruby). Copy the
   files from the bindings output (or from a CI artifact) into your project.

2. **Ship the native library** with your app. At runtime the bindings load the
   `.dylib` / `.so` / `.dll` for the current platform. Bundle the right library
   per target (e.g. from `lib-darwin-arm64`, `lib-windows-x86_64`).

3. **Wire it up** per language:
    - **Kotlin:** Add the generated `.kt` and the native lib; see [UniFFI
      Kotlin](https://mozilla.github.io/uniffi-rs/latest/kotlin/).
    - **Swift:** Add the generated `.swift` and the framework/dylib; see
      [UniFFI Swift](https://mozilla.github.io/uniffi-rs/latest/swift/).
    - **Python:** Use the generated module and ensure the library is on
      `LD_LIBRARY_PATH` or next to the module; see [UniFFI
      Python](https://mozilla.github.io/uniffi-rs/latest/python/).
    - **Ruby:** Similarly, require the generated code and load the native lib;
      see [UniFFI Ruby](https://mozilla.github.io/uniffi-rs/latest/ruby/).

**Optional next steps:**

- **Releases:** Add a job (or workflow) that runs on tag push and uploads
  `lib-*` and `bindings-*` artifacts to a [GitHub
  Release](https://docs.github.com/en/actions/creating-actions/creating-a-release-with-actions)
  so consumers can download them by version.
- **Packages:** Publish the bindings + library to your language’s package
  registry (e.g. PyPI, Maven, Swift Package Manager, RubyGems) using that
  language’s packaging docs and the generated artifacts.
