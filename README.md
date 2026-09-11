# KOTH

Updated Paper 26.2 build of Benzimmer's abandoned KOTH plugin.

Original plugin: KOTH v6.1 by benzimmer123. This repo is a source recode that runs on current Paper.

## Fix

v6.1 crashes on Paper 26.2 (`XMaterial` static init) whenever a player interacts. Minecraft version `26.2` is not a `1.x` string, so the old compatibility enum never finishes loading. This build replaces that enum with the Paper `Material` API.

## Run

- Paper **26.2**
- Java **25**
- Drop `target/KOTH.jar` in `plugins/`
- Keep your existing `plugins/KOTH` data folder

## Build

```bash
mvn -DskipTests package
```

Produces `target/KOTH.jar`.
