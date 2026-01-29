# feeling-rusty (Ruby)

Ruby bindings for the feeling-rusty Rust library (UniFFI).

**In CI:** The bindings workflow generates Ruby code and builds a gem in
`languages/ruby`. The package is assembled from the `bindings-ruby` and
`lib-*` artifacts. One platform-specific gem is produced per runner.

**Local:** Generate bindings with `mise run bindings-darwin-arm64` (or your
platform), then copy `out/feeling_rusty.rb` into `lib/` and the matching
`.so`/`.dylib`/`.dll` into `lib/`. Patch `lib/feeling_rusty.rb` so
`ffi_lib 'feeling_rusty'` loads from `__dir__`, then run `gem build feeling_rusty.gemspec`.
