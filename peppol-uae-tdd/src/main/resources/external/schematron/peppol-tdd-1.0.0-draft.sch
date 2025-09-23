<?xml version="1.0" encoding="utf-8"?>
<schema xmlns="http://purl.oclc.org/dsdl/schematron"
        xmlns:xs="http://www.w3.org/2001/XMLSchema"
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
        xmlns:pxc="urn:peppol:xslt:custom-function"
        queryBinding='xslt2'>
  <title>OpenPeppol UAE TDD Schematron</title>

  <p id="about">
    These are the Schematron rules for the OpenPeppol UAE TDD.

    Author:
      Philip Helger

    History
      v1.0.0 Draft
        2025-08-10, Philip Helger - initial version
  </p>

  <xsl:function name="pxc:genPath" as="xs:string">
    <xsl:param name="node" as="node()"/>
    <!--
      1. Iterate from root to passed node
      2a. Emits a /currentPath segment
         Adds [n] if there are other siblings with the same name (to make path unique)
      2b. Emits /@attrName 
      3. Join all nodes together without a separator
    -->
    <xsl:sequence select="
        string-join(for $ancestor in $node/ancestor-or-self::node()
                    return
                      if ($ancestor instance of element())
                      then concat('/',
                                  name($ancestor),
                                  if (   count($ancestor/preceding-sibling::*[name() = name($ancestor)]) > 0
                                      or count($ancestor/following-sibling::*[name() = name($ancestor)]) > 0)
                                  then concat('[', count($ancestor/preceding-sibling::*[name() = name($ancestor)]) + 1, ']')
                                  else ''
                                  )
                      else
                        if ($ancestor instance of attribute())
                        then concat('/@', name($ancestor))
                        else ''
                    , '')
    "/>
  </xsl:function>
    
  <ns prefix="cac" uri="urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2"/>
  <ns prefix="cbc" uri="urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2"/>
  <ns prefix="cec" uri="urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2"/>
  <ns prefix="pxs" uri="urn:peppol:schema:pint:peppol:tdd:1.0"/>
  <ns prefix="inv" uri="urn:oasis:names:specification:ubl:schema:xsd:Invoice-2"/>
  <ns prefix="cn"  uri="urn:oasis:names:specification:ubl:schema:xsd:CreditNote-2"/>
  <ns prefix="pxc" uri="urn:peppol:xslt:custom-function"/>
  
  <pattern id="default">
    <!-- Code lists -->
    <let name="cl_dtc" value="' S R W F '" />
    <let name="cl_ds" value="' D IP INP '" />
    <let name="cl_rr" value="' 01 02 '" />
    <let name="regex_pidscheme" value="'[0-9]{4}'" />

    <!-- Root element -->
    <rule context="/pxs:TaxData">
      <let name="dtc" value="normalize-space(pxs:DocumentTypeCode)" />
      <let name="ds"  value="normalize-space(pxs:DocumentScope)" />
      <let name="rr"  value="normalize-space(pxs:ReporterRole)" />
      <let name="rtCount" value="count(pxs:ReportedTransaction)" />

      <!-- CustmizationID is mandatory per XSD -->
      <assert id="XXX-01" flag="fatal" test="normalize-space(cbc:CustomizationID) = 'urn:peppol:pint:taxdata-1@ae-1'"
      >[XXX-01] The Customization ID MUST use the value 'urn:peppol:pint:taxdata-1@ae-1'</assert>

      <!-- ProfileID is mandatory per XSD -->
      <assert id="XXX-02" flag="fatal" test="normalize-space(cbc:ProfileID) = 'urn:peppol:bis:taxreporting'"
      >[XXX-02] The Profile ID MUST use the value 'urn:peppol:bis:taxreporting'</assert>

      <!-- ID is not allowed in UAE -->
      <assert id="XXX-03" flag="fatal" test="not(exists(cbc:ID))"
      >[XXX-03] The ID element MUST NOT be present</assert>

      <!-- cbc:IssueDate is mandatory according to the XSD -->
      <assert id="XXX-04" flag="fatal" test="string-length(normalize-space(cbc:IssueDate)) = 10"
      >[XXX-04] The Issue Date MUST NOT contain timezone information</assert>

      <!-- cbc:IssueTime is mandatory according to the XSD -->
      <assert id="XXX-05" flag="fatal" test="matches(normalize-space(cbc:IssueTime), '([+-]\d{2}:\d{2}|Z)$')"
      >[XXX-05] The Issue Time MUST contain timezone information</assert>
      
      <!-- DocumentTypeCode is mandatory according to the XSD -->
      <assert id="XXX-06" flag="fatal" test="not(contains($dtc, ' ')) and contains($cl_dtc, concat(' ', $dtc, ' '))"
      >[XXX-06] The Document Type Code (<value-of select="$dtc"/>) MUST be coded according to the code list</assert>
      
      <!-- DocumentScope is mandatory according to the XSD -->
      <assert id="XXX-07" flag="fatal" test="not(contains($ds, ' ')) and contains($cl_ds, concat(' ', $ds, ' '))"
      >[XXX-07] The Document Scope (<value-of select="$ds"/>) MUST be coded according to the code list</assert>
      
      <!-- ReporterRole is mandatory according to the XSD -->
      <assert id="XXX-08" flag="fatal" test="not(contains($rr, ' ')) and contains($cl_rr, concat(' ', $rr, ' '))"
      >[XXX-08] The Reporter Role (<value-of select="$rr"/>) MUST be coded according to the code list</assert>
      
      <!-- pxs:ReportedTransaction must be exactly 1 for UAE -->
      <assert id="XXX-09" flag="fatal" test="$rtCount = 1"
      >[XXX-09] Exactly one ReportedTransaction element MUST be present but found <value-of select="$rtCount" /> elements</assert>
    </rule>
    
    <!-- ReportingParty is mandatory in XSD -->
    <!-- ReceivingParty is mandatory in XSD -->
    <!-- This rule basically checks that only the cbc:EndpointID element is present -->
    <rule context="/pxs:TaxData/pxs:ReportingParty | /pxs:TaxData/pxs:ReceivingParty">
      <let name="currentPath" value="pxc:genPath(.)" />

      <!-- elements not allowed in UAE -->
      <assert id="XXX-10" flag="fatal" test="every $child in ('MarkCareIndicator', 'MarkAttentionIndicator', 'WebsiteURI', 'LogoReferenceID', 'IndustryClassificationCode',
                                                              'PartyIdentification', 'PartyName', 'Language', 'PostalAddress', 'PhysicalLocation', 'PartyTaxScheme',
                                                              'PartyLegalEntity', 'Contact', 'Person', 'AgentParty', 'ServiceProviderParty', 'PowerOfAttorney', 'FinancialAccount') 
                                               satisfies count (*[local-name(.) = $child]) = 0"
      >[XXX-10] The <value-of select="$currentPath"/> contains at least one forbidden child element</assert>
      
      <!-- EndpointID is mandatory in UAE -->
      <assert id="XXX-11" flag="fatal" test="exists(cbc:EndpointID)"
      >[XXX-11] The <value-of select="$currentPath"/>/cbc:EndpointID element MUST be present</assert>
      
      <!-- EndpointID must have a schemeID attribute mandatory in UAE -->
      <assert id="XXX-12" flag="fatal" test="exists(cbc:EndpointID/@schemeID)"
      >[XXX-12] The <value-of select="$currentPath"/>/cbc:EndpointID element MUST have a schemeID attribute</assert>
      
      <!-- EndpointID schemeID must be a Peppol Participant Identifier Scheme -->
      <assert id="XXX-13" flag="fatal" test="not(exists(cbc:EndpointID/@schemeID)) or matches(cbc:EndpointID/@schemeID, $regex_pidscheme)"
      >[XXX-13] The <value-of select="$currentPath"/>/cbc:EndpointID/@schemeID attribute MUST be a Peppol Participant Identifier Scheme</assert>
    </rule>
    
    <!-- ReportersRepresentative is mandatory in XSD -->
    <!-- This rule basically checks that only the cac:PartyIdentification element is present -->
    <rule context="/pxs:TaxData/pxs:ReportersRepresentative">
      <let name="currentPath" value="pxc:genPath(.)" />
      <let name="pidCount" value="count(cac:PartyIdentification)" />

      <!-- elements not allowed in UAE -->
      <assert id="XXX-14" flag="fatal" test="every $child in ('MarkCareIndicator', 'MarkAttentionIndicator', 'WebsiteURI', 'EndpointID', 'LogoReferenceID', 
                                                              'IndustryClassificationCode', 'PartyName', 'Language', 'PostalAddress', 'PhysicalLocation', 'PartyTaxScheme',
                                                              'PartyLegalEntity', 'Contact', 'Person', 'AgentParty', 'ServiceProviderParty', 'PowerOfAttorney', 'FinancialAccount') 
                                               satisfies count (*[local-name(.) = $child]) = 0"
      >[XXX-14] The <value-of select="$currentPath"/> contains at least one forbidden child element</assert>
      
      <!-- PartyIdentification is mandatory in UAE -->
      <assert id="XXX-15" flag="fatal" test="$pidCount = 1"
      >[XXX-15] Exactly one <value-of select="$currentPath"/>/cac:PartyIdentification element MUST be present but found <value-of select="$pidCount"/> elements</assert>
    </rule>
    
    <rule context="/pxs:TaxData/pxs:ReportersRepresentative/cac:PartyIdentification">
      <let name="currentPath" value="pxc:genPath(.)" />

      <!-- ID is the only element and it is mandatory -->

      <!-- PartyIdentification/ID schemeID is mandatory in UAE -->
      <assert id="XXX-16" flag="fatal" test="exists(cbc:ID/@schemeID)"
      >[XXX-16] Exactly one <value-of select="$currentPath"/>/cbc:ID element MUST have a schemeID attribute</assert>
      
      <!-- PartyIdentification/ID schemeID be a Peppol Participant Identifier Scheme -->
      <assert id="XXX-17" flag="fatal" test="not(exists(cbc:ID/@schemeID)) or 
                                             matches(cbc:ID/@schemeID, $regex_pidscheme)"
      >[XXX-17] Exactly one <value-of select="$currentPath"/>/cbc:ID element MUST be a Peppol Participant Identifier Scheme</assert>
    </rule>
    
    <rule context="/pxs:TaxData/pxs:ReportedTransaction">
      <let name="currentPath" value="pxc:genPath(.)" />
      <let name="ccCount" value="count(pxs:CustomContent)" />
      
      <!-- pxs:TransportHeaderID is optional for UAE -->
    
      <!-- ReportedDocument is required for UAE -->
      <assert id="XXX-18" flag="fatal" test="exists(pxs:ReportedDocument)"
      >[XXX-18] The <value-of select="$currentPath"/>/pxs:ReportedDocument element MUST be present</assert>
    
      <!-- ReportedDocument is required for UAE -->
      <assert id="XXX-19" flag="fatal" test="$ccCount &lt;= 1"
      >[XXX-19] A maximum of 1 <value-of select="$currentPath"/>/pxs:CustomContent element MUST be present but found <value-of select="$ccCount" /> elements</assert>

      <!-- SourceDocument is required for UAE -->
      <assert id="XXX-20" flag="fatal" test="exists(pxs:SourceDocument)"
      >[XXX-20] The <value-of select="$currentPath"/>/pxs:SourceDocument element MUST be present</assert>
    </rule>
    
    <rule context="/pxs:TaxData/pxs:ReportedTransaction/pxs:ReportedDocument">
      <let name="currentPath" value="pxc:genPath(.)" />
      <let name="dcc" value="normalize-space(cbc:DocumentCurrencyCode)" />
      <let name="has_tcc" value="exists(cbc:TaxCurrencyCode)" />
      <let name="tcc" value="normalize-space(cbc:TaxCurrencyCode)" />
      <let name="currencyCount" value="if ($has_tcc) then (2) else (1)" />
      <let name="ttCount" value="count(cac:TaxTotal)" />
      <let name="mtCount" value="count(pxs:MonetaryTotal)" />
    
      <!-- CustomizationID is mandatory in UAE -->
      <assert id="XXX-21" flag="fatal" test="exists(cbc:CustomizationID)"
      >[XXX-21] The <value-of select="$currentPath"/>/cbc:CustomizationID element MUST be present</assert>
      
      <!-- ProfileID is mandatory in UAE -->
      <assert id="XXX-22" flag="fatal" test="exists(cbc:ProfileID)"
      >[XXX-22] The <value-of select="$currentPath"/>/cbc:ProfileID element MUST be present</assert>
      
      <!-- ID is mandatory in UAE -->
      <assert id="XXX-23" flag="fatal" test="exists(cbc:ID)"
      >[XXX-23] The <value-of select="$currentPath"/>/cbc:ID element MUST be present</assert>
      
      <!-- UUID is mandatory in UAE -->
      <assert id="XXX-24" flag="fatal" test="exists(cbc:UUID)"
      >[XXX-24] The <value-of select="$currentPath"/>/cbc:UUID element MUST be present</assert>
      
      <!-- IssueDate is mandatory in UAE -->
      <assert id="XXX-25" flag="fatal" test="exists(cbc:IssueDate)"
      >[XXX-25] The <value-of select="$currentPath"/>/cbc:IssueDate element MUST be present</assert>
      
      <!-- IssueTime is optional in UAE -->
      
      <!-- DocumentTypeCode is mandatory in UAE -->
      <assert id="XXX-26" flag="fatal" test="exists(pxs:DocumentTypeCode)"
      >[XXX-26] The <value-of select="$currentPath"/>/pxs:DocumentTypeCode element MUST be present</assert>
      
      <!-- DocumentCurrencyCode is mandatory in UAE -->
      <assert id="XXX-27" flag="fatal" test="exists(cbc:DocumentCurrencyCode)"
      >[XXX-27] The <value-of select="$currentPath"/>/cbc:DocumentCurrencyCode element MUST be present</assert>
      
      <!-- TaxCurrencyCode is optional in UAE -->

      <!-- If TaxCurrencyCode is present, it must be different from DocumentCurrencyCode -->
      <assert id="XXX-28" flag="fatal" test="not($has_tcc) or $dcc != $tcc"
      >[XXX-28] The <value-of select="$currentPath"/>/cbc:TaxCurrencyCode (<value-of select="$tcc" />) MUST be different from the <value-of select="$currentPath"/>/cbc:DocumentCurrencyCode (<value-of select="$dcc" />)</assert>

      
      <!-- AccountingSupplierParty is mandatory in UAE -->
      <assert id="XXX-29" flag="fatal" test="exists(cac:AccountingSupplierParty)"
      >[XXX-29] The <value-of select="$currentPath"/>/cac:AccountingSupplierParty element MUST be present</assert>

      
      <!-- AccountingCustomerParty is mandatory in UAE -->
      <assert id="XXX-30" flag="fatal" test="exists(cac:AccountingCustomerParty)"
      >[XXX-30] The <value-of select="$currentPath"/>/cac:AccountingCustomerParty element MUST be present</assert>

      
      <!-- TaxTotal is mandatory in UAE -->
      <assert id="XXX-31" flag="fatal" test="$ttCount = $currencyCount"
      >[XXX-31] Exactly <value-of select="$currencyCount" /> <value-of select="$currentPath"/>/cac:TaxTotal <value-of select="if ($currencyCount = 1) then 'element is' else 'elements are'" /> expected but found <value-of select="$ttCount" /> elements</assert>

      <!-- TaxTotal in DocumentCurrency must be present -->
      <assert id="XXX-32" flag="fatal" test="count(cac:TaxTotal[cbc:TaxAmount/@currencyID = $dcc]) = 1"
      >[XXX-32] Exactly 1 <value-of select="$currentPath"/>/cac:TaxTotal element with an amount using document currency <value-of select="$dcc" />  MUST be present</assert>

      <!-- If TaxCurrency is present, a TaxTotal in TaxCurrency must exist as well -->
      <assert id="XXX-33" flag="fatal" test="not($has_tcc) or count(cac:TaxTotal[cbc:TaxAmount/@currencyID = $tcc]) = 1"
      >[XXX-33] Exactly 1 <value-of select="$currentPath"/>/cac:TaxTotal element with an amount using Tax Currency <value-of select="$tcc" />  MUST be present</assert>

      
      <!-- MonetaryTotal in DocumentCurrency is mandatory for UAE -->
      <assert id="XXX-34" flag="fatal" test="$mtCount = 1"
      >[XXX-34] Exactly 1 <value-of select="$currentPath"/>/pxs:MonetaryTotal element must be present but found <value-of select="$mtCount" /> elements</assert>

      <!-- MonetaryTotal currency must be document currency -->
      <assert id="XXX-35" flag="fatal" test="$mtCount != 1 or count(pxs:MonetaryTotal[cbc:TaxExclusiveAmount/@currencyID = $dcc]) = 1"
      >[XXX-35] Exactly 1 <value-of select="$currentPath"/>/pxs:MonetaryTotal element with an amount using Document Currency <value-of select="$dcc" />  MUST be present</assert>
    </rule>

    <rule context="/pxs:TaxData/pxs:ReportedTransaction/pxs:ReportedDocument/cac:AccountingSupplierParty">
      <let name="currentPath" value="pxc:genPath(.)" />

      <!-- Make sure only Party is present in UAE -->
      <assert id="XXX-36" flag="fatal" test="every $child in ('CustomerAssignedAccountID', 'AdditionalAccountID', 'DataSendingCapability', 
                                                              'DespatchContact', 'AccountingContact', 'SellerContact') 
                                               satisfies count (*[local-name(.) = $child]) = 0"
      >[XXX-36] The <value-of select="$currentPath"/> element contains at least one forbidden child element</assert>

      <!-- Party is mandatory in UAE -->
      <assert id="XXX-37" flag="fatal" test="exists(cac:Party)"
      >[XXX-37] The <value-of select="$currentPath"/>/cac:Party element MUST be present</assert>
    </rule>

    <rule context="/pxs:TaxData/pxs:ReportedTransaction/pxs:ReportedDocument/cac:AccountingSupplierParty/cac:Party">
      <let name="currentPath" value="pxc:genPath(.)" />
      <let name="ptsCount" value="count(cac:PartyTaxScheme)" />

      <!-- Make sure only PartyTaxScheme is present in UAE -->
      <assert id="XXX-38" flag="fatal" test="every $child in ('MarkCareIndicator', 'MarkAttentionIndicator', 'WebsiteURI', 'LogoReferenceID', 'EndpointID',
                                                              'IndustryClassificationCode', 'PartyIdentification', 'PartyName', 'Language', 'PostalAddress', 'PhysicalLocation',
                                                              'PartyLegalEntity', 'Contact', 'Person', 'AgentParty', 'ServiceProviderParty', 'PowerOfAttorney', 'FinancialAccount')
                                               satisfies count (*[local-name(.) = $child]) = 0"
      >[XXX-38] The <value-of select="$currentPath"/> element contains at least one forbidden child element</assert>

      <!-- PartyTaxScheme is mandatory in UAE -->
      <assert id="XXX-39" flag="fatal" test="$ptsCount = 1"
      >[XXX-39] Exactly 1 <value-of select="$currentPath"/>/cac:PartyTaxScheme element MUST be present but found <value-of select="$ptsCount" /> elements</assert>
    </rule>

    <rule context="/pxs:TaxData/pxs:ReportedTransaction/pxs:ReportedDocument/cac:AccountingSupplierParty/cac:Party/cac:PartyTaxScheme">
      <let name="currentPath" value="pxc:genPath(.)" />

      <!-- Make sure only CompanyID and TaxScheme are present in UAE -->
      <assert id="XXX-40" flag="fatal" test="every $child in ('RegistrationName', 'TaxLevelCode', 'ExemptionReasonCode', 'ExemptionReason', 'RegistrationAddress')
                                               satisfies count (*[local-name(.) = $child]) = 0"
      >[XXX-40] The <value-of select="$currentPath"/> element contains at least one forbidden child element</assert>

      <!-- CompanyID is mandatory in UAE -->
      <assert id="XXX-41" flag="fatal" test="exists(cbc:CompanyID)"
      >[XXX-41] The <value-of select="$currentPath"/>/cbc:CompanyID element MUST be present</assert>

      <!-- TaxScheme is mandatory -->
      
      <!-- TaxScheme/ID is mandatory in UAE -->
      <assert id="XXX-42" flag="fatal" test="exists(cac:TaxScheme/cbc:ID)"
      >[XXX-42] The <value-of select="$currentPath"/>/cac:TaxScheme/cbc:ID element MUST be present</assert>
    </rule>

    <rule context="/pxs:TaxData/pxs:ReportedTransaction/pxs:ReportedDocument/cac:AccountingCustomerParty">
      <let name="currentPath" value="pxc:genPath(.)" />

      <!-- Make sure only Party is present in UAE -->
      <assert id="XXX-43" flag="fatal" test="every $child in ('CustomerAssignedAccountID', 'SupplierAssignedAccountID', 'AdditionalAccountID', 
                                                              'DeliveryContact', 'AccountingContact', 'BuyerContact') 
                                               satisfies count (*[local-name(.) = $child]) = 0"
      >[XXX-43] The <value-of select="$currentPath"/> element contains at least one forbidden child element</assert>

      <!-- Party is mandatory in UAE -->
      <assert id="XXX-44" flag="fatal" test="exists(cac:Party)"
      >[XXX-44] The <value-of select="$currentPath"/>/cac:Party element MUST be present</assert>
    </rule>

    <rule context="/pxs:TaxData/pxs:ReportedTransaction/pxs:ReportedDocument/cac:AccountingCustomerParty/cac:Party">
      <let name="currentPath" value="pxc:genPath(.)" />

      <!-- Make sure only PartyIdentification or PartyTaxScheme or present in UAE -->
      <assert id="XXX-45" flag="fatal" test="every $child in ('MarkCareIndicator', 'MarkAttentionIndicator', 'WebsiteURI', 'LogoReferenceID', 'EndpointID',
                                                              'IndustryClassificationCode', 'PartyName', 'Language', 'PostalAddress', 'PhysicalLocation',
                                                              'PartyLegalEntity', 'Contact', 'Person', 'AgentParty', 'ServiceProviderParty', 'PowerOfAttorney', 'FinancialAccount')
                                               satisfies count (*[local-name(.) = $child]) = 0"
      >[XXX-45] The <value-of select="$currentPath"/> element contains at least one forbidden child element</assert>

      <!-- both cac:PartyIdentification and cac:PartyTaxScheme are optional -->
    </rule>

    <rule context="/pxs:TaxData/pxs:ReportedTransaction/pxs:ReportedDocument/cac:AccountingCustomerParty/cac:Party/cac:PartyIdentification">
      <!-- ID element is mandatory -->
      <!-- ID/schemeID attribute is optional -->
    </rule>

    <rule context="/pxs:TaxData/pxs:ReportedTransaction/pxs:ReportedDocument/cac:AccountingCustomerParty/cac:Party/cac:PartyTaxScheme">
      <let name="currentPath" value="pxc:genPath(.)" />

      <!-- Make sure only CompanyID and TaxScheme are present in UAE -->
      <assert id="XXX-46" flag="fatal" test="every $child in ('RegistrationName', 'TaxLevelCode', 'ExemptionReasonCode', 'ExemptionReason', 'RegistrationAddress')
                                               satisfies count (*[local-name(.) = $child]) = 0"
      >[XXX-46] The <value-of select="$currentPath"/> element contains at least one forbidden child element</assert>

      <!-- CompanyID is mandatory in UAE -->
      <assert id="XXX-47" flag="fatal" test="exists(cbc:CompanyID)"
      >[XXX-47] The <value-of select="$currentPath"/>/cbc:CompanyID element MUST be present</assert>

      <!-- TaxScheme is mandatory -->
      <!-- TaxScheme/ID is optional -->
    </rule>

    <rule context="/pxs:TaxData/pxs:ReportedTransaction/pxs:ReportedDocument/cac:TaxTotal">
      <let name="currentPath" value="pxc:genPath(.)" />

      <!-- Make sure only TaxAmount is present in UAE -->
      <assert id="XXX-48" flag="fatal" test="every $child in ('RoundingAmount', 'TaxEvidenceIndicator', 'TaxIncludedIndicator', 'TaxSubtotal') 
                                               satisfies count (*[local-name(.) = $child]) = 0"
      >[XXX-48] The <value-of select="$currentPath"/> element contains at least one forbidden child element</assert>
      
      <!-- TaxAmount element is mandatory -->
    </rule>

    <!-- Make sure only TaxExclusiveAmount and PayableAmount are present -->
    <rule context="/pxs:TaxData/pxs:ReportedTransaction/pxs:ReportedDocument/pxs:MonetaryTotal">
      <let name="currentPath" value="pxc:genPath(.)" />

      <!-- TaxExclusiveAmount is mandatory in UAE -->
      <assert id="XXX-49" flag="fatal" test="exists(cbc:TaxExclusiveAmount)"
      >[XXX-49] The <value-of select="$currentPath"/>/cbc:TaxExclusiveAmount element must be present</assert>
      
      <!-- TaxInclusiveAmount is forbidden in UAE -->
      <assert id="XXX-50" flag="fatal" test="not(exists(cbc:TaxInclusiveAmount))"
      >[XXX-50] The <value-of select="$currentPath"/>/cbc:TaxInclusiveAmount element must not be present</assert>
    </rule>
    
    <rule context="/pxs:TaxData/pxs:ReportedTransaction/pxs:CustomContent">
      <let name="currentPath" value="pxc:genPath(.)" />
      
      <!-- cbc:ID is mandatory in XSD -->

      <!-- No need to normalize here -->
      <assert id="XXX-51" flag="fatal" test="cbc:ID = upper-case(cbc:ID)"
      >[XXX-51] The <value-of select="$currentPath"/>/pxs:ID element MUST be all uppercase</assert>
      
      <!-- UAE only allows the simple cbc:Value element
           As the XSD uses "choice" the existance of Value implicitly forbids ExtensionContent element existance
      -->
      <assert id="XXX-52" flag="fatal" test="exists(cbc:Value)"
      >[XXX-52] The <value-of select="$currentPath"/> MUST use the simple cbc:Value element</assert>
    </rule>
    
    <rule context="/pxs:TaxData/pxs:ReportedTransaction/pxs:SourceDocument">
      <let name="currentPath" value="pxc:genPath(.)" />

      <!-- elements not allowed in UAE -->
      <assert id="XXX-53" flag="fatal" test="every $child in ('ID', 'Name', 'ExtensionAgencyID', 'ExtensionAgencyName', 'ExtensionVersionID', 'ExtensionAgencyURI',
                                                              'ExtensionURI', 'ExtensionReasonCode', 'ExtensionReason') 
                                               satisfies count (*[local-name(.) = $child]) = 0"
      >[XXX-53] The <value-of select="$currentPath"/> element contains at least one forbidden child element</assert>
      
      <!-- The element ExtensionContent is mandatory in XSD -->
    </rule>
    
    <rule context="/pxs:TaxData/pxs:ReportedTransaction/pxs:SourceDocument/cec:ExtensionContent">
      <let name="currentPath" value="pxc:genPath(.)" />

      <!-- In UAE it must be UBL Invoice or UBL CreditNote -->
      <assert id="XXX-54" flag="fatal" test="exists(inv:Invoice) or exists(cn:CreditNote)"
      >[XXX-54] The <value-of select="$currentPath"/> element MUST contain either a UBL Invoice or a UBL Credit Note</assert>
    </rule>
  </pattern>
</schema>
