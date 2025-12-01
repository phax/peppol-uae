# peppol-uae

[![Maven Central](https://img.shields.io/maven-central/v/com.helger.peppol/peppol-uae-parent-pom)](https://img.shields.io/maven-central/v/com.helger.peppol/peppol-uae-parent-pom)
[![javadoc](https://javadoc.io/badge2/com.helger.peppol/peppol-uae-parent-pom/javadoc.svg)](https://javadoc.io/doc/com.helger.peppol/peppol-uae-parent-pom)

Peppol specific stuff for United Arab Emirates (UAE)

**This project is work in progress and not yet meant to production use!**

This contains a set of Java libraries.
They are licensed under the Apache 2.0 license.
The minimum requirement is Java 17.


# Submodules

This project consists of the following submodules (in alphabetic order)

* `peppol-uae-tdd` - contains the main logic to create UAE TDD documents based on PINT AE documents as well as documentation - see https://docs.peppol.eu/tdd/ae/v1.0.0/
    * Main class to build a complete TDD from scratch is `PeppolUAETDD10Builder`
    * To run the Schematron validation, use class `PeppolUAETDDValidator`
* `peppol-uae-tdd-datatypes` - contains the JAXB generated UAE TDD data model
    * Main class to read and write TDD XML is `PeppolUAETDD10Marshaller`
* `peppol-uae-testfiles` - contains Peppol UAE specific test files as a reusable component
    * Main class is `PeppolUAETestFiles`

# Maven usage

Add the following to your `pom.xml` to use this artifact:, replacing `x.y.z` with the real version number.

```xml
<dependency>
  <groupId>com.helger.peppol</groupId>
  <artifactId>peppol-uae-tdd</artifactId>
  <version>x.y.z</version>
</dependency>
```

# Building

This project requires Apache Maven 3.x and Java 17 for building.
Simply run
```
mvn clean install
```
to build the solution.

# News and noteworthy

v0.9.0 - 2025-11-28
* Updated to XML Schema 1.0.0
    * Changed the namespace URI to `urn:peppol:schema:taxdata:1.0`
    * Added `schemaLocation` attribute to XS imports
* Updated to Schematron 1.0.0
    * Updated the pxs namespace URI according to the XSD
    * The expected `CustomizationID` must be `urn:peppol:taxdata:ae-1`
    * The expected `ProfileID` must be `urn:peppol:taxreporting`
    * The schemeID of the `ReceivingParty` must be `0242`
    * The schemeID of the `ReportersRepresentative` must be `0242`
    * Reworded rules, so that they refer to semantic rules instead of the XML artifacts
    * Added checks that used currencies are in ISO 4217 codelist

v0.8.4 - 2025-11-16
* Updated to ph-commons 12.1.0
* Using JSpecify annotations

v0.8.3 - 2025-10-21
* Fixed an error in the `PeppolUAETDD10ReportedTransactionBuilder.initFrom(Invoice|CreditNote)` using the wrong party as buyer

v0.8.2 - 2025-10-01
* The source documents attached to a TDD are now excluding all `EmbeddedDocumentBinaryObject` elements

v0.8.1 - 2025-09-23
* Updated Schematrons - now with real assertion ID prefixes

v0.8.0 - 2025-09-22
* Initial version 

---

My personal [Coding Styleguide](https://github.com/phax/meta/blob/master/CodingStyleguide.md) |
It is appreciated if you star the GitHub project if you like it.
