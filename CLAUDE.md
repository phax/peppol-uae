# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

Java library implementing Peppol Tax Data Document (TDD) support for the United Arab Emirates. Multi-module Maven project published to Sonatype Central under `com.helger.peppol`.

Spec references: [TDD AE](https://docs.peppol.eu/tdd/ae/), [PINT AE](https://test-docs.peppol.eu/pint/pint-ae/).

## Modules

- `peppol-uae-testfiles` — bundled UAE TDD test XML files (entry: `PeppolUAETestFiles`).
- `peppol-uae-tdd-datatypes` — JAXB-generated UAE TDD data model (entry: `PeppolUAETDD10Marshaller`).
- `peppol-uae-tdd` — builders and validator (entries: `PeppolUAETDD10Builder`, `PeppolUAETDD10ReportedTransactionBuilder`, `PeppolUAETDDValidator`).

`peppol-uae-tdd` depends on `peppol-uae-tdd-datatypes` depends on `peppol-uae-testfiles`.

## Build & test

- Build: `mvn clean install`
- CI Java matrix: 17, 21, 25. Minimum supported: Java 17.
- Tests use JUnit 4 (inherited from `com.helger:parent-pom:3.0.5`). Each module has an `SPITest` that verifies SPI registrations — keep it passing when adding new SPI services.

## Generated code

The `peppol-uae-tdd-datatypes` module generates Java sources from XSDs at build time via `ph-jaxb-plugin`.

- XSDs: `peppol-uae-tdd-datatypes/src/main/resources/external/schemas/peppol-tdd-*.xsd`
- Binding config: `peppol-uae-tdd-datatypes/src/main/jaxb/binding.xjb`
- Catalog: `peppol-uae-tdd-datatypes/src/main/jaxb/catalog-tdd.txt`

Do not hand-edit the generated classes (anything produced under `target/generated-sources/`). To change the model, edit the XSD or `binding.xjb` and rebuild.

## Schematron versioning

Validator rules live in `peppol-uae-tdd/src/main/resources/external/schematron/`. One active `.sch` file per TDD version (e.g. `peppol-ae-tdd-1.0.2.sch`); superseded versions are moved to the `old/` subdirectory rather than deleted. When adding a new TDD version, place the new `.sch` at the top level and move the prior active file into `old/`.

## Coding conventions

- License header: Apache 2.0, Copyright `2025-2026 Philip Helger`. Required on every Java/XML source file (enforced by the parent POM's license check).
- Nullability: JSpecify annotations (`org.jspecify.annotations.NonNull`/`Nullable`). Do not introduce alternative nullability libraries.
- Collections: ph-commons types (`ICommonsList`, `CommonsArrayList`, etc.) as return types; plain `java.util` types as parameter types.
- Logging: SLF4J. Use inline string concatenation in log calls, not `{}` placeholders.
- Naming (Hungarian-style, used throughout this codebase):
  - Variable type prefix: `s` String, `n` integer/long, `b` boolean, `c` char, `d` double, `f` float, `e` enum, `a` everything else.
  - Scope prefix: `m_` instance fields, `s_` static fields. `static final` constants use `ALL_UPPER_CASE` without prefix. Logger is `LOGGER`.
  - Parameters are `final`.
  - Type prefixes: `I` interfaces, `E` enums, `Abstract` abstract classes.
  - Private methods start with `_`.
  - "ID" is always uppercase: `getSenderID`, `m_sDocTypeID` — never `Id`.
- Formatting: space before `(` in calls, constructors, control flow, and before `<` in generics — e.g. `new Foo ()`, `if (x)`, `List <String>`.

## Release workflow

Versions follow semver (currently `0.9.x`). Release commits are typically one-line `pom.xml` version bumps. Release notes are maintained inline in `README.md` under the "News and noteworthy" section — add a new entry per released version.
