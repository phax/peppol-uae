# peppol-uae

<!-- ph-badge-start -->
[![Sonatype Central](https://maven-badges.sml.io/sonatype-central/com.helger.peppol/peppol-uae-parent-pom/badge.svg)](https://maven-badges.sml.io/sonatype-central/com.helger.peppol/peppol-uae-parent-pom/)
[![javadoc](https://javadoc.io/badge2/com.helger.peppol/peppol-uae-testfiles/javadoc.svg)](https://javadoc.io/doc/com.helger.peppol/peppol-uae-testfiles)

> If this project saved you some time or made your day a little easier, a star would mean a lot — it helps others find it too.
<!-- ph-badge-end -->

Peppol specific stuff for United Arab Emirates (UAE)

peppol-uae is part of my Peppol solution stack. See https://github.com/phax/peppol for other components and libraries in that area.

This contains a set of Java libraries.
They are licensed under the Apache 2.0 license.
The minimum requirement is Java 17.

The backing specifications are:
* TDD AE: https://docs.peppol.eu/tdd/ae/v1.0.2/
* PINT AE: https://test-docs.peppol.eu/pint/pint-ae/

# Submodules

This project consists of the following submodules (in alphabetic order)

* `peppol-uae-bom` - the Bill of Materials (BOM) with the managed versions of all artifacts of this project (since v1.1.4)
* `peppol-uae-tdd` - contains the main logic to create UAE TDD documents based on PINT AE documents as well as documentation
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

Alternatively use the following code in your `dependencyManagement` section to use it as a BOM:

```xml
<dependency>
  <groupId>com.helger.peppol</groupId>
  <artifactId>peppol-uae-bom</artifactId>
  <version>x.y.z</version>
  <type>pom</type>
  <scope>import</scope>
</dependency>
```

Note: prior to v1.1.4 there was no dedicated BOM and `peppol-uae-parent-pom` had to be imported instead, which also carries the build configuration of this project.

# Building

This project requires Apache Maven 3.x and Java 17 for building.
Simply run
```
mvn clean install
```
to build the solution.

# News and noteworthy

v1.2.1 - work in progress
* `PeppolUAETDD10ReportedTransactionBuilder`: `initFromInvoice` and `initFromCreditNote` now copy the Invoice (or CreditNote) total amount with VAT in AED (BTAE-20) from `cac:AdditionalDocumentReference[cbc:DocumentTypeCode='aedtotal-incl-vat']/cbc:DocumentDescription` into a `pxs:CustomContent`. See [issue #4](https://github.com/phax/peppol-uae/issues/4)

v1.2.0 - 2026-11-17
* This version suits the TDD 1.0.5 release
* Updated to AE TDD 1.0.5 Schematron
    * `ibr-tdd-58` / `ibr-tdd-60`: new rules, Seller name (ibt-027) and Buyer name (ibt-044) are mandatory in `cac:PartyLegalEntity/cbc:RegistrationName`
    * `ibr-tdd-59` / `ibr-tdd-61`: new rules, `cac:PartyLegalEntity` of Seller and Buyer may only contain `cbc:RegistrationName`
    * `ibr-tdd-41` / `ibr-tdd-48`: `cac:PartyLegalEntity` is now allowed in the Seller and Buyer party
    * `ibr-tdd-42`: the Seller tax identifier group may occur twice (once with tax scheme `VAT`), and may be omitted for document type codes `480` and `81`
    * `ibr-tdd-30-1` / `ibr-tdd-31-1`: currency code list aligned with PINT AE (`STN` and `XCG` added; `ANG`, `BGN` and `STD` removed)
    * Message text corrections for many rules
* Deprecated `PeppolUAETDDValidator.getSchematronUAE_TDD_104 ()` in favour of `getSchematronUAE_TDD_105 ()`
* Added `sellerName` and `buyerName` to `PeppolUAETDD10ReportedTransactionBuilder`. Both are mandatory and are filled by `initFromInvoice` and `initFromCreditNote` from `cac:PartyLegalEntity/cbc:RegistrationName`
* Added `sellerAdditionalTaxID` and `sellerAdditionalTaxSchemeID` to `PeppolUAETDD10ReportedTransactionBuilder` for the optional second Seller tax identifier group.
  If used, exactly one of the two groups must have the tax scheme `VAT`. `initFromInvoice` and `initFromCreditNote` fill it from the second `cac:PartyTaxScheme`
* `PeppolUAETDD10ReportedTransactionBuilder`: `sellerTaxID` and `sellerTaxSchemeID` are now optional for the document type codes `480` and `81`
* Updated the good TDD test files to the TDD 1.0.5 examples and added bad test files for `ibr-tdd-58` to `ibr-tdd-61`
* Updated the PINT AE Billing and Self-Billing test files to the 1.0.5 examples and added the new Billing examples

v1.1.4 - 2026-11-15
* Requires at least ph-commons 12.7.1
* Updated to peppol-commons 13.2.3 and ph-ubl 10.3.0, importing their new BOMs `ph-commons-bom`, `peppol-commons-bom` and `ph-ubl-bom`
* Updated to ph-schematron 10.3.2
* Added the new module `peppol-uae-bom` as a Maven BOM (Bill of Materials) for all peppol-uae artifacts.
  Use it instead of `peppol-uae-parent-pom` when importing the managed versions

v1.1.3 - 2026-10-22
* Requires ph-commons 12.5.2
* Added `logHandler (ILogHandler)` to `PeppolUAETDD10Builder` and all sub-builders.
  It receives the message of each missing or invalid required field (as error, warnings as warnings) when calling `build ()`; the default handler logs to the logger of the builder class.
  Use `ILogHandler.createForConsumer`, `ILogHandler.createForBiConsumer` or `ErrorListLogHandler` to redirect the messages, and `ILogHandler.createNoOp ()` to discard them.
  Call it first, because it is passed on to sub-builders created via lambda afterwards
* Added `isEveryRequiredFieldSet (ErrorList)` which collects the messages in an `ErrorList`, independent of the log handler
* Removed the public `isEveryRequiredFieldSet ()` and `isEveryRequiredFieldSet (boolean)` methods; use `isEveryRequiredFieldSet (ErrorList)` or `build ()` instead

v1.1.2 - 2026-08-01
* Aligned the AE TDD 1.0.4 Schematron with the final published release (assertion message text of `ibr-tdd-44`, `ibr-tdd-45` and `ibr-tdd-55`; no rule logic changes)

v1.1.1 - 2026-07-23
* This version suits the TDD 1.0.4 release
* Updated to AE TDD 1.0.4 Schematron
    * `ibr-tdd-17`: Receivers Endpoint Scheme identifier (tdt-008-1) is now required to be present AND equal to `0242` (SPIS)
    * `ibr-tdd-33`: Added export exclusion aligned with PINT AE rule `ibr-135-ae` (BUYER no longer mandatory when BTAE-02 is `XXXXXXX1` / Exports)
    * Schematron message text corrections (ibr-tdd-21, ibr-tdd-22, ibr-tdd-40, ibr-tdd-44, ibr-tdd-45, ibr-tdd-47, ibr-tdd-55)
    * Note: No data model or code list changes versus 1.0.3

v1.1.0 - 2026-07-19
* Updated to ph-schematron v10.x

v1.0.0 - 2026-06-03
* This version suits the TDD 1.0.3 release
* Updated to AE TDD 1.0.3 Schematron
    * Schematron message text corrections (ibr-tdd-17, ibr-tdd-21, ibr-tdd-30-1, ibr-tdd-31-1)
    * Changed XPath namespace prefix from `inv:Invoice` to `ubl:Invoice` (ibr-tdd-57)
    * Removed dead commented-out rule block (ibr-tdd-50)
    * Note: No data model, code list, or business rule changes versus 1.0.2
* Made the method `PeppolUAETDD10Marshaller.getAllXSDs` public

v0.9.2 - 2026-01-27
* This version suits the TDD 1.0.2 release published at https://docs.peppol.eu/tdd/ae/v1.0.2/
* Building as a simple JAR file and not as a bundle
* Updated to AE TDD 1.0.2 Schematron 

v0.9.1 - 2025-12-23
* This version suits the TDD 1.0.1 release published at https://docs.peppol.eu/tdd/ae/v1.0.1/
* Added updated Schematron 1.0.1
    * Removed the mandatory check for "pxs:ReportedDocument" in case of a failed TDD (TDS)
* Added explicit check, that C5 participant ID must use scheme `0242`
* Updated class `PeppolUAETDD10ReportedTransactionBuilder` to properly handle an optional `ReportedDocument`
* Improved inclusion of source Invoice and CreditNote as `SourceDocument` if they are not XSD compliant

v0.9.0 - 2025-11-28
* This version suits the TDD 1.0 release published at https://docs.peppol.eu/tdd/ae/v1.0.0/
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
